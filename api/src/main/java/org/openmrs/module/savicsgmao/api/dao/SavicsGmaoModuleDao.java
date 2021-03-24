/**
 * This Source Code Form is subject to the terms of the Mozilla Public License,
 * v. 2.0. If a copy of the MPL was not distributed with this file, You can
 * obtain one at http://mozilla.org/MPL/2.0/. OpenMRS is also distributed under
 * the terms of the Healthcare Disclaimer located at http://openmrs.org/license.
 *
 * Copyright (C) OpenMRS Inc. OpenMRS is a registered trademark and the OpenMRS
 * graphic logo is a trademark of OpenMRS Inc.
 */
package org.openmrs.module.savicsgmao.api.dao;

import java.io.Serializable;
import java.util.List;
import org.hibernate.Criteria;
import org.hibernate.criterion.Restrictions;
import org.openmrs.api.db.hibernate.DbSession;
import org.openmrs.api.db.hibernate.DbSessionFactory;
import org.openmrs.module.savicsgmao.api.entity.Item;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

@Repository("savicsgmao.SavicsGmaoModuleDao")
public class SavicsGmaoModuleDao<T extends Serializable> {
	
	@Autowired
	DbSessionFactory sessionFactory;
	
	private DbSession getSession() {
		return sessionFactory.getCurrentSession();
	}
	
	public Item getItemByUuid(String uuid) {
		return (Item) getSession().createCriteria(Item.class).add(Restrictions.eq("uuid", uuid)).uniqueResult();
	}
	
	public Item saveItem(Item item) {
		getSession().saveOrUpdate(item);
		return item;
	}
	
	public List getAll(Class t) {
		List entityList = getSession().createCriteria(t).list();
		return entityList;
	}
	
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
	
	public List doSearch(Class t, String key, String value, Integer limit, Integer offset) {
		getSession().createCriteria(t).list();
		Criteria criteria = getSession().createCriteria(t);
		criteria.add(Restrictions.eq(key, value));
		return criteria.list();
	}
	
	public T getEntity(Class t, Object id) {
		DbSession session = sessionFactory.getCurrentSession();
		Criteria criteria = session.createCriteria(t);
		criteria.add(Restrictions.eq("id", id));
		return (T) criteria.uniqueResult();
	}
	
	public T getEntityByUuid(Class t, String uuid) {
		DbSession session = sessionFactory.getCurrentSession();
		Criteria criteria = session.createCriteria(t);
		criteria.add(Restrictions.eq("uuid", uuid));
		return (T) criteria.uniqueResult();
	}
	
	public Serializable upsert(Serializable entity) {
		DbSession session = this.sessionFactory.getCurrentSession();
		session.saveOrUpdate(entity);
		session.flush();
		return entity;
	}
	
	public void delete(Serializable entity) {
		DbSession session = this.sessionFactory.getCurrentSession();
		session.delete(entity);
	}
}
