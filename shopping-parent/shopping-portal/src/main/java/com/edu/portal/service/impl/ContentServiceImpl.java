package com.edu.portal.service.impl;

import com.edu.common.util.HttpClientUtil;
import com.edu.portal.service.ContentService;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

@Service
public class ContentServiceImpl implements ContentService {
    @Value("${CONTENT_BASE_URL}")
    private String baseURL;
    @Value("${BIG_PIC_URL}")
    private String picURL;

    @Override
    public String getAll() {
        String result = HttpClientUtil.doGet(baseURL+picURL);
        return result;
    }
}
