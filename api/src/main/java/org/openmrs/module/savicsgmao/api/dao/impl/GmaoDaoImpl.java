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
import org.hibernate.criterion.Restrictions;
import org.openmrs.api.db.hibernate.DbSession;
import org.openmrs.api.db.hibernate.DbSessionFactory;
import org.openmrs.module.savicsgmao.api.dao.GmaoDao;
import org.openmrs.module.savicsgmao.api.entity.Item;

public class GmaoDaoImpl<T extends Serializable> implements GmaoDao<T> {
	
	DbSessionFactory sessionFactory;
	
	/**
	 * @param sessionFactory the sessionFactory to set
	 */
	public void setSessionFactory(DbSessionFactory sessionFactory) {
		this.sessionFactory = sessionFactory;
	}
	
	/**
	 * @return the sessionFactory
	 */
	public DbSessionFactory getSessionFactory() {
		return sessionFactory;
	}
	
	/**
	 * Gets the current hibernate session while taking care of the hibernate 3 and 4 differences.
	 * 
	 * @return the current hibernate session.
	 */
	private DbSession getSession() {
		try {
			System.out.println(">>>>>> getSession = sessionFactory = " + sessionFactory);
			System.out.println(">>>>>> getSession = sessionFactory.getCurrentSession() = "
			        + sessionFactory.getCurrentSession());
			
			return sessionFactory.getCurrentSession();
		}
		catch (NoSuchMethodError ex) {
			try {
				Method method = sessionFactory.getClass().getMethod("getSession", null);
				return (DbSession) method.invoke(sessionFactory, null);
			}
			catch (Exception e) {
				throw new RuntimeException("Failed to get the current hibernate session", e);
			}
		}
	}
	
	public Item getItemByUuid(String uuid) {
		return (Item) getSession().createCriteria(Item.class).add(Restrictions.eq("uuid", uuid)).uniqueResult();
	}
	
	public Item saveItem(Item item) {
		getSession().saveOrUpdate(item);
		return item;
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
		List entityList = getSession().createCriteria(t).list();
		
		Criteria criteria = getSession().createCriteria(t);
		
		if (limit != null) {
			criteria.setMaxResults(limit);
			if (offset != null) {
				criteria.setFirstResult(offset);
			}
		}
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
		DbSession session = sessionFactory.getCurrentSession();
		Criteria criteria = session.createCriteria(t);
		criteria.add(Restrictions.eq("id", id));
		return (T) criteria.uniqueResult();
	}
	
	@Override
	public T getEntityByUuid(Class t, String uuid) {
		DbSession session = sessionFactory.getCurrentSession();
		Criteria criteria = session.createCriteria(t);
		criteria.add(Restrictions.eq("uuid", uuid));
		return (T) criteria.uniqueResult();
	}
	
	@Override
	public Serializable upsert(Serializable entity) {
		DbSession session = this.sessionFactory.getCurrentSession();
		session.saveOrUpdate(entity);
		session.flush();
		return entity;
	}
	
	@Override
	public void delete(Serializable entity) {
		DbSession session = this.sessionFactory.getCurrentSession();
		session.delete(entity);
	}
}
