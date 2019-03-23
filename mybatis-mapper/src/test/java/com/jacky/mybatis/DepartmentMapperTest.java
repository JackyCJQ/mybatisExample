package com.jacky.mybatis;

import com.jacky.mapper.bean.Department;
import com.jacky.mapper.bean.Employee;
import com.jacky.mapper.dao.DepartmentMapper;
import org.apache.ibatis.reflection.SystemMetaObject;
import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.session.SqlSessionFactory;
import org.junit.Test;

import java.io.IOException;

/**
 * @Authror jacky
 * @create 2019-01-30
 */
public class DepartmentMapperTest extends BaseTest {
    //单表查询
    @Test
    public void testGetDeptById() throws IOException {
        SqlSession openSession = getSqlSession();
        try {
            DepartmentMapper mapper = openSession.getMapper(DepartmentMapper.class);
            Department dept = mapper.getDeptById(1);
            System.out.println(dept);
        } finally {
            openSession.close();
        }
    }

    //一对多查询，直接查询出全部
    @Test
    public void testGetDeptByIdPlus() throws IOException {

        SqlSession openSession = getSqlSession();
        try {
            DepartmentMapper mapper = openSession.getMapper(DepartmentMapper.class);
            Department dept = mapper.getDeptByIdPlus(1);
            System.out.println(dept);
        } finally {
            openSession.close();
        }

    }

    //一对多查询，延迟加载的方式实现
    @Test
    public void testGetDeptByIdStep() throws IOException {
        SqlSession openSession = getSqlSession();
        try {
            DepartmentMapper mapper = openSession.getMapper(DepartmentMapper.class);
            Department dept = mapper.getDeptByIdStep(1);
            System.out.println(dept.getDepartmentName());
            System.out.println("我需要使用延迟加载的属性");
            dept.getEmps().forEach(System.out::println);
            System.out.println(dept);
        } finally {
            openSession.close();
        }

    }
}
