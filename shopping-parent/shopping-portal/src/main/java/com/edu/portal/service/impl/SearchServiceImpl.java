package com.edu.portal.service.impl;

import com.edu.common.bean.SearchResult;
import com.edu.common.bean.ShoppingResult;
import com.edu.common.util.HttpClientUtil;
import com.edu.portal.service.SearchService;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.Map;

@Service
public class SearchServiceImpl implements SearchService {
    @Value("${SOLR_BASE_URL}")
    private String solrURL;
    @Override
    public SearchResult query(String q, int page) {
        Map<String,String> map = new HashMap<>();
        map.put("q",q);
        map.put("page",page+"");
        String result = HttpClientUtil.doGet(solrURL,map);
        ShoppingResult shoppingResult = ShoppingResult.formatToPojo(result, SearchResult.class);
        if (shoppingResult.getStatus()==200){
            return (SearchResult)shoppingResult.getData();
        }
        return null;
    }
}
