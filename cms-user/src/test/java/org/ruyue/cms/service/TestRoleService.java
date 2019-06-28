package org.ruyue.cms.service;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.ruyue.cms.dao.IRoleDao;
import org.ruyue.cms.dao.IUserDao;
import org.ruyue.cms.model.CmsException;
import org.ruyue.cms.model.Role;
import org.ruyue.cms.model.RoleType;
import org.ruyue.cms.model.User;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import javax.inject.Inject;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static org.easymock.EasyMock.*;

/**
 * @program: cms-parent
 * @description: 测试
 * @author: Ruyue Bian
 * @create: 2019-05-25 18:53
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration("/test-beans.xml")
public class TestRoleService {
    @Inject
    private IRoleService roleService;
    @Inject
    private IRoleDao roleDao;
    @Inject
    private IUserDao userDao;
    private Role baseRole=new Role(1,"管理员", RoleType.ROLE_ADMIN);

    @Test
    public void testAdd(){
        reset(roleDao);
        expect(roleDao.add(baseRole)).andReturn(baseRole);
        replay(roleDao);
        roleService.add(baseRole);
        verify(roleDao);
    }

    @Test
    public void testDeleteNoUsers(){
        reset(roleDao,userDao);
        int rid=1;
        expect(userDao.listRoleUsers(rid)).andReturn(null);
        roleDao.delete(rid);
        expectLastCall();
        replay(roleDao,userDao);
        roleService.delete(rid);
        verify(roleDao,userDao);
    }

    @Test(expected = CmsException.class)
    public void testDeleteHasUsers(){
        reset(roleDao,userDao);
        int rid=1;
        List<User> users= Arrays.asList(new User());
        expect(userDao.listRoleUsers(rid)).andReturn(users);
        roleDao.delete(rid);
        expectLastCall();
        replay(roleDao,userDao);
        roleService.delete(rid);
        verify(roleDao,userDao);
    }

    @Test
    public void testLoad(){
        reset(roleDao);
        int rid=1;
        expect(roleDao.load(rid)).andReturn(baseRole);
        replay(roleDao);
        roleService.load(rid);
        verify(roleDao);
    }

    @Test
    public void testUpdate(){
        reset(roleDao);
        roleDao.update(baseRole);
        expectLastCall();
        replay(roleDao);
        roleService.update(baseRole);
        verify(roleDao);
    }

    @Test
    public void testListRole(){
        reset(roleDao);
        expect(roleDao.listRole()).andReturn(new ArrayList<Role>());
        expectLastCall();
        replay(roleDao);
        roleService.listRole();
        verify(roleDao);
    }

    @Test
    public void testDeleteRoleUsers(){
        reset(roleDao);
        int rid=1;
        roleDao.deleteRoleUsers(rid);
        expectLastCall();
        replay(roleDao);
        roleService.deleteRoleUsers(rid);
        verify(roleDao);
    }

}
