package com.edu.sso.service.impl;

import com.edu.bean.TbUser;
import com.edu.bean.TbUserExample;
import com.edu.common.bean.ShoppingResult;
import com.edu.common.util.CookieUtils;
import com.edu.common.util.JsonUtils;
import com.edu.mapper.TbUserMapper;
import com.edu.sso.dao.RedisDao;
import com.edu.sso.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.util.DigestUtils;
import org.springframework.util.StringUtils;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.Date;
import java.util.List;
import java.util.UUID;

@Service
public class UserServiceImpl implements UserService {
    @Autowired
    private TbUserMapper tbUserMapper;
    @Autowired
    private RedisDao redisDao;
    @Value("${REDIS_TOKEN}")
    private String REDIS_TOKEN;
    @Value("${REDIS_TOKEN_EXPIRE}")
    private int REDIS_TOKEN_EXPIRE;
    @Override
    public ShoppingResult getUserByNameAndType(String param, int type) {
        TbUserExample example = new TbUserExample();
        if (type ==1){
            example.createCriteria().andUsernameEqualTo(param);
        }else if (type == 2){
            example.createCriteria().andPhoneEqualTo(param);
        }else if (type == 3){
            example.createCriteria().andEmailEqualTo(param);
        }
        List<TbUser> tbUsers = tbUserMapper.selectByExample(example);
        if (tbUsers.size()>0){
            return  ShoppingResult.ok(false);
        }
        return ShoppingResult.ok(true);
    }

    @Override
    public ShoppingResult saveUser(TbUser user) {
        try {
            user.setUpdated(new Date());
            user.setCreated(new Date());
            user.setPassword(DigestUtils.md5DigestAsHex(user.getPassword().getBytes()));
            tbUserMapper.insertSelective(user);
            return ShoppingResult.ok(user);
        }catch (Exception e){
            return ShoppingResult.build(400,"注册失败");
        }

    }

    @Override
    public ShoppingResult login(TbUser user, HttpServletRequest request, HttpServletResponse response) {
        TbUserExample example = new TbUserExample();
        example.createCriteria().andUsernameEqualTo(user.getUsername());
        List<TbUser> tbUsers = tbUserMapper.selectByExample(example);
        if (null == tbUsers || tbUsers.size() == 0){
            return  ShoppingResult.build(400,"用户名或者密码错误");
        }
        TbUser tbUser = tbUsers.get(0);
        if (!tbUser.getPassword().equals(DigestUtils.md5DigestAsHex(user.getPassword().getBytes()))){
            return  ShoppingResult.build(400,"用户名或者密码错误");
        }
        String token = UUID.randomUUID().toString();
        redisDao.set(REDIS_TOKEN+":"+token, JsonUtils.objectToJson(user));
        redisDao.expire(REDIS_TOKEN+":"+token,REDIS_TOKEN_EXPIRE);
        CookieUtils.setCookie(request,response,"TT_TOKEN",token,true);
        return ShoppingResult.ok(token);
    }

    @Override
    public TbUser getUserByToken(String token) {
        String result = redisDao.get(REDIS_TOKEN + ":" + token);
        if (StringUtils.isEmpty(result)){
            return null;
        }
        TbUser user = JsonUtils.jsonToPojo(result, TbUser.class);
        redisDao.expire(REDIS_TOKEN+":"+token,REDIS_TOKEN_EXPIRE);
        return user;
    }

    @Override
    public ShoppingResult logout(String token) {
        redisDao.expire(REDIS_TOKEN+":"+token,0);
        return ShoppingResult.ok();
    }
}
