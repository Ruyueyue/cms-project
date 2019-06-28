package org.ruyue.cms.dao;

import org.ruyue.basic.dao.IBaseDao;
import org.ruyue.basic.model.Pager;
import org.ruyue.cms.model.*;

import java.util.List;

public interface IUserDao extends IBaseDao<User> {

   /* public void add(User user,Integer[] rids,Integer[] gids);
    *//*更新用户*//*
    public void updata(User user,Integer[] rids,Integer[] gids);*/
    /*获取用户的所有角色信息*/
    public List<Role> listUserRoles(int userId);
    /*获取用户的所有角色Id*/
    public List<Integer> listUserRoleIds(int userId);
    /*获取用户的所有组信息*/
    public  List<Group> listUserGroups(int userId);
    /*获取用户所有组Id*/
    public List<Integer> listUserGroupIds(int userId);
    /*根据用户和角色获取用户角色的关联对象*/
    public UserRole loadUserRole(int userId,int roleId);
    /*根据用户和组获取用户组的关联对象*/
    public UserGroup loadUserGroup(int userId,int groupId);
    /*根据用户名获取用户对象*/
    public User loadByUsername(String username);
    /*根据角色Id获取用户列表*/
    public List<User> listRoleUsers(int roleId);
    /*根据角色类型获取用户列表*/
    public List<User> listRoleUsers(RoleType roleType);
    /*获取某个组中的对象,显示组成员*/
    public List<User> listGroupUsers(int gid);

    /*添加用户角色对象*/
    public void addUserRole(User user,Role role);
    /*添加用户组对象*/
    public void addUserGroup(User user,Group group);
    /*删除用户角色信息*/
    public void deleteUserRoles(int uid);
    /*删除用户组信息*/
    public void deleteUserGroups(int uid);

    public Pager<User> findUser();

    /*删除用户角色对象*/
    public void deleteUserRole(int uid,int rid);
    /*删除用户组对象*/
    public void deleteUserGroup(int uid,int gid);
}
