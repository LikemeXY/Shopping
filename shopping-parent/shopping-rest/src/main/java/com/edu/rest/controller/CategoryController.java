package com.edu.rest.controller;

import com.edu.common.util.JsonUtils;
import com.edu.rest.bean.CatRusltNode;
import com.edu.rest.service.CategoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

/**
 *
 * 用jsonp跨域的方式调取数据
 */
@Controller
public class CategoryController {
    @Autowired
    private CategoryService categoryService;

    @ResponseBody
    @RequestMapping(value = "/itemcat/all",produces = MediaType.APPLICATION_JSON_VALUE+";charset=utf-8")
    private String all(String callback){
        CatRusltNode all = categoryService.getAll(callback);
        String result = JsonUtils.objectToJson(all);
        return callback+"("+result+")";
    }
}
