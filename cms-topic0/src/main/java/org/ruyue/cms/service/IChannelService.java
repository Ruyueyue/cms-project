package org.ruyue.cms.service;


import org.ruyue.cms.model.Channel;
import org.ruyue.cms.model.ChannelTree;

import java.util.List;

public interface IChannelService {

    /*添加栏目*/
    public void add(Channel channel,Integer pid);
    /*更新栏目*/
    public void update(Channel channel);
    /*删除栏目*/
    public void delete(int id);
    /*清空栏目中的文章*/
    public void clearTopic(int id);
    public Channel load(int id);
    /*根据父亲id加载栏目
    * 首先要检查SystemContext中是否存在排序,如果没有,则将orders加入*/
    public List<Channel> listByParent(Integer pid);
    public List<ChannelTree> generateTree();
    public List<ChannelTree> generateTreeByParent(Integer pid);
    public void updateSort(Integer[] ids);
}
