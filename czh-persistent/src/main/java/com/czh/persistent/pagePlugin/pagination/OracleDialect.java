package com.czh.persistent.pagePlugin.pagination;

public class OracleDialect implements Dialect {

	@Override
	public String getLimitSql(String sql, int offset, int limit) {
		sql="select * from (select t.*,rownum rownum_  from ("+sql+") t where rownum<="+limit+")  where rownum_>="+offset;
		return sql;
	}

	@Override
	public String getCountSql(String sql) {
		sql="select count(0) from ("+sql +")";
		return sql;
	}

}
