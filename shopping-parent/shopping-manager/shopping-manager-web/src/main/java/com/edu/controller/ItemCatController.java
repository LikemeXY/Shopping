package com.edu.controller;

import com.edu.common.bean.EUTreeResult;
import com.edu.service.ItemCatService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.List;

/**
 * 商品分类控制器
 */
@Controller
@RequestMapping("/item/cat")
public class ItemCatController {

    @Autowired
    private ItemCatService itemCatService;

    @ResponseBody
    @RequestMapping("/list")
    public List<EUTreeResult> selectItemCat(@RequestParam(value="cid",defaultValue="0")Long cid){
        List<EUTreeResult> itemCatList = itemCatService.getItemCatList(cid);
        return itemCatList;
    }

}
