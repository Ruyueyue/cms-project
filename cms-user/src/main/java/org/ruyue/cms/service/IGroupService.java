package org.ruyue.cms.service;

import org.ruyue.basic.model.Pager;
import org.ruyue.cms.model.Group;

import java.util.List;

public interface IGroupService {
    public void add(Group group);
    public void delete(int id);
    public Group load(int id);
    public void update(Group group);

    public List<Group> listGroup();
    public Pager<Group> findGroup();
    public void deleteGroupUsers(int gid);//删除组中的用户

}
