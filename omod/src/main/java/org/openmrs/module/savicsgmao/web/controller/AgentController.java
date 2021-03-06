/**
 * This Source Code Form is subject to the terms of the Mozilla Public License,
 * v. 2.0. If a copy of the MPL was not distributed with this file, You can
 * obtain one at http://mozilla.org/MPL/2.0/. OpenMRS is also distributed under
 * the terms of the Healthcare Disclaimer located at http://openmrs.org/license.
 *
 * Copyright (C) OpenMRS Inc. OpenMRS is a registered trademark and the OpenMRS
 * graphic logo is a trademark of OpenMRS Inc.
 */
package org.openmrs.module.savicsgmao.web.controller;

import java.io.IOException;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.openmrs.api.UserService;
import org.openmrs.api.context.Context;
import org.openmrs.module.savicsgmao.api.service.GmaoService;
import org.openmrs.module.savicsgmao.api.entity.Agent;
import org.openmrs.module.savicsgmao.rest.v1_0.resource.GmaoRest;
import org.openmrs.module.savicsgmao.web.serialization.ObjectMapperRepository;
import org.openmrs.module.webservices.rest.web.RestConstants;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.ResponseBody;

/**
 * The main controller.
 */
@Controller
public class AgentController {
	
	protected final Log log = LogFactory.getLog(getClass());
	
	@Autowired
	UserService userService;
	
	@RequestMapping(method = RequestMethod.GET, value = "/rest/" + RestConstants.VERSION_1 + GmaoRest.GMAO_NAMESPACE
	        + "/agent/all")
	@ResponseBody
	public String getAllAgents() throws IOException {
		ObjectMapperRepository objectMapperRepository = new ObjectMapperRepository();
		GmaoService gmaoService = Context.getService(GmaoService.class);
		return objectMapperRepository.writeValueAsString(gmaoService.getAll(Agent.class));
	}
	
	@RequestMapping(method = RequestMethod.GET, value = "/rest/" + RestConstants.VERSION_1 + GmaoRest.GMAO_NAMESPACE
	        + "/agent/test")
	@ResponseBody
	public String testit() throws IOException {
		ObjectMapperRepository objectMapperRepository = new ObjectMapperRepository();
		GmaoService gmaoService = Context.getService(GmaoService.class);
		List<Agent> list1 = gmaoService.getAll(Agent.class);
		return objectMapperRepository.writeValueAsString(list1);
	}
	
	@RequestMapping(method = RequestMethod.POST, value = "/rest/" + RestConstants.VERSION_1 + GmaoRest.GMAO_NAMESPACE
	        + "/agent/test")
	@ResponseBody
	public String upsert() throws IOException {
		ObjectMapperRepository objectMapperRepository = new ObjectMapperRepository();
		GmaoService gmaoService = Context.getService(GmaoService.class);
		List<Agent> list1 = gmaoService.getAll(Agent.class);
		return objectMapperRepository.writeValueAsString(list1);
	}
}
