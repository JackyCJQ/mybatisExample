package com.jacky.mapper.dao;


import com.jacky.mapper.bean.Department;

public interface DepartmentMapper {

    Department getDeptById(Integer id);

    Department getDeptByIdPlus(Integer id);

    Department getDeptByIdStep(Integer id);
}
