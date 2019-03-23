package com.jacky.helloworld.dao;

import com.jacky.helloworld.bean.Employee;
import org.apache.ibatis.annotations.MapKey;
import org.apache.ibatis.session.ResultHandler;

import java.util.Map;

public interface EmployeeMapper {

    Employee getEmpById(Integer id);
}
