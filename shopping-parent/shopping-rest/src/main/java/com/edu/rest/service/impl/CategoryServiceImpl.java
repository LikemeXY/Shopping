package com.edu.rest.service.impl;

import com.edu.bean.TbItemCat;
import com.edu.bean.TbItemCatExample;
import com.edu.mapper.TbItemCatMapper;
import com.edu.rest.bean.CatNode;
import com.edu.rest.bean.CatRusltNode;
import com.edu.rest.service.CategoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class CategoryServiceImpl implements CategoryService {
    @Autowired
    private TbItemCatMapper itemCatMapper;

    @Override
    public CatRusltNode getAll(String callback) {
        CatRusltNode catRusltNode = new CatRusltNode();
        catRusltNode.setData(getCatNode(0));
        return catRusltNode;
    }

    private List<?> getCatNode(long parentId){
        // 1. 创建查询条件
        TbItemCatExample example = new TbItemCatExample();
        example.createCriteria().andParentIdEqualTo(parentId); // 查询父节点下的所有子节点
        // 2. 根据条件查询
        List<TbItemCat> list = itemCatMapper.selectByExample(example);
        List resultList = new ArrayList();
        int count =0;
        for (TbItemCat itemCat : list) {
            CatNode catNode = new CatNode();
            if (itemCat.getIsParent()){
                if (parentId == 0){
                    catNode.setName("<a href='/products/"+itemCat.getId()+".html'>"+itemCat.getName()+"</a>");
                } else {
                    catNode.setName(itemCat.getName());
                }
                catNode.setUrl("/products/"+itemCat.getId()+".html");
                catNode.setItem(getCatNode(itemCat.getId()));
                resultList.add(catNode);
                count++;
                if (count >= 14 && parentId == 0){
                    break;
                }
            }else {
                resultList.add("/products/"+itemCat.getId()+".html|"+itemCat.getName());
            }
        }
        return resultList;
    }
}
