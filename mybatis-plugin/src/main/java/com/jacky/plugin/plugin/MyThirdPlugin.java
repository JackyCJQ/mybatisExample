package com.jacky.plugin.plugin;

import org.apache.ibatis.executor.statement.StatementHandler;
import org.apache.ibatis.plugin.*;
import org.apache.ibatis.reflection.MetaObject;
import org.apache.ibatis.reflection.SystemMetaObject;

import java.util.Properties;

@Intercepts({@Signature(type = StatementHandler.class, method = "parameterize", args = {java.sql.Statement.class})})
public class MyThirdPlugin implements Interceptor {
    private String id = null;

    @Override
    public Object intercept(Invocation invocation) throws Throwable {
        Object target = invocation.getTarget();
        MetaObject metaObject = SystemMetaObject.forObject(target);
        Object value = metaObject.getValue("parameterHandler.parameterObject");
        metaObject.setValue("parameterHandler.parameterObject", id);
        Object result = invocation.proceed();
        return result;
    }

    @Override
    public Object plugin(Object target) {
        Object wrap = Plugin.wrap(target, this);
        return wrap;
    }

    @Override
    public void setProperties(Properties properties) {
        id = properties.getProperty("id");

    }
}
