package org.ruyue.cms.dao;

import org.ruyue.basic.dao.BaseDao;
import org.ruyue.cms.model.Channel;
import org.ruyue.cms.model.ChannelTree;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * @program: cms-parent
 * @description: 栏目
 * @author: Ruyue Bian
 * @create: 2019-05-29 00:01
 */
@Repository("channelDao")
public class ChannelDao extends BaseDao<Channel> implements IChannelDao {

    @Override
    public List<Channel> listByParent(Integer pid) {
        String hql = "select c from Channel c left join fetch c.parent cp where cp.id="+pid+" order by c.orders";//+"order by c.orders"
        if(pid==null||pid==0) hql = "select c from Channel c where c.parent is null order by c.orders";// order by c.orders
        return this.list(hql);
    }

    @Override
    public int getMaxOrderByParent(Integer pid) {
        String hql="select max(c.orders) from Channel c where c.parent.id="+pid;
        if (pid==null||pid==0)
            hql="select max(c.orders) from Channel c where c.parent is null";
        Object obj=this.queryObject(hql);
        if (obj==null)
            return 0;
        return (Integer)obj;
    }

    @Override
    public List<ChannelTree> generateTree() {
        String sql="select id,name,pid from t_channel order by orders";//order by orders
        List<ChannelTree> cts=this.listBySql(sql,ChannelTree.class,false);
        cts.add(0,new ChannelTree(Channel.ROOT_ID,Channel.ROOT_NAME,-1));
        for (ChannelTree ct:cts){
            if (ct.getPid()==null) ct.setPid(0);
        }
        return cts;
    }

    @Override
    public List<ChannelTree> generateTreeByParent(Integer pid) {
        if (pid==null|| pid==0){
            return this.listBySql("select id,name,pid from t_channel where pid is null order by orders",ChannelTree.class,false);// order by orders
        }else {
            return this.listBySql("select id,name,pid from t_channel where pid"+pid+" order by orders",ChannelTree.class,false);//+"order by orders"
        }
    }

    @Override
    public void updateSort(Integer[] ids) {
        int index=1;
        String hql="update Channel c set c.orders=? where c.id=?";
        for (Integer id:ids){
            this.updataByHql(hql,new Object[]{index++,id});
        }
    }
}

