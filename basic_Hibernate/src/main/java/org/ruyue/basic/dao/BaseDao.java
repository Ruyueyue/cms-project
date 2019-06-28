package org.ruyue.basic.dao;

import org.hibernate.Query;
import org.hibernate.SQLQuery;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.transform.Transformers;
import org.ruyue.basic.model.Pager;
import org.ruyue.basic.model.SystemContent;


import javax.inject.Inject;
import java.lang.reflect.ParameterizedType;
import java.math.BigInteger;
import java.util.Collection;
import java.util.List;
import java.util.Map;
import java.util.Set;

/**
 * @program: basic-hibernate
 * @description: 增删改查方法的实现
 * @author: Ruyue
 * @create: 2019-05-13 17:21
 */
@SuppressWarnings("unchecked")
public class BaseDao<T> implements IBaseDao<T> {

    //SessionFactory接口负责初始化Hibernate。它充当数据存储源的代理，并负责创建Session对象
    private SessionFactory sessionFactory;

    //创建一个class的对象来获取泛型的class
    private Class<T> clz;

    public Class<T> getClz() {
        if (clz == null) {
            clz = ((Class<T>)
                    (((ParameterizedType) (this.getClass().getGenericSuperclass())).getActualTypeArguments()[0]));
        }
        return clz;
    }

    public void setClz(Class<T> clz) {
        this.clz = clz;
    }


    public SessionFactory getSessionFactory() {
        return sessionFactory;
    }

    //session.createQuery(hql).setParemeter(username,"zs")
    //inject 依赖注入可以解耦，让相互协作的软件组件保持松散耦合，实例不在由程序员实例化，而是通过spring容器帮我们new指定实例并且将实例注入到需要该对象的类
    @Inject
    public void setSessionFactory(SessionFactory sessionFactory) {
        this.sessionFactory = sessionFactory;
    }

    //事物管理,获取一个session
    protected Session getSession() {
        return sessionFactory.getCurrentSession();  //本来是openSession,但后面测试时load和delete双重打开session
    }

    @Override
    public T add(T t) {
        getSession().save(t);
        getSession().flush();
        return t;
    }

    @Override
    public void update(T t) {
        getSession().update(t);
        getSession().flush();
    }
    @Override
    public void delete(int id) {
        getSession().delete(this.load(id));
        getSession().flush();
    }

    @Override
    public T load(int id) {
        return (T) getSession().load(getClz(), id);
    }

    
    public List<T> list(String hql, Object[] args) {
        return this.list(hql, args, null);
    }

    
    public List<T> list(String hql, Object arg) {
        return this.list(hql, new Object[]{arg});
    }

    
    public List<T> list(String hql) {
        return this.list(hql, null);
    }

    private String initSort(String hql) {
        String order = SystemContent.getOrder();
        String sort = SystemContent.getSort();
        if(sort!=null&&!"".equals(sort.trim())) {
            hql+=" order by "+sort;
            if(!"desc".equals(order)) hql+=" asc";
            else hql+=" desc";
        }
        return hql;
    }

    @SuppressWarnings("rawtypes")
    private void setAliasParameter(Query query, Map<String, Object> alias) {
        if (alias != null) {
            Set<String> keys = alias.keySet();
            for (String key : keys) {
                Object val = alias.get(key);
                if (val instanceof Collection)
                    //查询条件是列表，集合
                    query.setParameterList(key, (Collection) val);
                else
                    query.setParameter(key, val);
            }
        }
    }

    private void setParameter(Query query, Object[] args) {
        //普通参数查询
        if (args != null && args.length > 0) {
            int index = 0;
            for (Object arg : args) {
                query.setParameter(index++, arg);
            }
        }
    }

    
    public List<T> list(String hql, Object[] args, Map<String, Object> alias) {
        /*别名和类型的查询*/
        //设置别名
        hql = initSort(hql);//初始化sort hql
        Query query = getSession().createQuery(hql);
        setAliasParameter(query, alias);
        setParameter(query, args);
        return query.list();
    }

    
    public List<T> listByAlias(String hql, Map<String, Object> alias) {/*只有别名*/
        return this.list(hql, null, alias);
    }

    
    public Pager<T> find(String hql, Object[] args) {
        return this.find(hql, args, null);
    }

    
    public Pager<T> find(String hql, Object arg) {
        return this.find(hql, new Object[]{arg});
    }

    
    public Pager<T> find(String hql) {
        return this.find(hql, null);
    }

    /*设置分页*/
    @SuppressWarnings("rawtypes")
    private void setPagers(Query query, Pager pages) {
        Integer pageSize = SystemContent.getPageSize();
        Integer pageOffset = SystemContent.getPageOffset();
        if (pageOffset < 0 || pageOffset == null) pageOffset = 0;
        if (pageSize == null || pageSize < 0) pageSize = 15;
        pages.setOffset(pageOffset);
        pages.setSize(pageSize);
        query.setFirstResult(pageOffset).setMaxResults(pageSize);
    }

    private String getCountHql(String hql, boolean isHql) {
        String end = hql.substring(hql.indexOf("from"));  //get from之后的东西
        String count = "select count(*)" + end;
        if (isHql)
            count.replaceAll("fetch", "");//fetch指定关联对象
        return count;
    }

    //分页
    
    public Pager<T> find(String hql, Object[] args, Map<String, Object> alias) {
        hql = initSort(hql);
        String cq = getCountHql(hql, true);
        System.out.println("-----------"+cq);
        Query cquery = getSession().createQuery(cq);
        Query query = getSession().createQuery(hql);
        //设置别名参数
        setAliasParameter(query, alias);
        setAliasParameter(cquery, alias);
        //设置量化参数
        setParameter(query, args);
        setParameter(cquery, args);
        //设置分页
        Pager<T> pages = new Pager<T>();
        setPagers(query, pages);
        List<T> datas = query.list();
        pages.setDatas(datas);  //select * from
        long total = (Long) cquery.uniqueResult();//返回cquery的唯一结果
        pages.setTotal(total);//select count(*) from
        return pages;
    }

    
    public Pager<T> findByAlias(String hql, Map<String, Object> alias) {
        /*只有别名*/
        return this.find(hql, null, alias);
    }

    /*通过sql查询一组对象
     * 不需要查询对象*/
    
    public Object queryObject(String hql, Object[] args) {
        return this.queryObject(hql, args, null);
    }

    
    public Object queryObject(String hql, Object arg) {
        return this.queryObject(hql, new Object[]{arg});
    }

    public Object queryObject(String hql) {
        return this.queryObject(hql, null);
    }

    
    public Object queryObject(String hql, Object[] args, Map<String, Object> alias) {
        Query query = getSession().createQuery(hql);
        setAliasParameter(query, alias);
        setParameter(query, args);
        return query.uniqueResult();
    }

    
    public Object queryObjectByAlias(String hql, Map<String, Object> alias) {
        return this.queryObject(hql, null, alias);
    }

    /*根据hql更新对象*/
    
    public void updataByHql(String hql, Object[] args) {
        Query query = getSession().createQuery(hql);
        setParameter(query, args);
        query.executeUpdate();
    }

    
    public void updataByHql(String hql, Object arg) {

        this.updataByHql(hql, new Object[]{arg});
    }

    
    public void updataByHql(String hql) {
        this.updataByHql(hql, null);
    }

    
    public <N extends Object>List<N> listBySql(String sql, Object[] args, Class<?> clz, boolean hasEntity) {
        return this.listBySql(sql, args, null, clz, hasEntity);
    }

    
    public <N extends Object>List<N> listBySql(String sql, Object arg, Class<?> clz, boolean hasEntity) {
        return this.listBySql(sql, new Object[]{arg}, null, clz, hasEntity);
    }

    
    public <N extends Object>List<N> listBySql(String sql, Class<?> clz, boolean hasEntity) {
        return this.listBySql(sql, null, clz, hasEntity);
    }

    
    public <N extends Object>List<N> listBySql(String sql, Object[] args, Map<String, Object> alias, Class<?> clz, boolean hasEntity) {
        sql = initSort(sql);
        SQLQuery sq = getSession().createSQLQuery(sql);//sql的query
        setAliasParameter(sq, alias);
        setParameter(sq, args);
        if (hasEntity) {
            //如果是hibernate所管理的实体
            sq.addEntity(clz);
        } else
            sq.setResultTransformer(Transformers.aliasToBean(clz));//把结果通过setter方法注入到指定的对像属性中
        return sq.list();
    }

    
    public <N extends Object>List<N> listByAliasSql(String sql, Map<String, Object> alias, Class<?> clz, boolean hasEntity) {
        return this.listBySql(sql, null, alias, clz, hasEntity);
    }

    /*有分页*/
    
    public <N extends Object>Pager<N> findBySql(String sql, Object[] args, Class<?> clz, boolean hasEntity) {
        return this.findBySql(sql, args, null, clz, hasEntity);
    }

    
    public <N extends Object>Pager<N> findBySql(String sql, Object arg, Class<?> clz, boolean hasEntity) {
        return this.findBySql(sql, new Object[]{arg}, clz, hasEntity);
    }

    
    public <N extends Object>Pager<N> findBySql(String sql, Class<?> clz, boolean hasEntity) {
        return this.findBySql(sql, null, clz, hasEntity);
    }

    
    public <N extends Object>Pager<N> findBySql(String sql, Object[] args, Map<String, Object> alias, Class<?> clz, boolean hasEntity) {
        sql=initSort(sql);
        String cq = getCountHql(sql, false);
        SQLQuery sq = getSession().createSQLQuery(sql);
        SQLQuery cquery = getSession().createSQLQuery(cq);
        setAliasParameter(sq, alias);
        setAliasParameter(cquery, alias);
        setParameter(sq, args);
        setParameter(cquery, args);
        Pager<N> pages = new Pager<N>();
        setPagers(sq, pages);
        if (hasEntity) {
            sq.addEntity(clz);
        } else
            sq.setResultTransformer(Transformers.aliasToBean(clz));
        List<N> datas = sq.list();
        pages.setDatas(datas);
        long total = ((BigInteger)cquery.uniqueResult()).longValue();
        pages.setTotal(total);
        return pages;
    }

    
    public <N extends Object>Pager<N> findByAliasSql(String sql, Map<String, Object> alias, Class<?> clz, boolean hasEntity) {
        return this.findBySql(sql, null, alias, clz, hasEntity);
    }

}
