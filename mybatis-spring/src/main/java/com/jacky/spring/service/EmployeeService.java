package com.jacky.spring.service;

import org.apache.ibatis.session.SqlSession;
import org.springframework.beans.factory.annotation.Autowired;

public class EmployeeService {

    //自动注入一个批量添加sqlSession
    @Autowired
    private SqlSession sqlSession;


}
