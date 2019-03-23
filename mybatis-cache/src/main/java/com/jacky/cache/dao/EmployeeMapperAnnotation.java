package com.jacky.cache.dao;

import com.jacky.cache.bean.Employee;
import org.apache.ibatis.annotations.Select;


public interface EmployeeMapperAnnotation {
	
	@Select("select * from tbl_employee where id=#{id}")
	public Employee getEmpById(Integer id);
}
