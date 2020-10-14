package com.edu.service.impl;

import com.edu.bean.TbContent;
import com.edu.bean.TbContentExample;
import com.edu.common.bean.EUDategridResult;
import com.edu.common.bean.ShoppingResult;
import com.edu.common.util.HttpClientUtil;
import com.edu.mapper.TbContentMapper;
import com.edu.service.ContentService;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;

@Service
public class ContentServiceImpl implements ContentService {
    @Autowired
    private TbContentMapper contentMapper;
    @Value("${REDIS_CACHE_BASE}")
    private String REDIS_CACHE_BASE;
    @Value("${REDIS_CACHE_BIG_PIC}")
    private String REDIS_CACHE_BIG_PIC;

    @Override
    public EUDategridResult getAll(long categoryId, int page, int rows) {
        PageHelper.startPage(page,rows);
        TbContentExample example = new TbContentExample();
        example.createCriteria().andCategoryIdEqualTo(categoryId);
        List<TbContent> itemList = contentMapper.selectByExampleWithBLOBs(example);
        PageInfo pageInfo = new PageInfo(itemList);
        EUDategridResult euDategridResult = new EUDategridResult();
        euDategridResult.setRows(pageInfo.getList());
        euDategridResult.setTotal(pageInfo.getTotal());
        return euDategridResult;
    }

    @Override
    public ShoppingResult insertContent(TbContent tbContent) {
        tbContent.setCreated(new Date());
        tbContent.setUpdated(new Date());
        contentMapper.insertSelective(tbContent);
        HttpClientUtil.doGet(REDIS_CACHE_BASE+REDIS_CACHE_BIG_PIC+tbContent.getCategoryId());
        return ShoppingResult.ok();
    }
}

