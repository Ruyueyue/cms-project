package org.ruyue.cms.service1.cms.dao;


import org.dbunit.DatabaseUnitException;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.Assert.*;
import org.ruyue.basic.model.Pager;
import org.ruyue.basic.model.SystemContent;
import org.ruyue.cms.dao.IGroupDao;
import org.ruyue.cms.dao.IRoleDao;
import org.ruyue.cms.dao.IUserDao;
import org.ruyue.cms.model.*;
import org.ruyue.cms.service.IUserService;
import org.ruyue.cms.service1.basic.test.util.AbstractDbUnitTestCase;
import org.ruyue.cms.service1.basic.test.util.EntitiesHelper;
import org.springframework.orm.hibernate4.SessionHolder;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.transaction.support.TransactionSynchronizationManager;

import javax.inject.Inject;
import java.io.IOException;
import java.sql.SQLException;
import java.util.Arrays;
import java.util.List;

import static org.junit.Assert.*;


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
public class TestUserDao extends AbstractDbUnitTestCase {
    @Inject
    private SessionFactory sessionFactory;
    @Inject
    private IUserService userService;

    /*setUp()函数是在众多函数或者说是在一个类类里面最先被调用的函数，而且每执行完一个函数都要从setUp()调用开始后再执行下一个函数，
    有几个函数就调用他几次，与位置无关，随便放在那里都是他先被调用。*/
    @Before
    public void setUp() throws DatabaseUnitException, SQLException, IOException {
        Session s=sessionFactory.openSession();
        TransactionSynchronizationManager.bindResource(sessionFactory,new SessionHolder(s));//将session放入事务管理器中
        //this.backupAllTable();
    }

    @Test
    public void testLoad() throws DatabaseUnitException,SQLException{
        int uid=1;
        User user=userService.load(uid);
        System.out.println(user.getUsername());
    }



    @Test
    public void testUpdateState(){
        userService.updataStatus(9);
    }


    @After
    public void tearDown() throws IOException, DatabaseUnitException,SQLException {
        SessionHolder holder=(SessionHolder) TransactionSynchronizationManager.getResource(sessionFactory);
        Session s=holder.getSession();
        s.flush();
        TransactionSynchronizationManager.unbindResource(sessionFactory);
        //this.resumeTable();
    }

}
