package cat.mybatis.dialect;

import cat.mybatis.PageRequest;
import org.apache.ibatis.mapping.BoundSql;

/**
 * Created by czhtbq on 2017/4/8.
 */
public class MysqlDialect implements Dialect{
    @Override
    public String getLimitSql(BoundSql boundSql, PageRequest pageRequest) {
        if(pageRequest.getPageNum()==0){
            pageRequest.setPageNum(1);
        }
        if(pageRequest.getPageSize()==0){
            pageRequest.setPageSize(10);
        }
        int beginIndex=(pageRequest.getPageNum()-1)*pageRequest.getPageSize();
        String sql=boundSql.getSql()+"  limit "+beginIndex+","+pageRequest.getPageSize();
        return sql;
    }

    @Override
    public String getCountSql(BoundSql boundSql) {
        String sql="select count(1) from ( "+boundSql.getSql()+" ) t" ;
        return sql;
    }
}
