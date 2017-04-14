package cat.mybatis;

import cat.mybatis.dialect.Dialect;
import lombok.extern.slf4j.Slf4j;
import org.apache.ibatis.builder.SqlSourceBuilder;
import org.apache.ibatis.cache.CacheKey;
import org.apache.ibatis.executor.Executor;
import org.apache.ibatis.executor.parameter.ParameterHandler;
import org.apache.ibatis.executor.statement.StatementHandler;
import org.apache.ibatis.mapping.BoundSql;
import org.apache.ibatis.mapping.MappedStatement;
import org.apache.ibatis.mapping.ParameterMapping;
import org.apache.ibatis.mapping.SqlSource;
import org.apache.ibatis.plugin.*;
import org.apache.ibatis.reflection.MetaObject;
import org.apache.ibatis.reflection.factory.DefaultObjectFactory;
import org.apache.ibatis.reflection.wrapper.DefaultObjectWrapperFactory;
import org.apache.ibatis.scripting.defaults.DefaultParameterHandler;
import org.apache.ibatis.session.Configuration;
import org.apache.ibatis.session.ResultHandler;
import org.apache.ibatis.session.RowBounds;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;
import java.util.Properties;



@Slf4j
@Intercepts(
		{
				@Signature(type = Executor.class, method = "query", args = {MappedStatement.class, Object.class, RowBounds.class, ResultHandler.class}),
//				@Signature(type = Executor.class, method = "query", args = {MappedStatement.class, Object.class, RowBounds.class, ResultHandler.class, CacheKey.class, BoundSql.class}),
		}
)
public class PageInterceptor implements Interceptor {
	
	@Override
	public Object intercept(Invocation invocation) throws Throwable {
		Object[] args=invocation.getArgs();
		MappedStatement mappedStatement = (MappedStatement) args[0];
		Object parameter = args[1];
		RowBounds rowBounds = (RowBounds) args[2];
		ResultHandler resultHandler = (ResultHandler) args[3];
		MetaObject metaStatementHandler = MetaObject.forObject(mappedStatement, new DefaultObjectFactory(), new DefaultObjectWrapperFactory());
		BoundSql boundSql;
		Executor executor= (Executor) invocation.getTarget();
        CacheKey cacheKey;
		//由于逻辑关系，只会进入一次
		if(args.length == 4){
			//4 个参数时
			boundSql = mappedStatement.getBoundSql(parameter);
			cacheKey = executor.createCacheKey(mappedStatement, parameter, rowBounds, boundSql);
		} else {
			//6 个参数时
			cacheKey = (CacheKey) args[4];
			boundSql = (BoundSql) args[5];
		}

        //分页
		if(parameter instanceof PageRequest){
			log.info("pagination ..");
			PageRequest pageRequest= (PageRequest) parameter;
			Configuration configuration =  mappedStatement.getConfiguration();
			String classType=configuration.getVariables().getProperty("dialect");
			Dialect dialect= (Dialect) Class.forName(classType).newInstance();
			String countSql=dialect.getCountSql(boundSql);
			String pageSql=dialect.getLimitSql(boundSql, (PageRequest) parameter);
			BoundSql pageBoundSql = new BoundSql(mappedStatement.getConfiguration(), pageSql, boundSql.getParameterMappings(), parameter);
			BoundSql countBoundSql=new BoundSql(mappedStatement.getConfiguration(), countSql, boundSql.getParameterMappings(), parameter);
			cacheKey=executor.createCacheKey(mappedStatement, parameter, rowBounds, pageBoundSql);
			List list=executor.query(mappedStatement,parameter,RowBounds.DEFAULT,resultHandler,cacheKey,pageBoundSql);
			if(list==null||list.size()==0){
				return null;
			}
			Connection connection=mappedStatement.getConfiguration().getEnvironment().getDataSource().getConnection();
			PreparedStatement preparedStatement=connection.prepareStatement(countSql);
			List<ParameterMapping> parameterMappingList=pageBoundSql.getParameterMappings();
			ParameterHandler parameterHandler = new DefaultParameterHandler(mappedStatement, pageBoundSql.getParameterObject(), countBoundSql);
			parameterHandler.setParameters(preparedStatement);
			ResultSet resultSet=preparedStatement.executeQuery();
			int count=0;
			if(resultSet.next()) count=resultSet.getInt(1);
//			if(parameterMappingList!=null&&parameterMappingList.size()>0){
//				for(int i=1;i<=parameterMappingList.size();i++){
//					ParameterMapping parameterMapping=parameterMappingList.get(i);
////					preparedStatement.setObject(i,parameterMapping.get);
//				}
//			}



			PageResult pageResult=new PageResult();
			pageResult.setPageSize(pageRequest.getPageSize());
			pageResult.setPageNum(pageRequest.getPageNum());
			pageResult.setTotalSize(count);
			pageResult.setResult(list);
			pageResult.setTotalPage(count / pageRequest.getPageSize() + count % pageRequest.getPageSize() > 0 ? 1 : 0);
			List<PageResult> pageResultList=new ArrayList<>();
			pageResultList.add(pageResult);
			return pageResultList;

		}
		//未分页
		return invocation.proceed();
	}

	@Override
	public Object plugin(Object target) {
		return Plugin.wrap(target, this);
	}

	@Override
	public void setProperties(Properties properties) {}

}