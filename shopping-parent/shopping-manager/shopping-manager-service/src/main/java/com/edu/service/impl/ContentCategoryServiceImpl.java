package com.edu.service.impl;

import com.edu.bean.TbContentCategory;
import com.edu.bean.TbContentCategoryExample;
import com.edu.common.bean.EUTreeResult;
import com.edu.common.bean.ShoppingResult;
import com.edu.mapper.TbContentCategoryMapper;
import com.edu.service.ContentCategoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Service
public class ContentCategoryServiceImpl implements ContentCategoryService {
    
    @Autowired
    private TbContentCategoryMapper tbContentCategoryMapper;

    @Override
    public List<EUTreeResult> getAll(long id) {
        TbContentCategoryExample example = new TbContentCategoryExample();
        example.createCriteria().andParentIdEqualTo(id);
        List<TbContentCategory> tbContentCategories = tbContentCategoryMapper.selectByExample(example);
        List<EUTreeResult> results = new ArrayList<>();
        for(TbContentCategory category:tbContentCategories){
            EUTreeResult result = new EUTreeResult();
            result.setId(category.getId());
            result.setText(category.getName());
            result.setState(category.getIsParent()?"closed":"open");
            results.add(result);
        }
        return results;
    }

    @Override
    public ShoppingResult insertContentCategory(long parentId, String name) {
        TbContentCategory tbContentCategory = new TbContentCategory();
        tbContentCategory.setParentId(parentId);
        tbContentCategory.setName(name);
        tbContentCategory.setStatus(1);
        tbContentCategory.setSortOrder(1);
        tbContentCategory.setIsParent(false);
        tbContentCategory.setCreated(new Date());
        tbContentCategory.setUpdated(new Date());
        //新增一个节点
        tbContentCategoryMapper.insertSelective(tbContentCategory);
        //判断这个节点的父节点是否是叶子节点，如果不是父节点，把这个叶子节点变为父节点
        TbContentCategory contentCategory = tbContentCategoryMapper.selectByPrimaryKey(parentId);
        if (!contentCategory.getIsParent()){
            contentCategory.setIsParent(true);
            tbContentCategoryMapper.updateByPrimaryKeySelective(contentCategory);
        }
        return ShoppingResult.ok(tbContentCategory);
    }

    @Override
    public ShoppingResult updateContentCategory(long id, String name) {
        TbContentCategory tbContentCategory = tbContentCategoryMapper.selectByPrimaryKey(id);
        tbContentCategory.setName(name);
        tbContentCategory.setUpdated(new Date());
        tbContentCategoryMapper.updateByPrimaryKeySelective(tbContentCategory);
        return ShoppingResult.ok();
    }

    @Override
    public ShoppingResult deleteContentCategory(long id) {
        TbContentCategory tbContentCategory = tbContentCategoryMapper.selectByPrimaryKey(id);
        if (tbContentCategory.getIsParent()){
            caseCodeAll(id);
            deleteContentCategory(id);
        }else {
            tbContentCategoryMapper.deleteByPrimaryKey(id);
            updateCodeAll(tbContentCategory.getParentId());
        }
        return ShoppingResult.ok();
    }

    private void caseCodeAll(long id) {
        TbContentCategoryExample example = new TbContentCategoryExample();
        TbContentCategoryExample.Criteria criteria = example.createCriteria();
        criteria.andParentIdEqualTo(id);
        List<TbContentCategory> tbContentCategory = tbContentCategoryMapper.selectByExample(example);
        for (TbContentCategory category : tbContentCategory){
            if (category.getIsParent()){
                caseCodeAll(category.getId());
            }else {
                tbContentCategoryMapper.deleteByPrimaryKey(category.getId());
                updateCodeAll(category.getParentId());
            }
        }
    }

    //判断父节点下面是否还有子节点，如果没有修改父节点的isParent为false
    private void updateCodeAll(long id) {
        TbContentCategory tbContentCategory = tbContentCategoryMapper.selectByPrimaryKey(id);
        TbContentCategoryExample example = new TbContentCategoryExample();
        TbContentCategoryExample.Criteria criteria = example.createCriteria();
        criteria.andParentIdEqualTo(tbContentCategory.getId());
        List<TbContentCategory> list = tbContentCategoryMapper.selectByExample(example);
        if (list.size()<=0){
            tbContentCategory.setIsParent(false);
            tbContentCategory.setUpdated(new Date());
            tbContentCategoryMapper.updateByPrimaryKeySelective(tbContentCategory);
        }
    }

}
