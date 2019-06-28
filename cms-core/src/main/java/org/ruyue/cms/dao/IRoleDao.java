package org.ruyue.cms.dao;

import org.ruyue.basic.dao.IBaseDao;
import org.ruyue.cms.model.Role;

import java.util.List;

public interface IRoleDao extends IBaseDao<Role> {
    public List<Role> listRole();

    public void deleteRoleUsers(int rid);

}
