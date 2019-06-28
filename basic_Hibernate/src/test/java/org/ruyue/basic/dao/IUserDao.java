package org.ruyue.basic.dao;


import org.ruyue.basic.model.Pager;
import org.ruyue.basic.model.User;

import java.util.List;
import java.util.Map;

public interface IUserDao extends IBaseDao<User> {
    List<User> list(String s, Object[] objects);

    List<User> list(String s, Object[] objects, Map<String, Object> alias);

    Pager<User> find(String s, Object[] objects);

    Pager<User> find(String s, Object[] objects, Map<String, Object> alias);

    List<User> listUserBySql(String s, Object[] objects, Class<User> userClass, boolean b);

    List<User> listUserBySql(String s, Object[] objects, Map<String, Object> alias, Class<User> userClass, boolean b);

    Pager<User> findUserBySql(String s, Object[] objects, Class<User> userClass, boolean b);

    Pager<User> findUserBySql(String s, Object[] objects, Map<String, Object> alias, Class<User> userClass, boolean b);
}
