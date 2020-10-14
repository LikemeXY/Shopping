package com.edu.controller;

import com.edu.bean.TbItem;
import com.edu.common.bean.EUDategridResult;
import com.edu.common.bean.ShoppingResult;
import com.edu.service.ItemService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

/**
 * 商品控制器
 */
@Controller
@RequestMapping("/item")
public class ItemController {

    @Autowired
    private ItemService itemService;

    @ResponseBody
    @RequestMapping("/list")
    public EUDategridResult list(int page,int rows){
        return itemService.getAll(page,rows);
    }

    @ResponseBody
    @RequestMapping("/save")
    public ShoppingResult save(TbItem item,String desc,String itemParams){
        return itemService.inserItem(item,desc,itemParams);
    }


}
