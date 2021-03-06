package com.edu.rest.controller;

import com.edu.rest.service.ContentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.List;
import java.util.Map;
/**
 * 内容发布控制器
 */
@Controller
public class ContentController {
    @Autowired
    private ContentService contentService;

    @ResponseBody
    @RequestMapping("/content/list/{categoryId}")
    public List<Map<String,Object>> list(@PathVariable("categoryId") long categoryId){
        return contentService.list(categoryId);
    }
}
