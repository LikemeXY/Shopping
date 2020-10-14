package com.edu.portal.service.impl;

import com.edu.common.util.HttpClientUtil;
import com.edu.portal.service.UserService;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

@Service
public class UserServiceImpl implements UserService {
    @Value("${LOGIN_OUT_BASE_URL}")
    private String LOGIN_OUT_BASE_URL;
    @Value("${USER_BASE_URL}")
    private String USER_BASE_URL;
    @Override
    public void logout(String token) {
        HttpClientUtil.doGet(USER_BASE_URL+LOGIN_OUT_BASE_URL+token);
    }
}
