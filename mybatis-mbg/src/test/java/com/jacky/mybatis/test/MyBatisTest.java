package com.jacky.mybatis.test;

import com.jacky.mbg.bean.Employee;
import com.jacky.mbg.bean.EmployeeExample;
import com.jacky.mbg.dao.EmployeeMapper;
import org.apache.ibatis.io.Resources;
import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.session.SqlSessionFactory;
import org.apache.ibatis.session.SqlSessionFactoryBuilder;
import org.junit.Test;

import java.io.IOException;
import java.io.InputStream;
import java.util.List;

public class MyBatisTest {


    public SqlSessionFactory getSqlSessionFactory() throws IOException {
        String resource = "mybatis-config.xml";
        InputStream inputStream = Resources.getResourceAsStream(resource);
        return new SqlSessionFactoryBuilder().build(inputStream);
    }


    @Test
    public void test() throws IOException {
        SqlSessionFactory sqlSessionFactory = getSqlSessionFactory();
        SqlSession openSession = sqlSessionFactory.openSession();
        try {
            EmployeeMapper mapper = openSession.getMapper(EmployeeMapper.class);
            Employee employee = mapper.selectByPrimaryKey(1);
            System.out.println(employee);
        } finally {
            openSession.close();
        }

    }

    @Test
    public void test02() throws IOException {
        SqlSessionFactory sqlSessionFactory = getSqlSessionFactory();
        SqlSession openSession = sqlSessionFactory.openSession();
        try {
            EmployeeMapper mapper = openSession.getMapper(EmployeeMapper.class);

            List<Employee> employees = mapper.selectByExample(null);
            System.out.println(employees);
        } finally {
            openSession.close();
        }
    }

    @Test
    public void testExample() throws IOException {
        SqlSessionFactory sqlSessionFactory = getSqlSessionFactory();
        SqlSession openSession = sqlSessionFactory.openSession();
        try {
            EmployeeMapper mapper = openSession.getMapper(EmployeeMapper.class);
            EmployeeExample example = new EmployeeExample();
            EmployeeExample.Criteria criteria = example.createCriteria();
            criteria.andLastNameLike("%ac%");
            //与前面的条件是并列的
            EmployeeExample.Criteria criteria1 = example.or();
            EmployeeExample.Criteria emailIsNotNull = criteria1.andEmailIsNotNull();
             //只能指定一次
          /*  EmployeeExample.Criteria exampleCriteria = example.createCriteria();
            exampleCriteria.andGenderEqualTo("1");*/
            //这里指定排列顺序
            example.setOrderByClause("id desc,gender asc");


            List<Employee> employees = mapper.selectByExample(example);
            System.out.println(employees);
        } finally {
            openSession.close();
        }
    }
}
