package org.ruyue.cms.dao;

import org.ruyue.basic.dao.BaseDao;
import org.ruyue.cms.model.Role;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * @program: cms-core
 * @description: 1
 * @author: Ruyue Bian
 * @create: 2019-05-17 20:56
 */
@SuppressWarnings("unchecked")
@Repository("roleDao")
public class RoleDao extends BaseDao<Role> implements IRoleDao {

    @Override
    public List<Role> listRole() {
        return this.list("from Role");
    }

    @Override
    public void deleteRoleUsers(int rid) {
        this.updataByHql("delete UserRole ur where ur.role.id=?",rid);
    }
}
