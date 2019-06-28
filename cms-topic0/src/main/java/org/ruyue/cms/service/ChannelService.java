package org.ruyue.cms.service;

import org.ruyue.basic.model.SystemContent;
import org.ruyue.cms.dao.IChannelDao;
import org.ruyue.cms.model.Channel;
import org.ruyue.cms.model.ChannelTree;
import org.ruyue.cms.model.CmsException;
import org.springframework.stereotype.Service;

import javax.inject.Inject;
import java.util.List;

/**
 * @program: cms-parent
 * @description: 1
 * @author: Ruyue Bian
 * @create: 2019-05-29 23:01
 */
@Service("channelService")
public class ChannelService implements IChannelService{

    private IChannelDao channelDao;

    public IChannelDao getChannelDao() {
        return channelDao;
    }

    @Inject
    public void setChannelDao(IChannelDao channelDao) {
        this.channelDao = channelDao;
    }

    public void add(Channel channel, Integer pid) {
        Integer orders=channelDao.getMaxOrderByParent(pid);
        if (pid!=null&&pid>0){
            Channel pc=channelDao.load(pid);
            if (pc==null) throw new CmsException("要添加栏目的父类对象不正确");
            channel.setParent(pc);
        }
        channel.setOrders(orders+1);
        channelDao.add(channel);

    }

    public void update(Channel channel) {
        channelDao.update(channel);
    }

    public void delete(int id) {
        //TODO 1.需要判断是否存在子栏目
        List<Channel> cs=channelDao.listByParent(id);
        if (cs!=null&&cs.size()>0) throw new CmsException("要删除的栏目存在子栏目,无法删除");
        //TODO 2.判断是否存在文章
        //TODO 3.需要删除和组的关联关系
        channelDao.delete(id);
    }

    public void clearTopic(int id) {
        //TODO 实现文章之后再考虑
    }

    public Channel load(int id) {
        return channelDao.load(id);
    }

    public List<Channel> listByParent(Integer pid) {
        return channelDao.listByParent(pid);
    }


    public List<ChannelTree> generateTree() {
        return channelDao.generateTree();
    }

    public List<ChannelTree> generateTreeByParent(Integer pid) {
        return channelDao.generateTreeByParent(pid);
    }

    public void updateSort(Integer[] ids) {
        channelDao.updateSort(ids);
    }
}
