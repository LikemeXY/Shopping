package com.edu.service.impl;

import com.edu.bean.TbItem;
import com.edu.bean.TbItemDesc;
import com.edu.bean.TbItemExample;
import com.edu.bean.TbItemParam;
import com.edu.common.bean.EUDategridResult;
import com.edu.common.bean.ShoppingResult;
import com.edu.common.util.IDUtils;
import com.edu.mapper.TbItemDescMapper;
import com.edu.mapper.TbItemMapper;
import com.edu.mapper.TbItemParamMapper;
import com.edu.service.ItemService;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;
import java.util.List;

@Service
@Transactional
public class ItemServiceImpl implements ItemService {
    @Autowired
    private TbItemMapper itemMapper;
    @Autowired
    private TbItemDescMapper itemDescMapper;
    @Autowired
    private TbItemParamMapper itemParamMapper;

    @Override
    public EUDategridResult getAll(int page, int rows) {
        PageHelper.startPage(page,rows);

        TbItemExample example = new TbItemExample();
        List<TbItem> itemList = itemMapper.selectByExample(example);
        PageInfo pageInfo = new PageInfo(itemList);
        EUDategridResult euDategridResult = new EUDategridResult();
        euDategridResult.setRows(pageInfo.getList());
        euDategridResult.setTotal(pageInfo.getTotal());
        return euDategridResult;
    }

    @Override
    public ShoppingResult inserItem(TbItem item, String desc, String itemParams) {
        long itemId = IDUtils.genItemId();
        item.setId(itemId);
        item.setStatus((byte)1);
        item.setCreated(new Date());
        item.setUpdated(new Date());
        //保存商品
        itemMapper.insertSelective(item);
        //保存商品所对应的描述信息
        ShoppingResult result = insertItemDesc(itemId, desc);
        if (result.getStatus()==200){
            result = insertItemParam(itemId, itemParams);
            if (result.getStatus()==200){
                return result;
            }
        }else {
            throw new RuntimeException();
        }
        return null;
    }

    private ShoppingResult insertItemParam(long itemId, String itemParams) {
        TbItemParam tbItemParam = new TbItemParam();
        tbItemParam.setItemCatId(itemId);
        tbItemParam.setParamData(itemParams);
        tbItemParam.setCreated(new Date());
        tbItemParam.setUpdated(new Date());
        itemParamMapper.insertSelective(tbItemParam);
        return ShoppingResult.ok();
    }

    private ShoppingResult insertItemDesc(Long itemId,String desc) {
        TbItemDesc itemDesc = new TbItemDesc();
        itemDesc.setItemId(itemId);
        itemDesc.setItemDesc(desc);
        itemDesc.setCreated(new Date());
        itemDesc.setUpdated(new Date());
        itemDescMapper.insertSelective(itemDesc);
        return  ShoppingResult.ok();

    }
}
