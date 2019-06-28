package org.ruyue.cms.service;


import org.easymock.EasyMock;
import static org.easymock.EasyMock.*;

import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.ruyue.basic.model.Pager;
import org.ruyue.cms.dao.IGroupDao;
import org.ruyue.cms.dao.IRoleDao;
import org.ruyue.cms.dao.IUserDao;
import org.ruyue.cms.dao.UserDao;
import org.ruyue.cms.model.*;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import javax.inject.Inject;
import java.util.Arrays;
import java.util.List;

/**
 * @program: cms-parent
 * @description: 测试
 * @author: Ruyue Bian
 * @create: 2019-05-22 00:42
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration("/test-beans.xml")
public class TestUserService {
    //注入
    @Inject
    private IUserService userService;
    @Inject
    private IRoleDao roleDao;
    @Inject
    private IUserDao userDao;
    @Inject
    private IGroupDao groupDao;
    private User baseUser=new User(1,"admin1","123","admin1","admin1@admin","110",0);
    @Test
    public void testDelete(){
        reset(userDao);//单例模式,每次都需要清空,不然会保存
        int uid=2;
        userDao.deleteUserGroups(uid);
        expectLastCall();//录制mock对象预期行为
        userDao.deleteUserRoles(uid);
        expectLastCall();
        userDao.delete(uid);
        expectLastCall();
        replay(userDao);//重放Mock对象，测试时以录制的对象预期行为代替真实对象的行为
        userService.delete(uid);
        verify(userDao);//验证
    }

    @Test
    public void testUpdateStatus(){
        reset(userDao);
        int uid=1;
        expect(userDao.load(uid)).andReturn(baseUser);//有用户捕获用户
        userDao.update(baseUser);
        expectLastCall();
        replay(userDao);
        userService.updataStatus(uid);
        Assert.assertEquals(baseUser.getStatus(),1);
        verify(userDao);
    }


/*    @Test(expected = CmsException.class)
    public void testUpdateStatusNoUser(){
        reset(userDao);
        int uid=5;
        expect(userDao.load(uid)).andReturn(null);//没有用户捕获空
        userDao.update(baseUser);
        expectLastCall();
        replay(userDao);
        userService.updataStatus(uid);
        Assert.assertEquals(baseUser.getStatus(),1);
        verify(userDao);
    }*/


    @Test
    public void testFindUser(){
        reset(userDao);
        expect(userDao.findUser()).andReturn(new Pager<User>());//有返回值
        /*expectLastCall();*///没有返回值
        replay(userDao);
        userService.findUser();
        verify(userDao);
    }

    @Test
    public void testAdd1(){
        reset(userDao,roleDao,groupDao);
        Integer[] rids={1,2};
        Integer[] gids={2,3};
        Role r=new Role(1,"管理员", RoleType.ROLE_ADMIN);
        Group g=new Group(2,"财务处");
        //检测用户对象为空
        expect(userDao.loadByUsername("admin1")).andReturn(null);
        expect(userDao.add(baseUser)).andReturn(baseUser);//有返回值
        expect(roleDao.load(rids[0])).andReturn(r);
        userDao.addUserRole(baseUser,r);
        expectLastCall();
        r.setId(2);
        expect(roleDao.load(rids[1])).andReturn(r);
        userDao.addUserRole(baseUser,r);
        expectLastCall();

        expect(groupDao.load(gids[0])).andReturn(g);
        userDao.addUserGroup(baseUser,g);
        expectLastCall();
        g.setId(3);
        expect(groupDao.load(gids[1])).andReturn(g);
        userDao.addUserGroup(baseUser,g);
        expectLastCall();
        replay(userDao,roleDao,groupDao);
        userService.add(baseUser,rids,gids);
        verify(userDao,roleDao,groupDao);
    }

    //用次数来代替循环
    @Test
    public void testAdd2(){
        reset(userDao,roleDao,groupDao);
        Integer[] rids={1,2,5,6};
        Integer[] gids={2,3,4};
        Role r=new Role(1,"管理员", RoleType.ROLE_ADMIN);
        Group g=new Group(2,"财务处");
        //检测用户对象不为空
        expect(userDao.loadByUsername("admin1")).andReturn(null);
        //添加role
        expect(userDao.add(baseUser)).andReturn(baseUser);//有返回值
        expect(roleDao.load(EasyMock.gt(0))).andReturn(r).times(4);//动态参数,如果是rids[0],每次调用都相同,出错
        userDao.addUserRole(baseUser,r);
        expectLastCall().times(4);

        //添加group
        expect(groupDao.load(EasyMock.gt(0))).andReturn(g).times(3);
        userDao.addUserGroup(baseUser,g);
        expectLastCall().times(3);

        replay(userDao,roleDao,groupDao);
        userService.add(baseUser,rids,gids);
        verify(userDao,roleDao,groupDao);
    }

    @Test(expected = CmsException.class)
    public void testAddHasUser(){
        reset(userDao,roleDao,groupDao);
        Integer[] rids={1,2,5,6};
        Integer[] gids={2,3,4};
        expect(userDao.loadByUsername("admin1")).andReturn(baseUser);
        replay(userDao,roleDao,groupDao);
        userService.add(baseUser,rids,gids);
        verify(userDao,roleDao,groupDao);
    }

    @Test(expected = CmsException.class)
    public void testAddNoRole(){
        reset(userDao,roleDao,groupDao);
        Integer[] rids={1,2,5,6};
        Integer[] gids={2,3,4};
        Role r=new Role(1,"管理员", RoleType.ROLE_ADMIN);
        Group g=new Group(2,"财务处");
        //检测用户对象不为空
        expect(userDao.loadByUsername("admin1")).andReturn(null);
        //添加role
        expect(userDao.add(baseUser)).andReturn(baseUser);//有返回值
        expect(roleDao.load(EasyMock.gt(0))).andReturn(null).times(4);//动态参数,如果是rids[0],每次调用都相同,出错
        userDao.addUserRole(baseUser,r);
        expectLastCall().times(4);

        //添加group
        expect(groupDao.load(EasyMock.gt(0))).andReturn(g).times(3);
        userDao.addUserGroup(baseUser,g);
        expectLastCall().times(3);

        replay(userDao,roleDao,groupDao);
        userService.add(baseUser,rids,gids);
        verify(userDao,roleDao,groupDao);
    }

    @Test(expected = CmsException.class)
    public void testAddNoGroup(){
        reset(userDao,roleDao,groupDao);
        Integer[] rids={1,2,5,6};
        Integer[] gids={2,3,4};
        Role r=new Role(1,"管理员", RoleType.ROLE_ADMIN);
        Group g=new Group(2,"财务处");
        //检测用户对象不为空
        expect(userDao.loadByUsername("admin1")).andReturn(null);
        //添加role
        expect(userDao.add(baseUser)).andReturn(baseUser);//有返回值
        expect(roleDao.load(EasyMock.gt(0))).andReturn(r).times(4);//动态参数,如果是rids[0],每次调用都相同,出错
        userDao.addUserRole(baseUser,r);
        expectLastCall().times(4);

        //添加group
        expect(groupDao.load(EasyMock.gt(0))).andReturn(null).times(3);
        userDao.addUserGroup(baseUser,g);
        expectLastCall().times(3);

        replay(userDao,roleDao,groupDao);
        userService.add(baseUser,rids,gids);
        verify(userDao,roleDao,groupDao);
    }

    @Test
    public void testUpdateUser(){
        reset(userDao,roleDao,groupDao);
        Integer[] nids={1,2};
        List<Integer> eids= Arrays.asList(2,3);//删掉3,添加1
        Role r=new Role();
        Group g=new Group();
        //验证获取存在的角色id和组id
        expect(userDao.listUserRoleIds(baseUser.getId())).andReturn(eids);
        expect(userDao.listUserGroupIds(baseUser.getId())).andReturn(eids);
        expect(roleDao.load(1)).andReturn(r);
        //验证添加角色和组是否正确
        userDao.addUserRole(baseUser,r);
        expectLastCall();
        expect(groupDao.load(1)).andReturn(g);
        userDao.addUserGroup(baseUser,g);
        expectLastCall();
        //验证删除角色和组是否正确
        userDao.deleteUserRole(baseUser.getId(),3);
        userDao.deleteUserGroup(baseUser.getId(),3);

        replay(userDao,roleDao,groupDao);
        userService.update(baseUser,nids,nids);
        verify(userDao,roleDao,groupDao);
    }


}
