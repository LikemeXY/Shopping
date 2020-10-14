package com.edu.controller;

import com.edu.common.bean.EUTreeResult;
import com.edu.common.bean.ShoppingResult;
import com.edu.service.ContentCategoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.List;

/**
 * cms内容分类控制器
 */
@Controller
@RequestMapping("/content/category")
public class ContentCategoryController {
    @Autowired
    private ContentCategoryService categoryService;

    @ResponseBody
    @RequestMapping("/list")
    public List<EUTreeResult> list(@RequestParam(value="id",defaultValue="0") long id){
        return categoryService.getAll(id);
    }

    @ResponseBody
    @RequestMapping("/create")
    public ShoppingResult create(long parentId,String name){
        return categoryService.insertContentCategory(parentId,name);
    }

    @ResponseBody
    @RequestMapping("/update")
    public ShoppingResult update(long id ,String name){
        return categoryService.updateContentCategory(id,name);
    }

    @ResponseBody
    @RequestMapping("/delete")
    public ShoppingResult delete(long id){
        return categoryService.deleteContentCategory(id);
    }
}
