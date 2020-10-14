package com.edu.service;

import com.edu.common.bean.EUDategridResult;
import com.edu.common.bean.ShoppingResult;

public interface ItemParamService {
    EUDategridResult getAll(int page, int rows);

    ShoppingResult getItemParaByCategoryId(Long cetegoryId);

    ShoppingResult insertParam(Long categoryId, String paramData);
}
