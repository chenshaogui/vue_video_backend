package com.localvideo.dev.dao;

import java.io.Serializable;
import java.lang.reflect.ParameterizedType;
import java.util.List;
import java.util.Map;

import org.hibernate.Query;
import org.hibernate.SQLQuery;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.criterion.CriteriaSpecification;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.orm.hibernate4.HibernateTemplate;


public class BaseDao<T> {

	private Class<T> clazz;

	/**
	 * 通过构造方法指定DAO的具体实现类
	 */
	@SuppressWarnings("unchecked")
	public BaseDao() {
		ParameterizedType type = (ParameterizedType) this.getClass().getGenericSuperclass();
		clazz = (Class<T>)type.getActualTypeArguments()[0];
		System.out.println("DAO的真实实现类是：" + this.clazz.getName());
	}

	/**
	 * 向DAO层注入HibernateTemplate
	 */
	@Autowired
	private HibernateTemplate hibernateTemplate;

	public int save(T entity) {
		return (int) this.hibernateTemplate.save(entity);
	}
	
	public void update(T entity) {
		this.hibernateTemplate.update(entity);
	}

	public void delete(Serializable id) {
		this.hibernateTemplate.delete(this.findById(id));
	}

	public T findById(Serializable id) {
		return (T) this.hibernateTemplate.get(this.clazz, id);
	}


	public List<?> findByHQL(String hql, Object... params) {
		return this.hibernateTemplate.find(hql, params);
	}

	public int updateByHQL(String hql, Object... params) {
		return this.hibernateTemplate.bulkUpdate(hql, params);
	}
	
	@SuppressWarnings("unchecked")
	public List<Map<String, Object>> getDB(String sql) {  
		   SessionFactory sf=hibernateTemplate.getSessionFactory();
		   Session session=sf.openSession();
		   SQLQuery query=session.createSQLQuery(sql);
		   query.setResultTransformer(CriteriaSpecification.ALIAS_TO_ENTITY_MAP);
		   List<Map<String, Object>> list = query.list();
		   return list;
		} 
	
	public int insertDB(String sql) {  
		   SessionFactory sf=hibernateTemplate.getSessionFactory();
		   Session session=sf.openSession();
		   SQLQuery query=session.createSQLQuery(sql);
		   return query.executeUpdate();
		   //return session.createSQLQuery(sql).executeUpdate();
		}
	public Session openSession() {
		 SessionFactory sf=hibernateTemplate.getSessionFactory();
		 return sf.openSession();
	}
}
