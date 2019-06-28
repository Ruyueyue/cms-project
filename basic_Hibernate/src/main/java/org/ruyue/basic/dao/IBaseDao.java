package org.ruyue.basic.dao;

import org.ruyue.basic.model.Pager;

import java.util.List;
import java.util.Map;

/**
 * 公共的DAO处理对象，这个对象中包含了Hibernate的所有基本操作和对SQL的操作
 */
public interface IBaseDao<T> {
    /*
     *需要有泛型，添加这个类型的对象
     */
    public T add(T t);

    /*
     *更新对象，不需要返回
     */
    public void update(T t);

    /*
     *根据id删除对象，不需要返回数据
     */
    public void delete(int id);

    /*
     *根据id加载对象
     */
    public T load(int id);

/*    *//*
     * 不分页列表对象
     * hql:查询对象的hql
     * args：查询参数
     * return：一组不分页的列表对象
     * select user from User where user.username=? 使用数组进行查询*//*
    public List<T> list(String hql, Object[] args);

    public List<T> list(String hql, Object arg);

    public List<T> list(String hql);

    *//*
     *基于别名和查询参数的混合列表
     * alias：别名
     * select role from Role where role.user.id in (:ids) and username=:username and username=?别名的查询方式
     * swssion.createQuery(hql).setParemeter(ids.123)(username,"zhangsan"
     * *//*
    public List<T> list(String hql, Object[] args, Map<String, Object> alias);   *//*别名和类型的查询*//*

    public List<T> listByAlias(String hql, Map<String, Object> alias);*//*只有别名*//*

    *//*
     * 分页列表对象
     * hql:查询对象的hql
     * args：查询参数
     * return：一组分页的列表对象
     * select user from User where user.username=? 使用数组进行查询*//*
    public Pager<T> find(String hql, Object[] args);

    public Pager<T> find(String hql, Object arg);

    public Pager<T> find(String hql);

    *//*
     *基于别名和查询参数的混合列表
     * alias：别名对象
     * select role from Role where role.user.id in (:ids) and username=:username and username=?别名的查询方式
     * swssion.createQuery(hql).setParemeter(ids.123)(username,"zhangsan"
     * *//*
    public Pager<T> find(String hql, Object[] args, Map<String, Object> alias);   *//*别名和类型的查询*//*

    public Pager<T> findByAlias(String hql, Map<String, Object> alias);*//*只有别名*//*

    *//*通过sql查询一组对象*//*
    public Object queryObject(String hql, Object[] args);

    public Object queryObject(String hql, Object arg);

    public Object queryObject(String hql);
    public Object queryObject(String hql, Object[] args, Map<String, Object> alias);
    public Object queryObjectByAlias(String hql, Map<String, Object> alias);

    *//*根据hql更新对象*//*
    public void updataByHql(String hql, Object[] args);

    public void updataByHql(String hql, Object arg);

    public void updataByHql(String hql);

    *//*当效率较高时，使用sql,又分为分页和不分页
     * 不带分页的查询
     * 根据sql查询对象，不包含关联对象（若非要写，要把多个class放在map里，关联一组对象
     * sql:查询的sql语句
     * args：查询条件
     * clz：查询的实体对象
     * hasEntity：该对象是否是一个hibernate所管理的实体，如果不是需要使用setResultTransform查新
     * return：一组对象
     *//*
    //把T改为Obeject，可在任何时候查询任何对象
    //Class<Object> clz改为 Class<?> clz 适用于任何类型,主要是后面要用到User
    public <N extends Object>List<N> listBySql(String sql, Object[] args, Class<?> clz, boolean hasEntity);

    public <N extends Object>List<N> listBySql(String sql, Object arg, Class<?> clz, boolean hasEntity);

    public <N extends Object>List<N> listBySql(String sql, Class<?> clz, boolean hasEntity);

    public <N extends Object>List<N> listBySql(String sql, Object[] args, Map<String, Object> alias, Class<?> clz, boolean hasEntity);

    public <N extends Object>List<N> listByAliasSql(String sql, Map<String, Object> alias, Class<?> clz, boolean hasEntity);

    *//*当效率较高时，使用sql,又分为分页和不分页
     * 不带分页的查询
     * 根据sql查询对象，不包含关联对象（若非要写，要把多个class放在map里，关联一组对象
     * sql:查询的sql语句
     * args：查询条件
     * clz：查询的实体对象
     * hasEntity：该对象是否是一个hibernate所管理的实体，如果不是需要使用setResultTransform查新
     * return：一组对象
     *//*
    public <N extends Object>Pager<N> findBySql(String sql, Object[] args, Class<?> clz, boolean hasEntity);

    public <N extends Object>Pager<N> findBySql(String sql, Object arg, Class<?> clz, boolean hasEntity);

    public <N extends Object>Pager<N> findBySql(String sql, Class<?> clz, boolean hasEntity);

    public <N extends Object>Pager<N> findBySql(String sql, Object[] args, Map<String, Object> alias, Class<?> clz, boolean hasEntity);

    public <N extends Object>Pager<N> findByAliasSql(String sql, Map<String, Object> alias, Class<?> clz, boolean hasEntity);*/

}
