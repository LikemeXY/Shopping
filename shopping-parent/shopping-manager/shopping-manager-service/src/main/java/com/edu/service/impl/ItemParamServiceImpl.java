package com.edu.service.impl;

import com.edu.bean.TbItemParam;
import com.edu.bean.TbItemParamExample;
import com.edu.common.bean.EUDategridResult;
import com.edu.common.bean.ShoppingResult;
import com.edu.mapper.TbItemParamMapper;
import com.edu.service.ItemParamService;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;
import java.util.List;

@Service
@Transactional
public class ItemParamServiceImpl implements ItemParamService {
    @Autowired
    private TbItemParamMapper itemParamMapper;

    @Override
    public EUDategridResult getAll(int page, int rows) {
        PageHelper.startPage(page,rows);

        List<TbItemParam> itemParamList = itemParamMapper.selectByExampleWithBLOBs(new TbItemParamExample());
        PageInfo pageInfo = new PageInfo(itemParamList);
        EUDategridResult euDategridResult = new EUDategridResult();
        euDategridResult.setRows(pageInfo.getList());
        euDategridResult.setTotal(pageInfo.getTotal());
        return euDategridResult;
    }

    @Override
    public ShoppingResult getItemParaByCategoryId(Long categoryId) {
        TbItemParamExample example = new TbItemParamExample();
        TbItemParamExample.Criteria criteria = example.createCriteria();
        criteria.andItemCatIdEqualTo(categoryId);
        List<TbItemParam> itemParamList = itemParamMapper.selectByExampleWithBLOBs(example);
        if (itemParamList != null && itemParamList.size()>0){
            return ShoppingResult.ok(itemParamList.get(0));
        }
        return ShoppingResult.build(500,"商品不存在" +
                "");
    }

    @Override
    public ShoppingResult insertParam(Long categoryId, String paramData) {
        TbItemParam tbItemParam = new TbItemParam();
        tbItemParam.setItemCatId(categoryId);
        tbItemParam.setParamData(paramData);
        tbItemParam.setCreated(new Date());
        tbItemParam.setUpdated(new Date());
        itemParamMapper.insertSelective(tbItemParam);
        return ShoppingResult.ok();
    }
}
