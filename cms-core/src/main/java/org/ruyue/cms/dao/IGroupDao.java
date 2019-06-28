package org.ruyue.cms.dao;

import org.ruyue.basic.dao.IBaseDao;
import org.ruyue.basic.model.Pager;
import org.ruyue.cms.model.Group;

import java.util.List;

/*
public interface IGroupDao extends IBaseDao<Group> {
}*/

public interface IGroupDao extends IBaseDao<Group>{
    public List<Group> listGroup();
    public Pager<Group> findGroup();
    public void deleteGroupUsers(int gid);

}