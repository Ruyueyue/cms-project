package org.ruyue.cms.dao;

import org.dbunit.DatabaseUnitException;
import org.dbunit.dataset.IDataSet;
import org.dbunit.operation.DatabaseOperation;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.ruyue.basic.test.util.AbstractDbUnitTestCase;
import org.ruyue.basic.test.util.EntitiesHelper;
import org.ruyue.cms.model.Role;
import org.ruyue.cms.model.RoleType;
import org.ruyue.cms.model.User;
import org.springframework.orm.hibernate4.SessionHolder;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.transaction.support.TransactionSynchronizationManager;

import javax.inject.Inject;
import java.io.IOException;
import java.sql.SQLException;
import java.util.Arrays;
import java.util.List;

/**
 * @program: cms-parent
 * @description: 测试Role
 * @author: Ruyue Bian
 * @create: 2019-05-25 16:18
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration("/beans.xml")
public class TestRoleDao extends AbstractDbUnitTestCase {
    @Inject
    private SessionFactory sessionFactory;
    @Inject
    private IRoleDao roleDao;
    @Inject
    private IUserDao userDao;

    @Before
    public void setUp() throws SQLException, IOException, DatabaseUnitException {
        Session s = sessionFactory.openSession();
        TransactionSynchronizationManager.bindResource(sessionFactory, new SessionHolder(s));
        this.backupAllTable();
        IDataSet ds = createDateSet("t_user");
        DatabaseOperation.CLEAN_INSERT.execute(dbunitCon,ds);
    }

    @Test
    public void testListRole(){
        List<Role> actuals= Arrays.asList(
                new Role(1,"管理员", RoleType.ROLE_ADMIN),
                new Role(2,"文章发布人员",RoleType.ROLE_PUBLISH),
                new Role(3,"文章审核人员",RoleType.ROLE_AUDIT));
        List<Role> roles=roleDao.listRole();
        EntitiesHelper.assertRoles(roles,actuals);
    }

    @Test
    public void testDeleteRoleUsers(){
        int rid=2;
        roleDao.deleteRoleUsers(rid);
        List<User> users=userDao.listRoleUsers(rid);
        Assert.assertEquals(users.size(),0);
    }

    @After
    public void tearDown() throws DatabaseUnitException, SQLException, IOException {
        SessionHolder holder = (SessionHolder) TransactionSynchronizationManager.getResource(sessionFactory);
        Session s = holder.getSession();
        s.flush();
        TransactionSynchronizationManager.unbindResource(sessionFactory);
        this.resumeTable();
    }
}
