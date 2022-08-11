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

import org.springframework.stereotype.Component;

/**
 * Contains module's config.
 */
@Component("savicsgmao.SavicsGmaoModuleConfig")
public class SavicsGmaoModuleConfig {
	
	public final static String MODULE_PRIVILEGE = "Savics Gmao Module Privilege";
	
	public static final String GLOBAL_PROPERTY_EQUIPMENT_FAILURE_RATE = "savics.gmao.tasks.autoCloseExternalVisitAfter";
}
