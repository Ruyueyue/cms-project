package org.ruyue.cms.service;

import org.ruyue.cms.dao.IRoleDao;
import org.ruyue.cms.dao.IUserDao;
import org.ruyue.cms.model.CmsException;
import org.ruyue.cms.model.Role;
import org.ruyue.cms.model.User;
import org.springframework.stereotype.Service;

import javax.inject.Inject;
import java.util.List;

/**
 * @program: cms-parent
 * @description: 实现IRoleService
 * @author: Ruyue Bian
 * @create: 2019-05-24 21:48
 */
@Service("roleService")
public class RoleService implements IRoleService{
    private IUserDao userDao;
    private IRoleDao roleDao;

    public IUserDao getUserDao() {
        return userDao;
    }

    @Inject
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

    @Override
    public void add(Role role) {
        roleDao.add(role);
    }

    @Override
    public void delete(int id) {
        List<User> users=userDao.listRoleUsers(id);
        if (users!=null&&users.size()>0) throw new CmsException("删除的角色对象中还有用户,不能删除");
        roleDao.delete(id);
    }

    @Override
    public void update(Role role) {
        roleDao.update(role);
    }

    @Override
    public Role load(int id) {
        return roleDao.load(id);
    }

    @Override
    public List<Role> listRole() {
        return roleDao.listRole();
    }

    @Override
    public void deleteRoleUsers(int rid) {
        roleDao.deleteRoleUsers(rid);
    }
}
