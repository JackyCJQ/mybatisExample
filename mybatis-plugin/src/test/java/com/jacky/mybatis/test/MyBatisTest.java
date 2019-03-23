package com.jacky.mybatis.test;

import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.jacky.plugin.bean.Employee;
import com.jacky.plugin.bean.OraclePage;
import com.jacky.plugin.dao.EmployeeMapper;
import org.apache.ibatis.io.Resources;
import org.apache.ibatis.session.ExecutorType;
import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.session.SqlSessionFactory;
import org.apache.ibatis.session.SqlSessionFactoryBuilder;
import org.junit.Test;

import java.io.IOException;
import java.io.InputStream;
import java.util.List;

public class MyBatisTest {


    public SqlSessionFactory getSqlSessionFactory() throws IOException {
        String resource = "mybatis-config.xml";
        InputStream inputStream = Resources.getResourceAsStream(resource);
        return new SqlSessionFactoryBuilder().build(inputStream);
    }

    /**
     * 1、获取sqlSessionFactory对象:
     * 解析文件的每一个信息保存在Configuration中，返回包含Configuration的DefaultSqlSession；
     * 注意：【MappedStatement】：代表一个增删改查的详细信息
     * <p>
     * 2、获取sqlSession对象
     * 返回一个DefaultSQlSession对象，包含Executor和Configuration;
     * 这一步会创建Executor对象；
     * <p>
     * 3、获取接口的代理对象（MapperProxy）
     * getMapper，使用MapperProxyFactory创建一个MapperProxy的代理对象
     * 代理对象里面包含了，DefaultSqlSession（Executor）
     * 4、执行增删改查方法
     * <p>
     * 总结：
     * 1、根据配置文件（全局，sql映射）初始化出Configuration对象
     * 2、创建一个DefaultSqlSession对象，
     * 他里面包含Configuration以及
     * Executor（根据全局配置文件中的defaultExecutorType创建出对应的Executor）
     * 3、DefaultSqlSession.getMapper（）：拿到Mapper接口对应的MapperProxy；
     * 4、MapperProxy里面有（DefaultSqlSession）；
     * 5、执行增删改查方法：
     * 1）、调用DefaultSqlSession的增删改查（Executor）；
     * 2）、会创建一个StatementHandler对象。
     * （同时也会创建出ParameterHandler和ResultSetHandler）
     * 3）、调用StatementHandler预编译参数以及设置参数值;
     * 使用ParameterHandler来给sql设置参数
     * 4）、调用StatementHandler的增删改查方法；
     * 5）、ResultSetHandler封装结果
     * 注意：
     * 四大对象每个创建的时候都有一个interceptorChain.pluginAll(parameterHandler);
     *
     * @throws IOException
     */
    @Test
    public void test01() throws IOException {
        // 1、获取sqlSessionFactory对象
        SqlSessionFactory sqlSessionFactory = getSqlSessionFactory();
        // 2、获取sqlSession对象
        SqlSession openSession = sqlSessionFactory.openSession();
        try {
            // 3、获取接口的实现类对象
            //会为接口自动的创建一个代理对象，代理对象去执行增删改查方法
            EmployeeMapper mapper = openSession.getMapper(EmployeeMapper.class);
            Employee employee = mapper.getEmpById(1);
            System.out.println(mapper);
            System.out.println(employee);
        } finally {
            openSession.close();
        }

    }

    /**
     * 插件原理
     * 在四大对象创建的时候
     * 1、每个创建出来的对象不是直接返回的，而是
     * 		interceptorChain.pluginAll(parameterHandler);
     * 2、获取到所有的Interceptor（拦截器）（插件需要实现的接口）；
     * 		调用interceptor.plugin(target);返回target包装后的对象
     * 3、插件机制，我们可以使用插件为目标对象创建一个代理对象；AOP（面向切面）
     * 		我们的插件可以为四大对象创建出代理对象；
     * 		代理对象就可以拦截到四大对象的每一个执行；
     *
     public Object pluginAll(Object target) {
     for (Interceptor interceptor : interceptors) {
     target = interceptor.plugin(target);
     }
     return target;
     }

     */
    /**
     * 插件编写：
     * 1、编写Interceptor的实现类
     * 2、使用@Intercepts注解完成插件签名
     * 3、将写好的插件注册到全局配置文件中
     */
    @Test
    public void testPlugin() {

    }

    @Test
    public void testPage() throws IOException {
        // 1、获取sqlSessionFactory对象
        SqlSessionFactory sqlSessionFactory = getSqlSessionFactory();
        // 2、获取sqlSession对象
        SqlSession openSession = sqlSessionFactory.openSession();
        try {
            // 3、获取接口的实现类对象
            //会为接口自动的创建一个代理对象，代理对象去执行增删改查方法
            EmployeeMapper mapper = openSession.getMapper(EmployeeMapper.class);
            Page<Object> page = PageHelper.startPage(3, 1);

            List<Employee> list = mapper.getAllEmp();

            for (Employee employee : list) {
                System.out.println(employee);
            }
            System.out.println("总的数据：" + page.getTotal());
            System.out.println("每页数据数：" + page.getPageSize());
            System.out.println("总的页码" + page.getPages());
            System.out.println("当前页码" + page.getPageNum());
            System.out.println("==============");
            PageInfo<Employee> pageInfo = new PageInfo<>(list);
            System.out.println("总的数据：" + pageInfo.getTotal());
            System.out.println("每页数据数：" + pageInfo.getPageSize());
            System.out.println("总的页码" + pageInfo.getPages());
            System.out.println("当前页码" + pageInfo.getPageNum());
            System.out.println("是否是第一页：" + pageInfo.isIsFirstPage());
            System.out.println("是否是最后一夜：" + pageInfo.isIsLastPage());
            System.out.println("==============");
            //传入连续显示多少页
            PageInfo<Employee> pinfo = new PageInfo<>(list, 5);
            System.out.println("连续显示的页码");
            int[] nums = pinfo.getNavigatepageNums();
            for (int num : nums) {
                System.out.println(num);
            }

        } finally {
            openSession.close();
        }

    }

    @Test
    public void testBatch() throws IOException {
        // 1、获取sqlSessionFactory对象
        SqlSessionFactory sqlSessionFactory = getSqlSessionFactory();
        // 2、获取sqlSession对象
        //只编译sql语句一次，每次只发送参数即可，没有长度的限制
         //如果所用simple执行器，所发送的sql会有长度的限制
        SqlSession openSession = sqlSessionFactory.openSession(ExecutorType.BATCH);
        try {
            // 3、获取接口的实现类对象
            //会为接口自动的创建一个代理对象，代理对象去执行增删改查方法
            EmployeeMapper mapper = openSession.getMapper(EmployeeMapper.class);
            long start = System.currentTimeMillis();


            for (int i = 0; i < 10000; i++) {
                mapper.addEmp(new Employee("a", "b", "1"));
            }
            openSession.commit();
            long end = System.currentTimeMillis();
            System.out.println("总共所用的时间为："+(end-start));
        } finally {
            openSession.close();
        }

    }
    /**
     * oracle分页：
     * 		借助rownum：行号；子查询；
     * 存储过程包装分页逻辑
     * @throws IOException
     */
    @Test
    public void testProcedure() throws IOException{
        SqlSessionFactory sqlSessionFactory = getSqlSessionFactory();
        SqlSession openSession = sqlSessionFactory.openSession();
        try{
            EmployeeMapper mapper = openSession.getMapper(EmployeeMapper.class);
            OraclePage page = new OraclePage();
            page.setStart(1);
            page.setEnd(5);
            mapper.getPageByProcedure(page);

            System.out.println("总记录数："+page.getCount());
            System.out.println("查出的数据："+page.getEmps().size());
            System.out.println("查出的数据："+page.getEmps());
        }finally{
            openSession.close();
        }

    }

}
