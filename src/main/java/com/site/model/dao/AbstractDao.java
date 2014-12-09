package com.site.model.dao;

import com.site.model.domain.AbstractEntity;

import java.io.Serializable;
import java.lang.reflect.InvocationTargetException;
import java.util.List;
import java.util.Map;



public interface AbstractDao<E extends AbstractEntity> {
	/**
	 * 删除指定的实体
	 * @param entity
	 */
	public void delete(E entity);
	/**
	 * 删除多个实体对象
	 * @param enttities
	 */
	public void delete(List<E> enttities);
	/**
	 * 修改指定的实体
	 * @param entity
	 */
	public void update(E entity);
	/**
	 * 新增指定的实体
	 * @param entity
	 */
	public void insert(E entity);
	
	/**
	 * 如果对应实体的Id为空,则新增,否则修改
	 * @param entity
	 */
	public void saveOrUpdate(E entity) throws IllegalAccessException, InvocationTargetException ;

	/**
	 * 根据Id查询指定的对象
	 * @param id
	 * @return
	 */
	public E findById(Serializable id);
	/**
	 * 111.不分页：根据hql语句查询返回list结果
	 * @param hql  需要执行的HQL语句
	 * @return
	 */
	public List<E> findByHql(final String hql);
	/**
	 * 113.不分页：根据hql语句查询返回list结果
	 * @param hql  需要执行的HQL语句 
	 * @param condition Map<String, Object>参数
	 * @return
	 */
	public List<E> findByHql(final String hql, final Map<String, Object> condition);

	/**
	 * 131.统计记录数：根据HQL语句和数组条件
	 * @param hql  需要执行的HQL语句
	 * @return
	 */
	public Long countByHql(String hql);
	/**
	 * 133.统计记录数:根据HQL语句和Map<String, Object>条件
	 * @param hql  需要执行的HQL语句
	 * @param condition Map<String, Object>参数
	 * @return
	 */
	public Long countByHql(String hql, Map<String, Object> condition);
	
	
	/**
	 * 211.不分页：根据sql语句查询返回list结果
	 * @param sql  需要执行的SQL语句 
	 * @return
	 */
	public List<E> findBySql(final String sql);
	/**
	 * 213.不分页：根据Sql语句查询返回list结果
	 * @param sql  需要执行的SQL语句 
	 * @param condition Map<String, Object>参数
	 * @return
	 */
	public List<E> findBySql(final String sql, final Map<String, Object> condition);

	/**
	 * 231.统计记录数:根据sql语句和数组条件
	 * @param sql 需要执行的SQL语句
	 * @return
	 */
	public Long countBySql(String sql);
	/**
	 * 233.统计记录数:根据sql语句和Map<String, Object>条件
	 * @param sql 需要执行的SQL语句
	 * @param condition  Map<String, Object>参数
	 * @return
	 */
	public Long countBySql(String sql, Map<String, Object> condition);


	/**
	 * 根据原生Sql语句取出某个字段的最大值
	 * @param sql  需要执行的SQL语句
	 * @param condition
	 * @return
	 */
	public Long maxBySql(String sql, Map<String, Object> condition);
	/**
	 * 根据原生Sql语句查询返回list结果
	 * @param sql
	 * @param condition
	 * @return
	 */
	public List<E> findByNativeSqlToList(String sql, Map<String, Object> condition);
}
