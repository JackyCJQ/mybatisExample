package com.jacky.mybatis;

import com.jacky.helloworld.bean.Employee;
import com.jacky.helloworld.dao.EmployeeMapper;
import org.apache.ibatis.io.Resources;
import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.session.SqlSessionFactory;
import org.apache.ibatis.session.SqlSessionFactoryBuilder;
import org.apache.log4j.Logger;
import org.junit.Before;
import org.junit.Test;

import java.io.IOException;
import java.io.InputStream;
import java.util.Map;

public class test {
    private Logger logger = org.apache.log4j.Logger.getLogger(test.class);

    private SqlSessionFactory sessionFactory;

    @Before
    public void getSqlSessionFactory() throws IOException {
        String resource = "mybatis-config.xml";
        InputStream in = null;
        try {
            in = Resources.getResourceAsStream(resource);
            sessionFactory = new SqlSessionFactoryBuilder().build(in);
        } catch (IOException e) {
            e.printStackTrace();
            logger.error("配置文件读取错误。。。");
        } finally {
            in.close();
        }
    }

    //传统的调用方式
    @Test
    public void testStatementID() throws IOException {
        SqlSession sqlSession = sessionFactory.openSession();
        try {
            Object one = sqlSession.selectOne("com.jacky.helloworld.dao.EmployeeMapper.getEmpById", 1);
            System.out.println(one);
        } finally {
            sqlSession.close();
        }
    }

    //mapper代理的方式
    @Test
    public void testMapper() throws IOException {
        //sqlSession是方法级别的
        SqlSession sqlSession = sessionFactory.openSession();
        try {
            //通过接口的方式调用方法
            EmployeeMapper mapper = sqlSession.getMapper(EmployeeMapper.class);
            //内部是生成了一个代理对象去执行，利用的是jdk的动态代理
            Employee emp = mapper.getEmpById(1);
            System.out.println(emp);
        } finally {
            sqlSession.close();
        }
    }


}
