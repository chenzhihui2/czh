package cat.mybatis.dialect;

import cat.mybatis.PageInterceptor;
import cat.mybatis.PageRequest;
import org.apache.ibatis.mapping.BoundSql;

/**
 * Created by czhtbq on 2017/4/8.
 */
public interface Dialect {
    public String getLimitSql(BoundSql boundSql, PageRequest pageRequest);
    public String getCountSql(BoundSql boundSql);
}
