/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.openmrs.module.savicsgmao.api.dao.impl;

import java.io.Serializable;
import java.util.List;
import org.hibernate.Criteria;
import org.hibernate.criterion.Restrictions;
import org.openmrs.api.APIException;
import org.openmrs.api.db.hibernate.DbSession;
import org.openmrs.api.db.hibernate.DbSessionFactory;
import org.openmrs.api.impl.BaseOpenmrsService;
import org.openmrs.module.savicsgmao.api.GmaoService;
import org.springframework.beans.factory.annotation.Autowired;

public class GmaoServiceImpl<T extends Serializable> extends BaseOpenmrsService implements GmaoService {
	
	@Autowired
	DbSessionFactory sessionFactory;
	
	@Override
	public List getAll(Class t) {
		List entityList = sessionFactory.getCurrentSession().createCriteria(t).list();
		return entityList;
	}
	
	@Override
	public List getAll(Class t, Integer limit, Integer offset) {
		//TODO adapt this
		List entityList = sessionFactory.getCurrentSession().createCriteria(t).list();
		
		Criteria criteria = sessionFactory.getCurrentSession().createCriteria(t);
		
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
		sessionFactory.getCurrentSession().createCriteria(t).list();
		Criteria criteria = sessionFactory.getCurrentSession().createCriteria(t);
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
	
	@Override
	public void onStartup() {
		throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
	}
	
	@Override
	public void onShutdown() {
		throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
	}
	
}
