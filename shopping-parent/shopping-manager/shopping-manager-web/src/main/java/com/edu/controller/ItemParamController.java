package com.edu.controller;

import com.edu.common.bean.EUDategridResult;
import com.edu.common.bean.ShoppingResult;
import com.edu.service.ItemParamService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
@RequestMapping("/item/param")
public class ItemParamController {
    @Autowired
    private ItemParamService itemParamService;

    @ResponseBody
    @RequestMapping("/list")
    public EUDategridResult paramList(int page, int rows){
        return itemParamService.getAll(page,rows);
    }

    @ResponseBody
    @RequestMapping("/query/itemcatid/{categoryId}")
    public ShoppingResult itemParam(@PathVariable("categoryId") Long categoryId){
        return itemParamService.getItemParaByCategoryId(categoryId);
    }

    @ResponseBody
    @RequestMapping("/save/{categoryId}")
    public ShoppingResult save(@PathVariable("categoryId") Long categoryId,String paramData){
        return itemParamService.insertParam(categoryId,paramData);
    }
}
