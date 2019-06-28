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
import org.ruyue.basic.model.Pager;
import org.ruyue.basic.model.SystemContent;
import org.ruyue.basic.test.util.AbstractDbUnitTestCase;
import org.ruyue.basic.test.util.EntitiesHelper;
import org.ruyue.cms.model.Group;
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
 * @description: 测试group
 * @author: Ruyue Bian
 * @create: 2019-05-25 16:18
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration("/beans.xml")
public class TestGroupDao extends AbstractDbUnitTestCase {
    @Inject
    private SessionFactory sessionFactory;
    @Inject
    private IUserDao userDao;
    @Inject
    private IGroupDao groupDao;
    @Inject
    private IRoleDao roleDao;

    @Before
    public void setUp() throws SQLException, IOException, DatabaseUnitException{
        Session s=sessionFactory.openSession();
        TransactionSynchronizationManager.bindResource(sessionFactory,new SessionHolder(s));
        this.backupAllTable();
        IDataSet ds=createDateSet("t_user");
        DatabaseOperation.CLEAN_INSERT.execute(dbunitCon,ds);
    }

    @Test
    public void testListGroup(){
        List<Group> actuals= Arrays.asList(new Group(1,"财务处"),new Group(2,"计科系"),new Group(3,"宣传部"));
        List<Group> expects=groupDao.listGroup();
        EntitiesHelper.assertGroups(expects,actuals);
    }

    @Test
    public void testFindGroup(){
        SystemContent.setPageOffset(0);
        SystemContent.setPageSize(15);
        List<Group> actuals=Arrays.asList(new Group(1,"财务处"),new Group(2,"计科系"),new Group(3,"宣传部"));
        Pager<Group> pages=groupDao.findGroup();
        Assert.assertNotNull(pages);
        Assert.assertEquals(pages.getTotal(),3);
        EntitiesHelper.assertGroups(pages.getDatas(),actuals);
    }

    @Test
    public void testDeleteGroupUsers(){
        int gid=1;
        groupDao.deleteGroupUsers(gid);
        List<User> users=userDao.listGroupUsers(gid);
        Assert.assertEquals(users.size(),0);
    }
    @After
    public void tearDown() throws Exception{
        SessionHolder holder=(SessionHolder) TransactionSynchronizationManager.getResource(sessionFactory);
        Session s=holder.getSession();
        s.flush();
        TransactionSynchronizationManager.unbindResource(sessionFactory);
        this.resumeTable();
    }
}
