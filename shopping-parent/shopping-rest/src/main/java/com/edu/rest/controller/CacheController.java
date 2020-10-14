package com.edu.rest.controller;

import com.edu.common.bean.ShoppingResult;
import com.edu.rest.service.CacheService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/cache/sync")
public class CacheController {
    @Autowired
    private CacheService cacheService;

    @RequestMapping("/content/{categoryId}")
    public ShoppingResult syncContentCategoryCache(@PathVariable long categoryId){
        return cacheService.syncContentCategory(categoryId);
    }
}
