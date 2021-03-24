/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.openmrs.module.savicsgmao.api.dao.impl;

import java.io.Serializable;
import java.util.List;
import org.openmrs.api.impl.BaseOpenmrsService;
import org.openmrs.module.savicsgmao.api.GmaoService;
import org.openmrs.module.savicsgmao.api.dao.SavicsGmaoModuleDao;

public class GmaoServiceImpl<T extends Serializable> extends BaseOpenmrsService implements GmaoService {
	
	SavicsGmaoModuleDao dao;
	
	/**
	 * Injected in moduleApplicationContext.xml
	 * 
	 * @param dao
	 */
	public void setDao(SavicsGmaoModuleDao dao) {
		this.dao = dao;
	}
	
	@Override
	public List getAll(Class t) {
		List entityList = this.dao.getAll(t);
		return entityList;
	}
	
	@Override
	public List getAll(Class t, Integer limit, Integer offset) {
		return this.dao.getAll(t, limit, offset);
	}
	
	@Override
	public List doSearch(Class t, String key, String value, Integer limit, Integer offset) {
		return this.dao.doSearch(t, key, value, limit, offset);
	}
	
	@Override
	public T getEntity(Class t, Object id) {
		return (T) this.dao.getEntity(t, id);
	}
	
	@Override
	public T getEntityByUuid(Class t, String uuid) {
		return (T) this.dao.getEntityByUuid(t, uuid);
	}
	
	@Override
	public Serializable upsert(Serializable entity) {
		return this.dao.upsert(entity);
	}
	
	@Override
	public void delete(Serializable entity) {
		this.dao.delete(entity);
	}
}
