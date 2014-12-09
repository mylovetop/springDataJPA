package com.site.model.dao.impl;

import java.io.Serializable;
import java.lang.reflect.InvocationTargetException;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import javax.persistence.TypedQuery;
import javax.persistence.criteria.CriteriaBuilder;

import com.site.model.dao.AbstractDao;
import com.site.model.domain.AbstractEntity;
import org.hibernate.SQLQuery;
import org.hibernate.transform.Transformers;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeanUtils;
import org.springframework.core.ResolvableType;
import org.springframework.util.ClassUtils;



/**
 * 抽象Dao实现对象
 * 其中的E表示操作的实体对象
 * @author yhm
 *
 * @param <E>
 */
public class AbstractDaoImpl<E extends AbstractEntity> implements AbstractDao<E>{
	
	@PersistenceContext()
	protected EntityManager entityManager;
	
	protected Logger logger=LoggerFactory.getLogger(getClass());
	
	protected Class<E> entityClass;
	
	/**
	 * 获取泛型的实际类型
	 */
	@SuppressWarnings("unchecked")
	public Class<E> getEntityClass() {
		ResolvableType resolvableType = ResolvableType.forClass(ClassUtils.getUserClass(getClass()));
		return (Class<E>)(Class<E>)resolvableType.as(AbstractDao.class).getGeneric(0).resolve();
	}
	public AbstractDaoImpl(){
		this.entityClass=getEntityClass();
	}
	
	protected CriteriaBuilder getCriteriaBuilder(){
		return entityManager.getCriteriaBuilder();  
	}
	@Override
	public void update(E entity) {
		E entityTemp;
		entityTemp = entityManager.find(entityClass, entity.getId());
		BeanUtils.copyProperties(entity, entityTemp);
		entityManager.merge(entityTemp);
		entityManager.flush();
	}

	@Override
	public void insert(E entity) {
		entityManager.persist(entity);
		entityManager.flush();
	}

    @Override
    public void saveOrUpdate(E entity) throws IllegalAccessException, InvocationTargetException {
        E entityTemp;
        if(entity.getId() != null){
            entityTemp = entity;
        }else {
            entityTemp = entityManager.find(entityClass, entity.getId());
            BeanUtils.copyProperties(entity, entityTemp);
        }
        entityManager.merge(entityTemp);
        entityManager.flush();
    }
	

	@Override
	public void delete(E entity) {
		entityManager.remove(entityManager.find(entityClass, entity.getId()));
		entityManager.flush();
	}
	
	@Override
	public void delete(List<E> enttities) {
		for (E entity : enttities) {
			entityManager.remove(entityManager.find(entityClass, entity.getId()));
		}
		entityManager.flush();
	}
	@Override
	public E findById(Serializable id) {
		return (E) entityManager.find(entityClass, id);
	}
	/**
	 * 不分页：根据sql查询,Map<String, Object>条件和Object[]数组条件只能任选其一,如果都有有值则以Map<String, Object>条件为准
	 * @param hql  要执行的hql语句
	 * @param condition  Map<String, Object>条件
	 * @return
	 */
	@Override
	public List<E> findByHql(String hql, Map<String, Object> condition){
		TypedQuery<E> q=entityManager.createQuery(hql, entityClass);
		if(condition!=null&&!condition.isEmpty()){
			for (String key : condition.keySet()) {
				q.setParameter(key, condition.get(key));	
			}
		}
		logger.debug("find hql:{},{}",hql,condition);
		return q.getResultList();
	}
	@Override
	public List<E> findByHql(String hql) {
		return findByHql(hql, new HashMap<String, Object>());
	}
	/**
	 * 不分页：根据SQL查询,Map<String, Object>条件和Object[]数组条件只能任选其一,如果都有有值则以Map<String, Object>条件为准
	 * @param sql  要执行的sql语句
	 * @param condition  Map<String, Object>条件
	 * @return
	 */
	@Override
	@SuppressWarnings("unchecked")
	public List<E> findBySql(String sql, Map<String, Object> condition){
		Query q=entityManager.createNativeQuery(sql, entityClass);
		if(condition!=null&&condition.size()>0){
			for (String key : condition.keySet()) {
				q.setParameter(key, condition.get(key));	
			}
		}
		logger.debug("find hql:{},{}",sql,condition);
		return (List<E>)q.getResultList();
	}
	@Override
	public List<E> findBySql(String sql) {
		return findBySql(sql, new HashMap<String, Object>());
	}

	/**
	 * 统计记录数：根据HQL语句和数组条件
	 * @param isHQL 是否是HQL语句,true:执行的是HQL语句,false：执行的是原生sql语句
	 * @param shql  SQL语句或者HQL语句:程序根据第一个参数isHQL判断
	 * @param condition Map<String, Object>条件
	 * 两个条件只能人选其1,如果两个条件都有值,则以Map<String, Object>为准,忽略:Object[]条件
	 * @return
	 */
	private Long countBySqlOrHql(boolean isHQL,String shql, Map<String, Object> condition) {
		if (shql == null) {
			return 0l;
		}
		String tmpHql =isHQL?shql:shql.toLowerCase();
		shql="select count(*) "+shql.substring(tmpHql.indexOf("from"));
		Query q = isHQL?entityManager.createQuery(shql):entityManager.createNativeQuery(shql);
		//如果传递有Map参数则使用,否则使用数组参数
		if(condition!=null&&condition.size()>0){
			for (String key : condition.keySet()) {
				q.setParameter(key, condition.get(key));	
			}
		}
		logger.debug("count(*) hql:{},{}",shql,condition);
		return Long.valueOf(q.getSingleResult().toString());
	}
	
	/**
	 * 根据原生Sql语句取出某个字段的最大值
	 * @param sql  需要执行的SQL语句 
	 * @param condition 
	 * @return
	 */
	@Override
	public Long maxBySql(String sql,Map<String, Object> condition) {
		if (sql == null) {
			return 0l;
		}
		Query q = entityManager.createNativeQuery(sql);
		//如果传递有Map参数则使用,否则使用数组参数
		if(condition!=null&&condition.size()>0){
			for (String key : condition.keySet()) {
				q.setParameter(key, condition.get(key));	
			}
		}
		logger.debug("max sql:{},{}",sql,condition);
		return Long.valueOf(q.getSingleResult().toString());
	}
	@Override
	public Long countByHql(String hql){
		return countBySqlOrHql(true, hql,new HashMap<String, Object>());
	}
	@Override
	public Long countByHql(String hql, Map<String, Object> condition){
		return countBySqlOrHql(true, hql, condition);
	}
	@Override
	public Long countBySql(String sql) {
		return countBySqlOrHql(false,sql,new HashMap<String, Object>());
	}
	@Override
	public Long countBySql(String sql, Map<String, Object> condition) {
		return countBySqlOrHql(false,sql, condition);
	}
	

	@SuppressWarnings("unchecked")
	@Override
	public List<E> findByNativeSqlToList(String sql,Map<String, Object> condition) {
		Query q = entityManager.createNativeQuery(sql); 
		q.unwrap(SQLQuery.class).setResultTransformer(Transformers.aliasToBean(entityClass)); 
		if(condition!=null&&condition.size()>0){
			for (String key : condition.keySet()) {
				q.setParameter(key, condition.get(key));	
			}
		}
		logger.debug("find sql==============:{},{}",sql,condition);
		return q.getResultList();
	}


    public void setEntityClass(Class<E> entityClass) {
		this.entityClass = entityClass;
	}
}
