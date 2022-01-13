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
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.openmrs.module.savicsgmao.api.entity.Equipment;
import org.springframework.stereotype.Controller;
import org.openmrs.module.savicsgmao.api.service.GmaoService;
import org.openmrs.module.savicsgmao.export.EquipmentExport;
import org.openmrs.module.savicsgmao.rest.v1_0.resource.GmaoRest;
import org.openmrs.module.webservices.rest.web.RestConstants;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

/**
 * @author anatoleabe The main controller.
 */
@Controller
public class EquipmentController {
	
	protected final Log log = LogFactory.getLog(getClass());
	
	@Autowired
	GmaoService gmaoService;
	
	@RequestMapping(method = RequestMethod.GET, value = "/rest/" + RestConstants.VERSION_1 + GmaoRest.GMAO_NAMESPACE
	        + "/equipment/export")
	public void exportToExcel(HttpServletResponse response, HttpServletRequest request) throws IOException, Exception {
		response.setContentType("application/octet-stream");
		DateFormat dateFormatter = new SimpleDateFormat("yyyy-MM-dd_HH:mm:ss");
		String currentDateTime = dateFormatter.format(new Date());
		
		String headerKey = "Content-Disposition";
		String headerValue = "attachment; filename=Maintenance_Requests_" + currentDateTime + ".xlsx";
		response.setHeader(headerKey, headerValue);
		
		List<Equipment> ecs = gmaoService.getAll(Equipment.class);
		
		EquipmentExport excelExporter = new EquipmentExport(ecs);
		
		excelExporter.export(response);
	}
}
