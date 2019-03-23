package com.jacky.mybatis;

import com.jacky.mapper.bean.Department;
import com.jacky.mapper.bean.Employee;
import com.jacky.mapper.dao.DepartmentMapper;
import com.jacky.mapper.dao.EmployeeMapper;
import com.sun.xml.internal.ws.policy.privateutil.PolicyUtils;
import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.session.SqlSessionFactory;
import org.junit.Test;

import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class EmployeeMapperTest extends BaseTest {
    @Test
    public void testGetEmpByLastNameLikeReturnMap() throws Exception {
        SqlSession openSession = getSqlSession();
        try {
            EmployeeMapper mapper = openSession.getMapper(EmployeeMapper.class);
            Map<String, Employee> emp = mapper.getEmpByLastNameLikeReturnMap("jacky");
            System.out.println(emp);
        } finally {
            openSession.close();
        }
    }

    @Test
    public void testGetEmpByIdReturnMap() throws Exception {
        SqlSession openSession = getSqlSession();
        try {
            EmployeeMapper mapper = openSession.getMapper(EmployeeMapper.class);
            Map<String, Object> map = mapper.getEmpByIdReturnMap(1);
            System.out.println(map);
        } finally {
            openSession.close();
        }
    }

    @Test
    public void testGetEmpsByLastNameLike() throws Exception {
        SqlSession openSession = getSqlSession();
        try {
            EmployeeMapper mapper = openSession.getMapper(EmployeeMapper.class);
            List<Employee> emps = mapper.getEmpsByLastNameLike("jacky");
            emps.stream().forEach(System.out::println);
        } finally {
            openSession.close();
        }
    }

    @Test
    public void testGetEmpByMap() throws Exception {
        SqlSession openSession = getSqlSession();
        try {
            EmployeeMapper mapper = openSession.getMapper(EmployeeMapper.class);
            Map<String,Object>param=new HashMap<>();
            param.put("tableName","tbl_employee");
            param.put("id",1);
            param.put("lastName","jacky");
            Employee emps = mapper.getEmpByMap(param);
            System.out.println(emps);
        } finally {
            openSession.close();
        }
    }

    @Test
    public void testGetEmpByIdAndLastName() throws IOException {
        SqlSession openSession = getSqlSession();
        try {
            EmployeeMapper mapper = openSession.getMapper(EmployeeMapper.class);
            Employee emps = mapper.getEmpByIdAndLastName(1,"jacky");
            System.out.println(emps);
        } finally {
            openSession.close();
        }
    }

    @Test
    public void testGetEmpById() throws IOException{
        SqlSession openSession = getSqlSession();
        try {
            EmployeeMapper mapper = openSession.getMapper(EmployeeMapper.class);
            Employee emp = mapper.getEmpById(1);
            System.out.println(emp);
        } finally {
            openSession.close();
        }
    }

    /**
     * 测试增删改
     * 1、mybatis允许增删改直接定义以下类型返回值
     * Integer、Long、Boolean、void
     * 2、我们需要手动提交数据
     * sqlSessionFactory.openSession();===》手动提交
     * sqlSessionFactory.openSession(true);===》自动提交
     */
    @Test
    public void testAddEmp()  throws  IOException{
        //1、获取到的SqlSession不会自动提交数据
        SqlSession openSession = getSqlSession();

        try {
            EmployeeMapper mapper = openSession.getMapper(EmployeeMapper.class);
            //测试添加
            Employee employee = new Employee(null, "jerry4", null, "1");
            mapper.addEmp(employee);
            System.out.println(employee.getId());
            //测试修改
            //Employee employee = new Employee(1, "Tom", "jerry@atguigu.com", "0");
            //boolean updateEmp = mapper.updateEmp(employee);
            //System.out.println(updateEmp);
            //测试删除
            //mapper.deleteEmpById(2);
            //2、手动提交数据
            openSession.commit();
        } finally {
            openSession.close();
        }
    }

    @Test
    public void testUpdateEmp() {

    }

    @Test
    public void testDeleteEmpById() {

    }
}
