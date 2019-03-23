package com.jacky.dynamic.dao;

import com.jacky.dynamic.bean.Employee;
import com.sun.xml.internal.ws.policy.privateutil.PolicyUtils;
import org.apache.ibatis.io.Resources;
import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.session.SqlSessionFactory;
import org.apache.ibatis.session.SqlSessionFactoryBuilder;
import org.junit.Test;

import java.io.IOException;
import java.io.InputStream;
import java.util.Arrays;
import java.util.List;

import static org.junit.Assert.*;

/**
 * @Authror jacky
 * @create 2019-03-21
 */
public class EmployeeMapperDynamicSQLTest {
    public SqlSessionFactory getSqlSessionFactory() throws IOException {
        String resource = "mybatis-config.xml";
        InputStream inputStream = Resources.getResourceAsStream(resource);
        return new SqlSessionFactoryBuilder().build(inputStream);
    }

    /**
     * 测试mybatis的两个内置的参数
     *
     * @throws IOException
     */
    @Test
    public void getEmpsTestInnerParameter() throws IOException {
        SqlSessionFactory sqlSessionFactory = getSqlSessionFactory();
        SqlSession openSession = sqlSessionFactory.openSession();
        try {
            EmployeeMapperDynamicSQL mapper = openSession.getMapper(EmployeeMapperDynamicSQL.class);
            Employee employee = new Employee();
            employee.setLastName("e");
            List<Employee> list = mapper.getEmpsTestInnerParameter(employee);
            for (Employee emp : list) {
                System.out.println(emp);
            }
        } finally {
            openSession.close();
        }
    }

    @Test
    public void testDynamicSql() throws IOException {
        SqlSessionFactory sqlSessionFactory = getSqlSessionFactory();
        SqlSession openSession = sqlSessionFactory.openSession();
        try {
            EmployeeMapperDynamicSQL mapper = openSession.getMapper(EmployeeMapperDynamicSQL.class);
            Employee employee = new Employee(null, "jacky", null, null);
            //查询的时候如果某些条件没带可能sql拼装会有问题
            //1、给where后面加上1=1，以后的条件都and xxx.
            //2、mybatis使用where标签来将所有的查询条件包括在内。mybatis就会将where标签中拼装的sql，多出来的and或者or去掉
            //where只会去掉第一个多出来的and或者or。
            //测试if where
            List<Employee> emps = mapper.getEmpsByConditionIf(employee);
            emps.forEach(System.out::println);


            //测试Trim
            List<Employee> emps2 = mapper.getEmpsByConditionTrim(employee);
            for (Employee emp : emps2) {
                System.out.println(emp);
            }


            //测试choose
            List<Employee> list = mapper.getEmpsByConditionChoose(employee);
            for (Employee emp : list) {
                System.out.println(emp);
            }

            //测试set标签
            mapper.updateEmp(employee);
            openSession.commit();
            //测试foreach标签
            List<Employee> list1 = mapper.getEmpsByConditionForeach(Arrays.asList(1, 2));
              list1.forEach(System.out::println);

        } finally {
            openSession.close();
        }
    }
}