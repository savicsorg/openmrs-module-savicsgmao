/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.openmrs.module.savicsgmao.api.dao.impl;

import java.io.Serializable;
import java.lang.reflect.Method;
import java.util.List;
import org.hibernate.Criteria;
import org.hibernate.Session;
import org.hibernate.criterion.Restrictions;
import org.openmrs.api.APIException;
import org.openmrs.api.db.hibernate.DbSession;
import org.openmrs.api.db.hibernate.DbSessionFactory;
import org.openmrs.module.addresshierarchy.AddressHierarchyEntry;
import org.openmrs.module.addresshierarchy.AddressHierarchyLevel;
import org.openmrs.module.savicsgmao.api.dao.GmaoDao;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

@Repository
public class GmaoDaoImpl<T extends Serializable> implements GmaoDao<T> {
	
	@Autowired
	DbSessionFactory dbSessionFactory;
	
	public void setDbSessionFactory(DbSessionFactory dbSessionFactory) {
		this.dbSessionFactory = dbSessionFactory;
	}
	
	/**
	 * Gets the current hibernate session while taking care of the hibernate 3 and 4 differences.
	 * 
	 * @return the current hibernate session.
	 */
	private DbSession getSession() {
		try {
			System.out.println(">>>>>> getSession = sessionFactory = " + dbSessionFactory);
			System.out.println(">>>>>> getSession = sessionFactory.getCurrentSession() = "
			        + dbSessionFactory.getCurrentSession());
			
			return dbSessionFactory.getCurrentSession();
		}
		catch (NoSuchMethodError ex) {
			try {
				Method method = dbSessionFactory.getClass().getMethod("getSession", null);
				return (DbSession) method.invoke(dbSessionFactory, null);
			}
			catch (Exception e) {
				throw new RuntimeException("Failed to get the current hibernate session", e);
			}
		}
	}
	
	public T getByUuid(Class t, String uuid) {
		return (T) getSession().createCriteria(t).add(Restrictions.eq("uuid", uuid)).uniqueResult();
	}
	
	public T saveAgent(T t) {
		getSession().saveOrUpdate(t);
		return t;
	}
	
	@Override
	public List getAll(Class t) {
		System.out.println(">>>>> Dao get all, class = " + t);
		List entityList = getSession().createCriteria(t).list();
		System.out.println(">>>>> List= " + entityList);
		return entityList;
	}
	
	@Override
	public List getAll(Class t, Integer limit, Integer offset) {
		//TODO adapt this
		Criteria criteria = getSession().createCriteria(t);
		
		if (limit != null) {
			criteria.setMaxResults(limit);
			if (offset != null) {
				criteria.setFirstResult(offset);
			}
		}
		List entityList = criteria.list();
		System.out.println(">>>>> List 2= " + entityList);
		return criteria.list();
	}
	
	@Override
	public List doSearch(Class t, String key, String value, Integer limit, Integer offset) {
		getSession().createCriteria(t).list();
		Criteria criteria = getSession().createCriteria(t);
		criteria.add(Restrictions.eq(key, value));
		return criteria.list();
	}
	
	@Override
	public T getEntity(Class t, Object id) {
		DbSession session = dbSessionFactory.getCurrentSession();
		Criteria criteria = session.createCriteria(t);
		criteria.add(Restrictions.eq("id", id));
		return (T) criteria.uniqueResult();
	}
	
	@Override
	public T getEntityByUuid(Class t, String uuid) {
		DbSession session = dbSessionFactory.getCurrentSession();
		Criteria criteria = session.createCriteria(t);
		criteria.add(Restrictions.eq("uuid", uuid));
		return (T) criteria.uniqueResult();
	}
	
	@Override
	public Serializable upsert(Serializable entity) {
		DbSession session = this.dbSessionFactory.getCurrentSession();
		session.saveOrUpdate(entity);
		session.flush();
		return entity;
	}
	
	@Override
	public void delete(Serializable entity) {
		DbSession session = this.dbSessionFactory.getCurrentSession();
		session.delete(entity);
	}
	
	@Override
	public T getEntityByid(Class<T> t, String idName, Integer id) throws APIException {
		DbSession session = dbSessionFactory.getCurrentSession();
		Criteria criteria = session.createCriteria(t);
		criteria.add(Restrictions.eq(idName, id));
		return (T) criteria.uniqueResult();
	}
}
