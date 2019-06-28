package org.ruyue.cms.service;

import org.ruyue.basic.model.Pager;
import org.ruyue.cms.model.Group;
import org.ruyue.cms.model.Role;
import org.ruyue.cms.model.User;

import java.util.List;

public interface IUserService {
    /*添加用户,需要判断用户名是否存在,如果存在抛出异常*/
    public void add(User user,Integer[] rids,Integer[] gids);
    /*删除用户,注意需要把用户和角色和组的对应关系删除
    * 如果存在文章,则不许删除,只能停用*/
    public void delete(int id);
    /*用户的更新,如果rids中的角色在用户中已经存在,则不做操作
    * 如果rids中的角色在用户中不存在,则添加
    * 如果用户中的角色不存在于rids中,则需要删除
    * 对于group而言相同操作*/
    public void update(User user,Integer[] rids,Integer[] gids);
    /*更新用户状态*/
    public void updataStatus(int id);
    /*列表用户,需要分页*/
    public Pager<User> findUser();
    /*获取用户信息*/
    public User load(int id);
    /*获取用户的所有角色信息*/
    public List<Role> listUserRoles(int id);
    /*获取用户的所有组信息*/
    public List<Group> listUserGroups(int id);
    /*获取用户的角色id*/
    public List<Integer> listUserRoleIds(int id);
    /*获取用户的组id*/
    public List<Integer> listUserGroupIds(int id);

    /*获取组内所有用户*/
    public List<User> listGroupUsers(int gid);
    /*获取角色内所有用户*/
    public List<User> listRoleUsers(int rid);
}
