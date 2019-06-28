package org.ruyue.cms.dao;


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
import org.ruyue.basic.test.util.AbstractDbUnitTestCase;
import org.ruyue.basic.test.util.EntitiesHelper;
import org.ruyue.cms.model.*;
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
    private IUserDao userDao;
    @Inject
    private IRoleDao roleDao;
    @Inject
    private IGroupDao groupDao;

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
        User user=userDao.load(uid);
        System.out.println(user.getUsername());
    }

    @Test
    public void testAdd() throws DatabaseUnitException,SQLException{
        User user=new User(10,"admin1","123","admin1","admin1@admin.com","110",1);
        userDao.add(user);
    }

/*    @Test
    public void testDelete() throws DatabaseUnitException,SQLException{
        int uid=6;
        userDao.delete(uid);
    }*/

    @Test
    public void testUpdate(){
        User user=userDao.load(9);
        user.setStatus(0);
        userDao.update(user);
    }
    public void testListUserRoles() throws DatabaseUnitException,SQLException{
        List<Role> actuals= Arrays.asList(new Role(2,"文章发布人员", RoleType.ROLE_PUBLISH),new Role(3,"文章审核人员",RoleType.ROLE_AUDIT));
        List<Role> roles=userDao.listUserRoles(2);//role 2文章发布人员
        EntitiesHelper.assertRoles(roles,actuals);
    }

    @Test
    public void testListUserRoleIds() throws Exception{
        List<Integer> actuals=Arrays.asList(2,3);
        List<Integer> expected=userDao.listUserRoleIds(2);
        EntitiesHelper.assertObjects(expected,actuals);
    }

/*    @Test
    public void testListUserGroups() throws DatabaseUnitException,SQLException{
        List<Group> actuals= Arrays.asList(new Group(1,"财务处"),new Group(3,"宣传部"));
        List<Group> expected=userDao.listUserGroups(3);
        EntitiesHelper.assertGroups(expected,actuals);
    }*/

/*    @Test
    public void testListUserGroupIds() throws Exception{
        List<Integer> actuals=Arrays.asList(1,3);
        List<Integer> expected=userDao.listUserRoleIds(1);
        EntitiesHelper.assertObjects(expected,actuals);
    }*/

/*    @Test
    public void testLoadUserRole() throws DatabaseUnitException,SQLException{
        int uid=1;
        int rid=1;
        UserRole ur=userDao.loadUserRole(uid,rid);
        User au=new User(1,"admin1","123","admin1","admin1@admin.com","110",1);
        Role ar=new Role(1,"管理员",RoleType.ROLE_ADMIN);
        EntitiesHelper.assertUser(ur.getUser(),au);
        EntitiesHelper.assertRole(ur.getRole(),ar);
    }*/

/*    @Test
    public void testLoadUserGroup() throws DatabaseUnitException,SQLException{
        int uid=2;
        int gid=1;
        UserGroup ug=userDao.loadUserGroup(uid,gid);
        User au=new User(2,"admin2","123","admin2","admin2@admin.com","110",1);
        Group ag=new Group(1,"财务处");
        EntitiesHelper.assertUser(ug.getUser(),au);
        EntitiesHelper.assertGroup(ug.getGroup(),ag);
    }*/

 /*   @Test
    public void testLoadByUserName() throws DatabaseUnitException,SQLException{
        User au=EntitiesHelper.getBaseUser();
        String username="admin2";
        User eu=userDao.loadByUsername(username);
        EntitiesHelper.assertUser(eu,au);
    }*/

/*    @Test
    public void testListRoleUsersByName() throws DatabaseUnitException,SQLException{
        int rid=2;
        List<User> aus=Arrays.asList(new User(2,"admin2","123","admin2","admin2@admin.com","110",1),
                new User(3,"admin3","123","admin3","admin3@admin.com","110",1));
        List<User> eus=userDao.listRoleUsers(rid);
        EntitiesHelper.assertUsers(eus,aus);
    }*/

/*    @Test
    public void testListRoleUsersByRoleType() throws DatabaseUnitException,SQLException{
        List<User> aus=Arrays.asList(new User(2,"admin2","123","admin2","admin2@admin.com","110",1),
                new User(3,"admin3","123","admin3","admin3@admin.com","110",1));
        List<User> eus=userDao.listRoleUsers(2);
        EntitiesHelper.assertUsers(eus,aus);
    }

    @Test
    public void testListGroupUsers() throws  DatabaseUnitException,SQLException{
        List<User> aus=Arrays.asList(new User(2,"admin2","123","admin2","admin2@admin.com","110",1),
                new User(3,"admin3","123","admin3","admin3@admin.com","110",1));
        List<User> eus=userDao.listGroupUsers(1);
        EntitiesHelper.assertUsers(eus,aus);
    }*/

    @Test
    public void testAddUserRole() throws DatabaseUnitException,SQLException{
        Role role=roleDao.load(1);
        User user=userDao.load(1);
        userDao.addUserRole(user,role);
        UserRole ur=userDao.loadUserRole(1,1);
        assertNotNull(ur);
        assertEquals(ur.getRole().getId(),1);
        assertEquals(ur.getUser().getId(),1);
    }

    @Test
    public void testAddUserGroup() throws DatabaseUnitException,SQLException{
        Group group=groupDao.load(1);
        User user=userDao.load(1);
        userDao.addUserGroup(user,group);
        UserGroup ug=userDao.loadUserGroup(1,1);
        assertNotNull(ug);
        assertEquals(ug.getGroup().getId(),1);
        assertEquals(ug.getUser().getId(),1);
    }

    @Test
    public void testDeleteUserRoles() throws DatabaseUnitException,SQLException{
        int uid=2;
        userDao.deleteUserRoles(uid);//删除2
        List<Role> urs=userDao.listUserRoles(uid);
        assertTrue(urs.size()<=0);
    }

    @Test
    public void testDeleteUserGroups() throws DatabaseUnitException,SQLException{
        int uid=2;
        userDao.deleteUserGroups(uid);//删除2
        List<Group> ugs=userDao.listUserGroups(uid);
        assertTrue(ugs.size()<=0);
    }

/*    @Test
    public void testFindUser(){
        SystemContent.setPageOffset(0);
        SystemContent.setPageSize(15);
        List<User> actuals=Arrays.asList(new User(1,"admin1","123","admin1","admin1@admin.com","110",1),
                new User(2,"admin2","123","admin2","admin2@admin.com","110",1),
                new User(3,"admin3","123","admin3","admin3@admin.com","110",1));
        Pager<User> pages=userDao.findUser();
        assertNotNull(pages);
        assertEquals(pages.getTotal(),3);
        EntitiesHelper.assertUsers(pages.getDatas(),actuals);
    }*/

    @Test
    public void testDeleteUserRole() throws DatabaseUnitException,SQLException{
        int uid=1;
        int rid=1;
        userDao.deleteUserRole(uid,rid);
        assertNull(userDao.loadUserRole(uid,rid));
    }

    @Test
    public void testDeleteUserGroup() throws DatabaseUnitException,SQLException{
        int uid=2;
        int gid=1;
        userDao.deleteUserGroup(uid,gid);
        assertNull(userDao.loadUserGroup(uid,gid));
    }
    /*
     *tearDown(）函数是在众多函数执行完后他才被执行，意思就是不管这个类里面有多少函数，他总是最后一个被执行的，与位置无关，
     *放在那里都行，最后不管测试函数是否执行成功都执行tearDown()方法；如果setUp()方法失败，则认为这个测试项目失败，
     * 不会执行测试函数也不执行tearDown()方法。
     * 在执行测试时将set up和tearDown里的内容注释掉
     */
    @After
    public void tearDown() throws IOException, DatabaseUnitException,SQLException {
        SessionHolder holder=(SessionHolder) TransactionSynchronizationManager.getResource(sessionFactory);
        Session s=holder.getSession();
        s.flush();
        TransactionSynchronizationManager.unbindResource(sessionFactory);
        //this.resumeTable();
    }

}
