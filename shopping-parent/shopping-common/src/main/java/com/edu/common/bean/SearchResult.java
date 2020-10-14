package com.edu.common.bean;

import java.util.List;

public class SearchResult {
    private List<Item> itemList;//搜索出来所对应的数据信息
    private Integer currentPage;//当前页码
    private long pageCount;//总页数
    private long rowCount;//搜索出来的总条数

    public long getRowCount() {
        return rowCount;
    }

    public void setRowCount(long rowCount) {
        this.rowCount = rowCount;
    }

    public List<Item> getItemList() {
        return itemList;
    }

    public void setItemList(List<Item> itemList) {
        this.itemList = itemList;
    }

    public Integer getCurrentPage() {
        return currentPage;
    }

    public void setCurrentPage(Integer currentPage) {
        this.currentPage = currentPage;
    }

    public long getPageCount() {
        return pageCount;
    }

    public void setPageCount(long pageCount) {
        this.pageCount = pageCount;
    }

}
