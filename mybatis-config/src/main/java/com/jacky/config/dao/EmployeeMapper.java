package com.jacky.config.dao;


import com.jacky.config.bean.Employee;

public interface EmployeeMapper {

    Employee getEmpById(Integer id);

    Employee getEmpByIdGengral(Integer id);

}
