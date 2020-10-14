package com.edu.rest.service.impl;

import com.edu.common.bean.ShoppingResult;
import com.edu.rest.dao.RedisDao;
import com.edu.rest.service.CacheService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

@Service
public class CacheServiceImpl implements CacheService {
    @Autowired
    private RedisDao redisDao;
    @Value("${INDEX_CONTENT_CATEGORY_CACHE}")
    private String INDEX_CONTENT_CATEGORY_CACHE;

    @Override
    public ShoppingResult syncContentCategory(long categoryId) {
        redisDao.hdel(INDEX_CONTENT_CATEGORY_CACHE,categoryId+"");
        return ShoppingResult.ok();
    }
}
