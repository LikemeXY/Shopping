package com.edu.sso.service;

import com.edu.bean.TbUser;
import com.edu.common.bean.ShoppingResult;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public interface UserService {
    ShoppingResult getUserByNameAndType(String param,int type);
    ShoppingResult saveUser(TbUser user);

    ShoppingResult login(TbUser user, HttpServletRequest request, HttpServletResponse response);

    TbUser getUserByToken(String token);

    ShoppingResult logout(String token);
}
