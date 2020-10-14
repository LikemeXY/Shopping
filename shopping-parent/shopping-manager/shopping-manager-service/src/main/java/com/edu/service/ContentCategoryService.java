package com.edu.service;

import com.edu.common.bean.EUTreeResult;
import com.edu.common.bean.ShoppingResult;

import java.util.List;

public interface ContentCategoryService {
    List<EUTreeResult> getAll(long id);

    ShoppingResult insertContentCategory(long parentId, String name);

    ShoppingResult updateContentCategory(long id, String name);

    ShoppingResult deleteContentCategory(long id);

}
