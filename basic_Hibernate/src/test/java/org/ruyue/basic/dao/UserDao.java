package org.ruyue.basic.dao;

import org.ruyue.basic.model.Pager;
import org.ruyue.basic.model.User;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Map;

/**
 * @program: basic-hibernate
 * @description: 实现IUserDao
 * @author: Ruyue Bian
 * @create: 2019-05-14 18:21
 */

/*@repository用来注解接口,将接口IUserDao的一个实现类交给spring管理*/
@Repository("userDao")
public class UserDao extends BaseDao<User> implements IUserDao {
    @Override
    public List<User> listUserBySql(String s, Object[] objects, Class<User> userClass, boolean b) {
        return super.listBySql(s,objects,userClass,b);
    }

    @Override
    public List<User> listUserBySql(String s, Object[] objects, Map<String, Object> alias, Class<User> userClass, boolean b) {
        return super.listBySql(s,objects,alias,userClass,b);
    }

    @Override
    public Pager<User> findUserBySql(String s, Object[] objects, Class<User> userClass, boolean b) {
        return super.findBySql(s,objects,userClass,b);
    }

    @Override
    public Pager<User> findUserBySql(String s, Object[] objects, Map<String, Object> alias, Class<User> userClass, boolean b) {
        return super.findBySql(s,objects,alias,userClass,b);
    }
}
