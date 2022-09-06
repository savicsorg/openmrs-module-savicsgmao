/**
 * This Source Code Form is subject to the terms of the Mozilla Public License,
 * v. 2.0. If a copy of the MPL was not distributed with this file, You can
 * obtain one at http://mozilla.org/MPL/2.0/. OpenMRS is also distributed under
 * the terms of the Healthcare Disclaimer located at http://openmrs.org/license.
 *
 * Copyright (C) OpenMRS Inc. OpenMRS is a registered trademark and the OpenMRS
 * graphic logo is a trademark of OpenMRS Inc.
 */
package org.openmrs.module.savicsgmao;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.openmrs.GlobalProperty;
import org.openmrs.api.AdministrationService;
import org.openmrs.api.context.Context;
import org.openmrs.module.BaseModuleActivator;

/**
 * This class contains the logic that is run every time this module is either started or shutdown
 */
public class SavicsGmaoModuleActivator extends BaseModuleActivator {
	
	private Log log = LogFactory.getLog(this.getClass());
	
	public static final String GLOBAL_PROPERTY_EQUIPMENT_FAILURE_RATE = "savics.gmao.failure.rate";
	
	private AdministrationService administrationService;
	
	/**
	 * @see #started()
	 */
	public void started() {
		log.info("Started Savics Gmao Module");
		administrationService = Context.getAdministrationService();
		GlobalProperty gp;
		
		String property2 = administrationService.getGlobalProperty(GLOBAL_PROPERTY_EQUIPMENT_FAILURE_RATE);
		if (property2 == null || property2.isEmpty()) {
			gp = new GlobalProperty(GLOBAL_PROPERTY_EQUIPMENT_FAILURE_RATE, "60");
			gp.setDescription("GMAO : taux de pannes des équipements");
			administrationService.saveGlobalProperty(gp);
		}
	}
	
	/**
	 * @see #shutdown()
	 */
	public void shutdown() {
		log.info("Shutdown Savics Gmao Module");
	}
	
}
