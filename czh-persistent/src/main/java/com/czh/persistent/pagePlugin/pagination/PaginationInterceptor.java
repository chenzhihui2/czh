package com.czh.persistent.pagePlugin.pagination;

import org.apache.ibatis.executor.parameter.ParameterHandler;
import org.apache.ibatis.executor.resultset.ResultSetHandler;
import org.apache.ibatis.executor.statement.RoutingStatementHandler;
import org.apache.ibatis.executor.statement.StatementHandler;
import org.apache.ibatis.mapping.BoundSql;
import org.apache.ibatis.mapping.MappedStatement;
import org.apache.ibatis.plugin.*;
import org.apache.ibatis.reflection.MetaObject;
import org.apache.ibatis.reflection.factory.DefaultObjectFactory;
import org.apache.ibatis.reflection.wrapper.DefaultObjectWrapperFactory;
import org.apache.ibatis.scripting.defaults.DefaultParameterHandler;
import org.apache.log4j.Logger;
import org.springframework.util.StringUtils;

import java.sql.*;
import java.util.List;
import java.util.Properties;

/**
 * @author czh
 */
@Intercepts({
	@Signature(type = StatementHandler.class, method = "prepare", args = {Connection.class}),
    @Signature(type = ResultSetHandler.class, method = "handleResultSets", args = {Statement.class})})
public class PaginationInterceptor implements Interceptor {
	
	private Logger log= Logger.getLogger(PaginationInterceptor.class);
	
	private String dialectName;
	
	private final static DefaultObjectFactory DEFAULT_OBJECT_FACTORY=new DefaultObjectFactory();
	
	private final static DefaultObjectWrapperFactory DEFAULT_OBJECT_WRAPPER_FACTORY=new DefaultObjectWrapperFactory();
	
	private final static ThreadLocal<Page> localPage=new ThreadLocal<Page>();
	
	
	public  static void setPage(Page page){
		localPage.set(page);
	}
	
	public static Page endPage(){
		Page page=localPage.get();
		localPage.remove();
	    return page;
	}
	
	@Override
	public Object intercept(Invocation invocation) throws Throwable {
		//page为空就跳过
		if (localPage.get() == null) {  
            return invocation.proceed();  
        }  
		
        Object target=invocation.getTarget();
        if (target instanceof StatementHandler) {
             return prepare(invocation);  
        } else if (target instanceof ResultSetHandler) {
        	 Object result = invocation.proceed();  
             Page page = localPage.get();  
             page.setResult((List) result);  
             return result;  
        }  
		return invocation.proceed();
	}
	
	
    private Object prepare(Invocation invocation) throws Exception{
        
    	
		RoutingStatementHandler routing=(RoutingStatementHandler) invocation.getTarget();
		MetaObject metaStatementHandler= MetaObject.forObject(routing, DEFAULT_OBJECT_FACTORY, DEFAULT_OBJECT_WRAPPER_FACTORY);
		StatementHandler statementHandler = (StatementHandler) metaStatementHandler.getValue("delegate");
		
		Page page=localPage.get();
		
		//设置分页sql
        Dialect dialected=this.getDialect();
        BoundSql boundSql=statementHandler.getBoundSql();
        String sql=boundSql.getSql();
        String pageSql=dialected.getLimitSql(boundSql.getSql(), page.getStart(), page.getEnd());
        metaStatementHandler.setValue("delegate.boundSql.sql", pageSql);
        
        if(log.isDebugEnabled()){
        	log.debug("分页sql:"+boundSql.getSql());
        }
        
        //设置分页参数
		MappedStatement mappedStatement = (MappedStatement) metaStatementHandler.getValue("delegate.mappedStatement");
		Connection connection=(Connection) invocation.getArgs()[0];
		this.setPageParameter(sql, connection, mappedStatement, boundSql, page);
    	return invocation.proceed();
    }
    
   
    private void setPageParameter(String sql,Connection connection,MappedStatement mappedStatement,BoundSql boundSql,Page page) throws Exception{
    	  String countSql=this.getDialect().getCountSql(sql);
    	  PreparedStatement countStatement=null;
    	  ResultSet rs = null; 
    	 
    	  try{
    		  countStatement=connection.prepareStatement(countSql);
    		  BoundSql countBS=new BoundSql(mappedStatement.getConfiguration(), countSql, boundSql.getParameterMappings(), boundSql.getParameterMappings());
    		  ParameterHandler parameterHandler = new DefaultParameterHandler(mappedStatement, boundSql.getParameterObject(), countBS);
    	      parameterHandler.setParameters(countStatement);  
    	      rs=countStatement.executeQuery();
    	      int totalCount = 0;  
              if (rs.next()) {  
                  totalCount = rs.getInt(1);  
              } 
              page.setTotalSize(totalCount);
              
              //设置总页数
              if(page.getPageSize()!=0){
            	  int totalPage = totalCount / page.getPageSize() + ((totalCount % page.getPageSize() == 0) ? 0 : 1);  
                  page.setTotalPage(totalPage); 
              }else{
            	  page.setTotalPage(1);
              }
    	  }catch(SQLException e){
    		  log.error("查询记录总数错误", e);
    	  }finally{
    		  if(rs!=null){
    			  rs.close();
    		  }
    		  if(countStatement!=null){
    			  countStatement.close();
    		  }
    	  }
    }
    
    private Dialect getDialect( ) throws Exception{
	   	 Dialect dialect=(Dialect) Class.forName(this.dialectName).newInstance();
	   	 return dialect;
   }
    
	@Override
	public Object plugin(Object target) {
		if(target instanceof StatementHandler || target instanceof ResultSetHandler){
			return Plugin.wrap(target, this);
		}
		return target;
	}

	@Override
	public void setProperties(Properties properties) {
            this.dialectName=properties.getProperty("dialect");
            if(StringUtils.isEmpty(this.dialectName)){
            	log.info("use default dialect 'common.core.pagination.OracleDialect'");
            	this.dialectName="common.core.pagination.OracleDialect";
            }
	}

}
