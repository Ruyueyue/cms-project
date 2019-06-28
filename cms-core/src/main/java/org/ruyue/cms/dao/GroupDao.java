package org.ruyue.cms.dao;


import org.ruyue.basic.dao.BaseDao;
import org.ruyue.basic.model.Pager;
import org.ruyue.cms.model.Group;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * @program: cms-core
 * @description: 1
 * @author: Ruyue Bian
 * @create: 2019-05-17 20:58
 */
@SuppressWarnings("unchecked")
@Repository("groupDao")
public class GroupDao extends BaseDao<Group> implements IGroupDao {

    @Override
    public List<Group> listGroup() {
        return this.list("from Group");
    }

    @Override
    public Pager<Group> findGroup() {
        return this.find("from Group");
    }

    @Override
    public void deleteGroupUsers(int gid) {
        this.updataByHql("delete UserGroup ug where ug.group.id=?",gid);
    }
}
