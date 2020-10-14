package com.edu.rest.service.impl;

import com.edu.bean.*;
import com.edu.common.bean.ShoppingResult;
import com.edu.common.util.JsonUtils;
import com.edu.mapper.TbItemDescMapper;
import com.edu.mapper.TbItemMapper;
import com.edu.mapper.TbItemParamItemMapper;
import com.edu.rest.dao.RedisDao;
import com.edu.rest.service.ItemService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import java.util.List;

@Service
public class ItemServiceImpl implements ItemService {
    @Autowired
    private RedisDao redisDao;
    @Autowired
    private TbItemMapper itemMapper;
    @Autowired
    private TbItemDescMapper itemDescMapper;
    @Autowired
    private TbItemParamItemMapper itemParamItemMapper;
    @Value("${REDIS_ITEM_KEY}")
    private String REDIS_ITEM_KEY;
    @Value("${REDIS_ITEM_EXPIRE}")
    private int REDIS_ITEM_EXPIRE;
    @Override
    public ShoppingResult getInfo(long itemId) {
        TbItem tbItem = null;
        //从redis缓存中取值
        try {
            String infoItem=redisDao.get(REDIS_ITEM_KEY+":"+itemId+":INFO");
            if (!StringUtils.isEmpty(infoItem)){
                tbItem = JsonUtils.jsonToPojo(infoItem,TbItem.class);
                return ShoppingResult.ok(tbItem);
            }
        } catch (Exception e){
            e.printStackTrace();
        }
        //先取值然后存入缓存中
        tbItem = itemMapper.selectByPrimaryKey(itemId);
        try {
            redisDao.set(REDIS_ITEM_KEY+":"+itemId+":INFO",JsonUtils.objectToJson(tbItem));
            redisDao.expire(REDIS_ITEM_KEY+":"+itemId+":INFO",REDIS_ITEM_EXPIRE);
            return ShoppingResult.ok(tbItem);
        } catch (Exception e){
            e.printStackTrace();
            return ShoppingResult.build(500,"错误");
        }
    }

    @Override
    public ShoppingResult getDesc(long itemId) {
        TbItemDesc tbItemDesc = null;
        try {
           String descItem = redisDao.get(REDIS_ITEM_KEY + ":" + itemId + ":DESC");
           if (!StringUtils.isEmpty(descItem)){
               tbItemDesc = JsonUtils.jsonToPojo(descItem,TbItemDesc.class);
               return ShoppingResult.ok(tbItemDesc);
           }
        }catch (Exception e){
            e.printStackTrace();
        }

        tbItemDesc = itemDescMapper.selectByPrimaryKey(itemId);
        try {
            redisDao.set(REDIS_ITEM_KEY+":"+itemId+":DESC",JsonUtils.objectToJson(tbItemDesc));
            redisDao.expire(REDIS_ITEM_KEY+":"+itemId+":DESC",REDIS_ITEM_EXPIRE);
            return ShoppingResult.ok(tbItemDesc);
        } catch (Exception e){
            e.printStackTrace();
            return ShoppingResult.build(500,"错误");
        }
    }

    @Override
    public ShoppingResult getParam(long itemId) {
        TbItemParamItem tbItemParamItem = null;
        try {
            String paramItem = redisDao.get(REDIS_ITEM_KEY + ":" + itemId + ":PARAM");
            if (!StringUtils.isEmpty(paramItem)){
                tbItemParamItem = JsonUtils.jsonToPojo(paramItem,TbItemParamItem.class);
                return ShoppingResult.ok(tbItemParamItem);
            }
        }catch (Exception e){
            e.printStackTrace();
        }
        TbItemParamItemExample example = new TbItemParamItemExample();
        example.createCriteria().andItemIdEqualTo(itemId);
        List<TbItemParamItem> TbItemParamItem = itemParamItemMapper.selectByExampleWithBLOBs(example);
        try {
            if (null != TbItemParamItem&&TbItemParamItem.size()>0){
                tbItemParamItem = TbItemParamItem.get(0);
                redisDao.set(REDIS_ITEM_KEY+":"+itemId+":PARAM",JsonUtils.objectToJson(tbItemParamItem));
                redisDao.expire(REDIS_ITEM_KEY+":"+itemId+":PARAM",REDIS_ITEM_EXPIRE);
                return ShoppingResult.ok(tbItemParamItem);
            }
        } catch (Exception e){
            e.printStackTrace();
            return ShoppingResult.build(500,"错误");
        }
        return ShoppingResult.build(500,"错误");
    }
}
