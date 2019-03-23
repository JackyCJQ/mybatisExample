package com.jacky.mybatis;

import com.jacky.mapper.bean.Employee;
import com.jacky.mapper.dao.EmployeeMapper;
import com.jacky.mapper.dao.EmployeeMapperPlus;
import org.apache.ibatis.session.SqlSession;
import org.junit.Test;

import java.io.IOException;
import java.util.List;

/**
 * @Authror jacky
 * @create 2019-03-21
 */
public class EmployeeMapperPlusTest extends BaseTest {

    @Test
    public void getEmpById() throws IOException {
        SqlSession sqlSession = getSqlSession();
        try {
            EmployeeMapperPlus mapper = sqlSession.getMapper(EmployeeMapperPlus.class);
            Employee emps = mapper.getEmpById(1);
            System.out.println(emps);
        } finally {
            sqlSession.close();
        }

    }

    @Test
    public void getEmpAndDept() throws IOException {
        SqlSession sqlSession = getSqlSession();
        try {
            EmployeeMapperPlus mapper = sqlSession.getMapper(EmployeeMapperPlus.class);
            Employee emps = mapper.getEmpAndDept(1);
            System.out.println(emps);
        } finally {
            sqlSession.close();
        }
    }

    @Test
    public void getEmpByIdStep() throws IOException {
        SqlSession sqlSession = getSqlSession();
        try {
            EmployeeMapperPlus mapper = sqlSession.getMapper(EmployeeMapperPlus.class);
            Employee emps = mapper.getEmpByIdStep(1);
            System.out.println(emps);
        } finally {
            sqlSession.close();
        }
    }

    @Test
    public void getEmpsByDeptId() throws IOException {
        SqlSession sqlSession = getSqlSession();
        try {
            EmployeeMapperPlus mapper = sqlSession.getMapper(EmployeeMapperPlus.class);
            List<Employee> emps = mapper.getEmpsByDeptId(1);
           emps.forEach(System.out::println);
        } finally {
            sqlSession.close();
        }
    }

    @Test
    public void getAllEmployee() throws IOException{
        SqlSession sqlSession = getSqlSession();
        try {
            EmployeeMapperPlus mapper = sqlSession.getMapper(EmployeeMapperPlus.class);
            List<Employee> emps = mapper.getAllEmployee();
            emps.forEach(System.out::println);
        } finally {
            sqlSession.close();
        }    }
}