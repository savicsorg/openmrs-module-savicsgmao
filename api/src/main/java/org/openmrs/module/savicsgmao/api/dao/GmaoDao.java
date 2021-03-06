/**
 * The contents of this file are subject to the OpenMRS Public License
 * Version 1.0 (the "License"); you may not use this file except in
 * compliance with the License. You may obtain a copy of the License at
 * http://license.openmrs.org
 *
 * Software distributed under the License is distributed on an "AS IS"
 * basis, WITHOUT WARRANTY OF ANY KIND, either express or implied. See the
 * License for the specific language governing rights and limitations
 * under the License.
 *
 * Copyright (C) OpenMRS, LLC.  All Rights Reserved.
 */
package org.openmrs.module.savicsgmao.api.dao;

import java.io.Serializable;
import java.util.List;
import org.openmrs.api.APIException;
import org.springframework.transaction.annotation.Transactional;

@Transactional
public interface GmaoDao<T extends Serializable> {
	
	@Transactional(readOnly = true)
	public List<T> getAll(Class<T> t) throws APIException;
	
	@Transactional(readOnly = true)
	public List<T> getAll(Class<T> t, Integer limit, Integer offset) throws APIException;
	
	@Transactional(readOnly = true)
	public List<T> doSearch(Class<T> t, String key, String value, Integer limit, Integer offset) throws APIException;
	
	@Transactional(readOnly = true)
	T getEntity(Class<T> t, Object id) throws APIException;
	
	@Transactional(readOnly = true)
	T getEntityByUuid(Class<T> t, String uuid) throws APIException;
	
	@Transactional(readOnly = true)
	T getEntityByid(Class<T> t, String idName, Integer id) throws APIException;
	
	@Transactional
	T upsert(T entity) throws APIException;
	
	@Transactional
	void delete(final T entity) throws APIException;
	
}
