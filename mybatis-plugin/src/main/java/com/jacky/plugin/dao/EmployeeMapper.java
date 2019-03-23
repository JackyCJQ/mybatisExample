package com.jacky.plugin.dao;


import com.jacky.plugin.bean.Employee;
import com.jacky.plugin.bean.OraclePage;

import java.util.List;

public interface EmployeeMapper {

    Employee getEmpById(Integer id);

    List<Employee> getAllEmp();

    long addEmp(Employee employee);

    public void getPageByProcedure(OraclePage page);


}
