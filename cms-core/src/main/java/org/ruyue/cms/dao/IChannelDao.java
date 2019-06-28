package org.ruyue.cms.dao;

import org.ruyue.basic.dao.IBaseDao;
import org.ruyue.cms.model.Channel;
import org.ruyue.cms.model.ChannelTree;

import java.util.List;

public interface IChannelDao extends IBaseDao<Channel> {
    /*根据父ID获取所有的子栏目*/
    public List<Channel> listByParent(Integer pid);
    /*获取子栏目的最大排序号*/
    public int getMaxOrderByParent(Integer pid);
    /*获取所有的栏目并生成一棵完整的树,*/
    public List<ChannelTree> generateTree();
    /*根据父类对象获取子类栏目,并生成树列表*/
    public List<ChannelTree> generateTreeByParent(Integer pid);
    /*通过一个数组来完成排序*/
    public void updateSort(Integer[] ids);
}
