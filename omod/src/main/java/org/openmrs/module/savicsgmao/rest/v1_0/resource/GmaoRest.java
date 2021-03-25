package org.openmrs.module.savicsgmao.rest.v1_0.resource;

import org.openmrs.module.webservices.rest.web.RestConstants;
import org.openmrs.module.webservices.rest.web.v1_0.controller.MainResourceController;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/rest/" + RestConstants.VERSION_1 + GmaoRest.GMAO_NAMESPACE)
public class GmaoRest extends MainResourceController {
	
	/**
	 * * @see org.openmrs.module.webservices.rest.web.v1_0.controller.
	 * BaseRestController#getNamespace()
	 */
	public static final String GMAO_NAMESPACE = "/savicsgmao";
	
	@Override
	public String getNamespace() {
		return RestConstants.VERSION_1 + GMAO_NAMESPACE;
	}
}
