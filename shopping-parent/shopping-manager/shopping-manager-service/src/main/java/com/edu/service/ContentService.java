package com.edu.service;

import com.edu.bean.TbContent;
import com.edu.common.bean.EUDategridResult;
import com.edu.common.bean.ShoppingResult;

public interface ContentService {

    EUDategridResult getAll(long categoryId, int page, int rows);

    ShoppingResult insertContent(TbContent tbContent);
}
