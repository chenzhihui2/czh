package com.czh.persistent.pagePlugin.pagination;

public interface Dialect {
    public String getLimitSql(String sql, int offset, int limit);
    
    public String  getCountSql(String sql);
}
