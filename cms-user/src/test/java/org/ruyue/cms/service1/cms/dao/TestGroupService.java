package org.ruyue.cms.service1.cms.dao;

import org.dbunit.DatabaseUnitException;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.ruyue.cms.dao.IGroupDao;
import org.ruyue.cms.dao.IUserDao;
import org.ruyue.cms.model.Group;
import org.ruyue.cms.service.IGroupService;
import org.springframework.orm.hibernate4.SessionHolder;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.transaction.support.TransactionSynchronizationManager;

import javax.inject.Inject;
import java.io.IOException;
import java.sql.SQLException;

/**
 * @program: cms-parent
 * @description: 测试
 * @author: Ruyue Bian
 * @create: 2019-05-27 15:47
 */

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration("/beans.xml")
public class TestGroupService {
    @Inject
    private SessionFactory sessionFactory;
    @Inject
    private IGroupService groupService;
    @Inject
    private IGroupDao groupDao;

    @Before
    public void setUp() throws DatabaseUnitException, SQLException, IOException {
        Session s=sessionFactory.openSession();
        TransactionSynchronizationManager.bindResource(sessionFactory,new SessionHolder(s));//将session放入事务管理器中
        //this.backupAllTable();
    }

    @Test
    public void testUpdate(){
        int gid=3;
        Group group=groupService.load(gid);
        System.out.println(group.getDescr());
        group.setDescr("学校教务处,负责某些模块嗯哼啊哈");
        System.out.println(group.getDescr());
        groupService.update(group);
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
