package org.ruyue.basic.dao;

import com.github.springtestdbunit.DbUnitTestExecutionListener;
import com.github.springtestdbunit.annotation.DatabaseSetup;
import org.dbunit.DatabaseUnitException;
import org.dbunit.dataset.DataSetException;
import org.dbunit.dataset.IDataSet;
import org.dbunit.operation.DatabaseOperation;
import org.hibernate.ObjectNotFoundException;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.ruyue.basic.model.Pager;
import org.ruyue.basic.model.SystemContent;
import org.ruyue.basic.model.User;
import org.ruyue.basic.test.util.AbstractDbUnitTestCase;
import org.ruyue.basic.test.util.EntitiesHelper;
import org.springframework.orm.hibernate4.SessionHolder;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.TestExecutionListeners;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.support.DependencyInjectionTestExecutionListener;
import org.springframework.transaction.support.TransactionSynchronizationManager;

import static org.junit.Assert.*;

import javax.inject.Inject;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.sql.SQLException;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


/**
 * @program: basic-hibernate
 * @description: 测试类
 * @author: Ruyue Bian
 * @create: 2019-05-14 18:29
 */

//让测试在Spring容器环境下执行,比如常见的 Service  Dao  Action ， 这些个东西，都在Spring容器里，junit需要将他们拿到，并且使用来测试
    /*@RunWith：用于指定junit运行环境，是junit提供给其他框架测试环境接口扩展，为了便于使用spring的依赖注入，spring提供了org.springframework.test.context.junit4.SpringJUnit4ClassRunner作为Junit测试环境
@ContextConfiguration({"classpath:applicationContext.xml","classpath:spring/buyer/applicationContext-service.xml"}) 
导入配置文件*/
    /*试图使用TestExecutionListeners从而省略create,但事实证明不可以*/
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration("/beans.xml")
//@TestExecutionListeners({DbUnitTestExecutionListener.class, DependencyInjectionTestExecutionListener.class})   //加入这个可以不用写createDataSet
public class TestUserDao extends AbstractDbUnitTestCase {
    @Inject
    private SessionFactory sessionFactory;
    @Inject
    private IUserDao userDao;

    /*setUp()函数是在众多函数或者说是在一个类类里面最先被调用的函数，而且每执行完一个函数都要从setUp()调用开始后再执行下一个函数，
    有几个函数就调用他几次，与位置无关，随便放在那里都是他先被调用。*/
    @Before
    public void setUp() throws DataSetException, SQLException, IOException {
        Session s=sessionFactory.openSession();
        TransactionSynchronizationManager.bindResource(sessionFactory,new SessionHolder(s));//将session放入事务管理器中
        this.backupAllTable();
    }

    @Test
    //@DatabaseSetup("/t_user.xml")     //有了这句话,就不需要前两句程序了,会自动导入xml的数据,要在程序上面加入TextExecutionListencers
    public void testLoad() throws DatabaseUnitException,SQLException{
        /*创建IDataSet，通过DataSet来获取测试数据中的数据*/
        IDataSet ds=createDateSet("t_user");
        /*会将数据库中的数据清空，并且把测试数据插入*/
        DatabaseOperation.CLEAN_INSERT.execute(dbunitCon,ds);
        //从DAO中获取数据并且完成测试
        User u=userDao.load(7);
        System.out.println(u.getUsername());
        /*EntitiesHelper.assertUser(u);*/
    }

    @Test
    public void testUpdate() throws DatabaseUnitException,SQLException{
        IDataSet ds=createDateSet("t_user");
        DatabaseOperation.CLEAN_INSERT.execute(dbunitCon,ds);
        User u=userDao.load(1);
        System.out.println(u.getUsername());
        u.setUsername("user");
        System.out.println(u.getUsername());
        userDao.update(u);
    }

    @Test
    public void testAdd() throws DatabaseUnitException,SQLException{
        IDataSet ds=createDateSet("t_user");
        DatabaseOperation.CLEAN_INSERT.execute(dbunitCon,ds);
        User u=new User();
        u.setId(18);
        u.setUsername("admin18");
        User tu=userDao.add(u);
        System.out.println(tu.getUsername());
    }

    /*
    illegally attempted to associate a proxy with two open Sessions
    at BaseDao.delete(BaseDao.java:75)
	at org.ruyue.basic.dao.TestUserDao.testDelete(TestUserDao.java:82)
	load 和delete都open了session
	*/
    @Test(expected = ObjectNotFoundException.class)//如果捕获到,则证明删除成功
    public void testDelete() throws DatabaseUnitException,SQLException{
        IDataSet ds=createDateSet("t_user");
        DatabaseOperation.CLEAN_INSERT.execute(dbunitCon,ds);
        userDao.delete(1);
        User tu=userDao.load(1);//删除了1,再load1会出错
        System.out.println(tu.getUsername());
    }

    @Test
    public void testListByArgs() throws DatabaseUnitException,SQLException{
        IDataSet ds = createDateSet("t_user");
        DatabaseOperation.CLEAN_INSERT.execute(dbunitCon,ds);
        SystemContent.setOrder("desc");
        SystemContent.setSort("id");
        List<User> expected = userDao.list("from User where id>? and id<?", new Object[]{1,4});
        List<User> actuals = Arrays.asList(new User(3,"admin3"),new User(2,"admin2"));
        //assertNull(expected);
        assertTrue(expected.size()==2);
        EntitiesHelper.assertUsers(expected,actuals);
    }

    @Test
    public void testListByArgsAndAlias() throws DatabaseUnitException,SQLException{
        IDataSet ds=createDateSet("t_user");
        DatabaseOperation.CLEAN_INSERT.execute(dbunitCon,ds);
        SystemContent.setOrder("asc");//排序方式
        SystemContent.setSort("id");//排序字段
        Map<String,Object> alias=new HashMap<String, Object>();
        alias.put("ids",Arrays.asList(1,2,3,5,6,7,8,9,10));
        List<User> expected=userDao.list("from User where id>? and id<? and id in(:ids)",new Object[]{1,5},alias);
        List<User> actual= Arrays.asList(new User(2,"admin2"),new User(3,"admin3"));
        System.out.println(expected);
        //assertNull(expected);
        assertTrue(expected.size()==2);
        EntitiesHelper.assertUsers(expected,actual);//验证
    }

    //分页的情况
    @Test
    public void testFindByArgs() throws DatabaseUnitException,SQLException{
        IDataSet ds = createDateSet("t_user");
        DatabaseOperation.CLEAN_INSERT.execute(dbunitCon,ds);
        SystemContent.setOrder("desc");
        SystemContent.setSort("id");
        SystemContent.setPageSize(3);
        SystemContent.setPageOffset(0);
        Pager<User> expected = userDao.find("from User where id>=? and id<=?", new Object[]{1,10});
        List<User> actuals = Arrays.asList(new User(10,"admin10"),new User(9,"admin9"),new User(8,"admin8"));
//        assertNotNull(expected);
        assertTrue(expected.getTotal()==10);
        assertTrue(expected.getOffset()==0);
        assertTrue(expected.getSize()==3);
        EntitiesHelper.assertUsers(expected.getDatas(), actuals);
    }

    @Test
    public void testFindByArgsAndAlias() throws DatabaseUnitException,SQLException{
        IDataSet ds=createDateSet("t_user");
        DatabaseOperation.CLEAN_INSERT.execute(dbunitCon,ds);
        SystemContent.removeOrder();
        SystemContent.removeSort();
        SystemContent.setPageSize(3);
        SystemContent.setPageOffset(0);
        Map<String,Object> alias=new HashMap<String, Object>();
        alias.put("ids",Arrays.asList(1,2,4,5,6,7,8,10));
        Pager<User> expected=userDao.find("from User where id>=? and id<=? and id in(:ids)",new Object[]{1,10},alias);
        List<User> actual= Arrays.asList(new User(1,"admin1"),new User(2,"admin2"),new User(4,"admin4"));
        //assertNull(expected);
        assertTrue(expected.getTotal()==8);
        assertTrue(expected.getOffset()==0);
        assertTrue(expected.getSize()==3);
        EntitiesHelper.assertUsers(expected.getDatas(),actual);//验证
    }

    @Test
    public void testListSQLByArgs() throws DatabaseUnitException,SQLException{
        IDataSet ds = createDateSet("t_user");
        DatabaseOperation.CLEAN_INSERT.execute(dbunitCon,ds);
        SystemContent.setOrder("desc");
        SystemContent.setSort("id");
        List<User> expected = userDao.listUserBySql("select * from t_user where id>? and id<?", new Object[]{1,4},User.class,true);
        List<User> actuals = Arrays.asList(new User(3,"admin3"),new User(2,"admin2"));
        //assertNull(expected);
        assertTrue(expected.size()==2);
        EntitiesHelper.assertUsers(expected,actuals);
    }

    @Test
    public void testListSQLByArgsAndAlias() throws DatabaseUnitException,SQLException{
        IDataSet ds=createDateSet("t_user");
        DatabaseOperation.CLEAN_INSERT.execute(dbunitCon,ds);
        SystemContent.setOrder("asc");//排序方式
        SystemContent.setSort("id");//排序字段
        Map<String,Object> alias=new HashMap<String, Object>();
        alias.put("ids",Arrays.asList(1,2,3,5,6,7,8,9,10));
        List<User> expected=userDao.listUserBySql("select * from t_user where id>? and id<? and id in(:ids)",new Object[]{1,5},alias,User.class,true);
        List<User> actual= Arrays.asList(new User(2,"admin2"),new User(3,"admin3"));
        System.out.println(expected);
        //assertNull(expected);
        assertTrue(expected.size()==2);
        EntitiesHelper.assertUsers(expected,actual);//验证
    }

    //分页的情况
    @Test
    public void testFindSQLByArgs() throws DatabaseUnitException,SQLException{
        IDataSet ds = createDateSet("t_user");
        DatabaseOperation.CLEAN_INSERT.execute(dbunitCon,ds);
        SystemContent.setOrder("desc");
        SystemContent.setSort("id");
        SystemContent.setPageSize(3);
        SystemContent.setPageOffset(0);
        Pager<User> expected = userDao.findUserBySql("select * from t_user where id>=? and id<=?", new Object[]{1,10},User.class,true);
        List<User> actuals = Arrays.asList(new User(10,"admin10"),new User(9,"admin9"),new User(8,"admin8"));
//        assertNotNull(expected);
        assertTrue(expected.getTotal()==10);
        assertTrue(expected.getOffset()==0);
        assertTrue(expected.getSize()==3);
        EntitiesHelper.assertUsers(expected.getDatas(), actuals);
    }

    @Test
    public void testFindSQLByArgsAndAlias() throws DatabaseUnitException,SQLException{
        IDataSet ds=createDateSet("t_user");
        DatabaseOperation.CLEAN_INSERT.execute(dbunitCon,ds);
        SystemContent.setOrder("asc");
        SystemContent.setSort("id");
        SystemContent.setPageSize(3);
        SystemContent.setPageOffset(0);
        Map<String,Object> alias=new HashMap<String, Object>();
        alias.put("ids",Arrays.asList(1,2,4,5,6,7,8,10));
        Pager<User> expected=userDao.findUserBySql("select * from t_user where id>=? and id<=? and id in(:ids)",new Object[]{1,10},alias,User.class,true);
        List<User> actual= Arrays.asList(new User(1,"admin1"),new User(2,"admin2"),new User(4,"admin4"));
        //assertNull(expected);
        assertTrue(expected.getTotal()==8);
        assertTrue(expected.getOffset()==0);
        assertTrue(expected.getSize()==3);
        EntitiesHelper.assertUsers(expected.getDatas(),actual);//验证
    }
    /*
     *tearDown(）函数是在众多函数执行完后他才被执行，意思就是不管这个类里面有多少函数，他总是最后一个被执行的，与位置无关，
     *放在那里都行，最后不管测试函数是否执行成功都执行tearDown()方法；如果setUp()方法失败，则认为这个测试项目失败，
     * 不会执行测试函数也不执行tearDown()方法。
     * 在执行测试时将set up和tearDown里的内容注释掉
     */
    @After
    public void tearDown() throws FileNotFoundException, DatabaseUnitException,SQLException {
        SessionHolder holder=(SessionHolder) TransactionSynchronizationManager.getResource(sessionFactory);
        Session s=holder.getSession();
        s.flush();
        TransactionSynchronizationManager.unbindResource(sessionFactory);
        this.resumeTable();
    }

}
