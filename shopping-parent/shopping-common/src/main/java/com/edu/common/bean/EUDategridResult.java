package com.edu.common.bean;

import java.util.List;

/**
 * 用于json封装的bean
 */
public class EUDategridResult {
    public long total;
    public List<?> rows;

    public long getTotal() {
        return total;
    }

    public void setTotal(long total) {
        this.total = total;
    }

    public List<?> getRows() {
        return rows;
    }

    public void setRows(List<?> rows) {
        this.rows = rows;
    }
}
