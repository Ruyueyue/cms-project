package org.ruyue.cms.service;


import org.apache.commons.lang.ArrayUtils;
import org.ruyue.basic.model.Pager;
import org.ruyue.cms.dao.IGroupDao;
import org.ruyue.cms.dao.IRoleDao;
import org.ruyue.cms.dao.IUserDao;
import org.ruyue.cms.model.CmsException;
import org.ruyue.cms.model.Group;
import org.ruyue.cms.model.Role;
import org.ruyue.cms.model.User;
import org.springframework.stereotype.Service;

import javax.inject.Inject;
import java.util.Date;
import java.util.List;

/**
 * @program: cms-parent
 * @description: 实现
 * @author: Ruyue Bian
 * @create: 2019-05-20 22:08
 */
@Service("userService")
public class UserService implements IUserService{
    private IUserDao userDao;
    private IRoleDao roleDao;
    private IGroupDao groupDao;

    public IUserDao getUserDao() {
        return userDao;
    }

    @Inject  //注入
    public void setUserDao(IUserDao userDao) {
        this.userDao = userDao;
    }

    public IRoleDao getRoleDao() {
        return roleDao;
    }

    @Inject
    public void setRoleDao(IRoleDao roleDao) {
        this.roleDao = roleDao;
    }

    public IGroupDao getGroupDao() {
        return groupDao;
    }

    @Inject
    public void setGroupDao(IGroupDao groupDao) {
        this.groupDao = groupDao;
    }

    public void addUserRore(User user,int rid){
        //1.检查角色对象是否存在,如果不存在,则抛出异常
        Role role=roleDao.load(rid);
        if (role==null) throw new CmsException("要添加的用户角色不存在");
        //2.检查用户角色对象是否已经存在,如果已经存在,则不添加
        userDao.addUserRole(user,role);
    }

    private void addUserGroup(User user,int gid){
        Group group=groupDao.load(gid);
        if (group==null) throw new CmsException("要添加的用户组不存在");
        userDao.addUserGroup(user,group);
    }

    @Override
    public void add(User user, Integer[] rids, Integer[] gids) {
        System.out.println(234);
        User tu=userDao.loadByUsername(user.getUsername());
        if (tu!=null) throw new CmsException("添加的用户对象已经存在,不能添加");
        user.setCreateDate(new Date());
        userDao.add(user);
        for (Integer rid:rids){
            this.addUserRore(user,rid);
        }
        //添加用户组对象
        for (Integer gid:gids){
            this.addUserGroup(user,gid);
        }
    }

    @Override
    public void delete(int id) {
        //TODO 需要进行用户是否有文章的判断
        //1.删除用户关联的组对象
        userDao.deleteUserGroups(id);
        //2.删除用户关联的角色对象
        userDao.deleteUserRoles(id);
        userDao.delete(id);
    }

    @Override
    public void update(User user, Integer[] rids, Integer[] gids) {
        //1.获取用户已经存在的组id和角色id
        List<Integer> erids=userDao.listUserRoleIds(user.getId());
        List<Integer> egids=userDao.listUserGroupIds(user.getId());
        //2.判断,如果erids中不存在rids,则进行添加
        for (Integer rid:rids){
            if (!erids.contains(rid)){
                //添加角色
                addUserRore(user,rid);
            }
        }
        for (Integer gid:gids){
            if (!egids.contains(gid))
                addUserGroup(user,gid);
        }
        //3.进行删除,如果要更新的不存在于角色中,则删除
        for (Integer erid:erids){
            if (!ArrayUtils.contains(rids,erid)){
                userDao.deleteUserRole(user.getId(),erid);
            }
        }
        for (Integer egid:egids){
            if (!ArrayUtils.contains(gids,egid)){
                userDao.deleteUserGroup(user.getId(),egid);
            }
        }

    }

    @Override
    public void updataStatus(int id) {
        System.out.println(123);
        User u=userDao.load(id);
        if (u==null) throw new CmsException("修改状态的用户不存在");
        if (u.getStatus()==0) u.setStatus(1);
        else u.setStatus(0);
        System.out.println(u.getStatus());
        userDao.update(u);
    }

    @Override
    public Pager<User> findUser() {
        return userDao.findUser();
    }

    @Override
    public User load(int id) {
        return userDao.load(id);
    }

    @Override
    public List<Role> listUserRoles(int id) {
        return userDao.listUserRoles(id);
    }

    @Override
    public List<Group> listUserGroups(int id) {
        return userDao.listUserGroups(id);
    }

    @Override
    public List<Integer> listUserRoleIds(int id) {
        return userDao.listUserRoleIds(id);
    }

    @Override
    public List<Integer> listUserGroupIds(int id) {
        return userDao.listUserGroupIds(id);
    }

    @Override
    public List<User> listGroupUsers(int gid) {
        return userDao.listGroupUsers(gid);
    }

    @Override
    public List<User> listRoleUsers(int rid) {
        return userDao.listRoleUsers(rid);
    }

}
