package org.ruyue.cms.dao;

import org.ruyue.basic.dao.BaseDao;
import org.ruyue.basic.model.Pager;
import org.ruyue.cms.model.*;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * @program: cms-core
 * @description: 1
 * @author: Ruyue Bian
 * @create: 2019-05-17 21:22
 */
@SuppressWarnings("unchecked")
@Repository("userDao")

public class UserDao extends BaseDao<User> implements IUserDao{

    @Override
    public List<Role> listUserRoles(int userId) {
        String hql="select ur.role from UserRole ur where ur.user.id=?";
        return this.getSession().createQuery(hql).setParameter(0,userId).list();
    }

    @Override
    public List<Integer> listUserRoleIds(int userId) {
        String hql="select ur.role.id from UserRole ur where ur.user.id=?";
        return this.getSession().createQuery(hql).setParameter(0,userId).list();
    }

    @Override
    public List<Group> listUserGroups(int userId) {
        String hql="select ug.group from UserGroup ug where ug.user.id=?";
        return this.getSession().createQuery(hql).setParameter(0,userId).list();
    }

    @Override
    public List<Integer> listUserGroupIds(int userId) {
        String hql="select ug.group.id from UserGroup ug where ug.user.id=?";
        return this.getSession().createQuery(hql).setParameter(0,userId).list();
    }

    @Override
    public UserRole loadUserRole(int userId, int roleId) {
        String hql="select ur from UserRole ur left join fetch ur.user u left join fetch ur.role r where u.id=? and r.id=?";
        return (UserRole)this.getSession().createQuery(hql).setParameter(0,userId).setParameter(1,roleId).uniqueResult();
    }

    @Override
    public UserGroup loadUserGroup(int userId, int groupId) {
        String hql="select ug from UserGroup ug left join fetch ug.user u left join fetch ug.group g where u.id=? and g.id=?";
        return (UserGroup) this.getSession().createQuery(hql).setParameter(0,userId).setParameter(1,groupId).uniqueResult();
    }

    @Override
    public User loadByUsername(String username) {
        String hql = "from User where username=?";
        return (User)this.queryObject(hql, username);
    }

    @Override
    public List<User> listRoleUsers(int roleId) {
        String hql="select ur.user from UserRole ur where ur.role.id=?";
        return this.list(hql,roleId);
    }

    @Override
    public List<User> listRoleUsers(RoleType roleType) {
        String hql="select ur.user from UserRole ur where ur.role.roleType=? ";
        return this.list(hql,roleType);
    }

    @Override
    public List<User> listGroupUsers(int gid) {
        String hql="select ug.user from UserGroup ug where ug.group.id=? ";
        return this.list(hql,gid);
    }

    @Override
    public void addUserRole(User user, Role role) {
        UserRole ur=this.loadUserRole(user.getId(),role.getId());
        if (ur!=null) return;
        ur=new UserRole();
        ur.setRole(role);
        ur.setUser(user);
        this.getSession().save(ur);
    }

    @Override
    public void addUserGroup(User user, Group group) {
        UserGroup ug=this.loadUserGroup(user.getId(),group.getId());
        if (ug!=null) return;
        ug=new UserGroup();
        ug.setGroup(group);
        ug.setUser(user);
        this.getSession().save(ug);
    }

    @Override
    public void deleteUserRoles(int uid) {
        String hql="delete UserRole ur where ur.user.id=?";
        this.updataByHql(hql,uid);
    }

    @Override
    public void deleteUserGroups(int uid) {
        String hql="delete UserGroup ug where ug.user.id=?";
        this.updataByHql(hql,uid);
    }

    @Override
    public Pager<User> findUser() {
        return this.find("from User");
    }

    @Override
    public void deleteUserRole(int uid, int rid) {
        String hql="delete UserRole ur where ur.user.id=? and ur.role.id=?";
        this.updataByHql(hql,new Object[]{uid,rid});
    }

    @Override
    public void deleteUserGroup(int uid, int gid) {
        String hql="delete UserGroup ug where ug.user.id=? and ug.group.id=?";
        this.updataByHql(hql,new Object[]{uid,gid});
    }
}
