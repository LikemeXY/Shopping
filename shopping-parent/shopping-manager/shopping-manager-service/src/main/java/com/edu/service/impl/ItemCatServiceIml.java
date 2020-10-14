package com.edu.service.impl;

import com.edu.bean.TbItemCat;
import com.edu.bean.TbItemCatExample;
import com.edu.common.bean.EUTreeResult;
import com.edu.mapper.TbItemCatMapper;
import com.edu.service.ItemCatService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

@Service
@Transactional
public class ItemCatServiceIml implements ItemCatService {

    @Autowired
    private TbItemCatMapper itemCatMapper;


    @Override
    public List<EUTreeResult> getItemCatList(long cid) {
        // 1. 创建查询条件
        TbItemCatExample example = new TbItemCatExample();
        example.createCriteria().andParentIdEqualTo(cid); // 查询父节点下的所有子节点
        example.createCriteria().andStatusEqualTo(1); // 查询未删除状态的菜单
        // 2. 根据条件查询
        List<TbItemCat> list = itemCatMapper.selectByExample(example);
        List<EUTreeResult> resultList = new ArrayList<>();
        for (TbItemCat itemCat : list) {
            EUTreeResult tree = new EUTreeResult();
            tree.setId(itemCat.getId());
            tree.setText(itemCat.getName());
            tree.setState(itemCat.getIsParent()?"closed":"open");
            if(itemCat.getIsParent()){
                List<EUTreeResult> categoryList = getItemCatList(tree.getId());
                tree.setChildren(categoryList);
            }
            resultList.add(tree);
        }
        // 3. 返回结果
        return resultList;
    }
}
