package com.edu.service;

import com.edu.bean.TbItem;
import com.edu.common.bean.EUDategridResult;
import com.edu.common.bean.ShoppingResult;

public interface ItemService {
    EUDategridResult getAll(int page, int rows);

    ShoppingResult inserItem(TbItem item, String desc, String itemParams);
}
