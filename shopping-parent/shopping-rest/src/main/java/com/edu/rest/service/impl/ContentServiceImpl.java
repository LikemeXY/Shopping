package com.edu.rest.service.impl;

import com.edu.bean.TbContent;
import com.edu.bean.TbContentExample;
import com.edu.common.util.JsonUtils;
import com.edu.mapper.TbContentMapper;
import com.edu.rest.dao.RedisDao;
import com.edu.rest.service.ContentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
@Service
public class ContentServiceImpl implements ContentService {
    @Autowired
    private TbContentMapper contentMapper;
    @Autowired
    private RedisDao redisDao;
    @Value("${INDEX_CONTENT_CATEGORY_CACHE}")
    private String INDEX_CONTENT_CATEGORY_CACHE;
    @Override
    public List<Map<String, Object>> list(long categoryId) {
        List<TbContent> tbContents = null;
        try {
            String hget = redisDao.hget(INDEX_CONTENT_CATEGORY_CACHE, categoryId + "");
            if (!StringUtils.isEmpty(hget)){
                tbContents = JsonUtils.jsonToList(hget,TbContent.class);
            }
        }catch (Exception e){
            e.printStackTrace();
        }
        List<Map<String, Object>> lists = new ArrayList<>();
        if (tbContents == null){
            TbContentExample example = new TbContentExample();
            example.createCriteria().andCategoryIdEqualTo(categoryId);
            tbContents = contentMapper.selectByExampleWithBLOBs(example);
            try {
                String ruslt = JsonUtils.objectToJson(tbContents);
                redisDao.hset(INDEX_CONTENT_CATEGORY_CACHE,categoryId+"",ruslt);
            }catch (Exception e){
                e.printStackTrace();
            }
        }

        for (TbContent content:tbContents){
            Map<String, Object> map = new HashMap<>();
            map.put("src",content.getPic());
            map.put("height",240);
            map.put("width",670);
            map.put("alt",content.getSubTitle());
            map.put("srcB",content.getPic2());
            map.put("heightB",550);
            map.put("widthB",240);
            map.put("href",content.getUrl());
            lists.add(map);
        }
        return lists;
    }
}
