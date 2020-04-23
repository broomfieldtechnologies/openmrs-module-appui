/*
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

package org.openmrs.module.appui.fragment.controller;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Set;

import javax.servlet.http.HttpServletRequest;

import org.openmrs.Location;
import org.openmrs.LocationAttribute;
import org.openmrs.LocationAttributeType;
import org.openmrs.Person;
import org.openmrs.PersonAttribute;
import org.openmrs.User;
import org.openmrs.api.LocationService;
import org.openmrs.api.PersonService;
import org.openmrs.api.context.Context;
import org.openmrs.module.appframework.domain.Extension;
import org.openmrs.module.appframework.service.AppFrameworkService;
import org.openmrs.module.appui.AppUiConstants;
import org.openmrs.module.appui.AppUiExtensions;
import org.openmrs.module.enterprise.Enterprise;
import org.openmrs.module.enterprise.api.EnterpriseService;
import org.openmrs.ui.framework.annotation.SpringBean;
import org.openmrs.ui.framework.fragment.FragmentModel;

/**
 *
 */
public class HeaderFragmentController {

    // RA-592: don't use PrivilegeConstants.VIEW_LOCATIONS
    private static final String GET_LOCATIONS = "Get Locations";
    private static final String VIEW_LOCATIONS = "View Locations";

    public void controller(@SpringBean AppFrameworkService appFrameworkService,
    		@SpringBean EnterpriseService enterpriseService, FragmentModel fragmentModel) {
        try {
            Context.addProxyPrivilege(GET_LOCATIONS);
            Context.addProxyPrivilege(VIEW_LOCATIONS);
            fragmentModel.addAttribute("loginLocations", appFrameworkService.getLoginLocations());

            List<Extension> exts = appFrameworkService.getExtensionsForCurrentUser(AppUiExtensions.HEADER_CONFIG_EXTENSION);
            Extension lowestOrderExtension = getLowestOrderExtenstion(exts);
			
			List<Location> loginLocations = appFrameworkService.getLoginLocations();
			fragmentModel.addAttribute("locationsbyEnterprise", getLocationsbyEnterprise(loginLocations, enterpriseService));
			
            Map<String, Object> configSettings = lowestOrderExtension.getExtensionParams();
            fragmentModel.addAttribute("configSettings", configSettings);
            List<Extension> userAccountMenuItems = appFrameworkService.getExtensionsForCurrentUser(AppUiExtensions.HEADER_USER_ACCOUNT_MENU_ITEMS_EXTENSION);
            fragmentModel.addAttribute("userAccountMenuItems", userAccountMenuItems);
            fragmentModel.addAttribute("enterpriseName", getEnterpriseName(enterpriseService));

        } finally {
            Context.removeProxyPrivilege(GET_LOCATIONS);
            Context.removeProxyPrivilege(VIEW_LOCATIONS);
        }
    }

    public Extension getLowestOrderExtenstion(List<Extension> exts) {
        Extension lowestOrderExtension = exts.size() > 0 ? exts.get(0) : null;
        for(Extension ext : exts) {
            if (lowestOrderExtension.getOrder() > ext.getOrder()) {
                lowestOrderExtension = ext;
            }
        }
        return lowestOrderExtension;
    }

    public void logout(HttpServletRequest request) throws IOException {
        Context.logout();
        request.getSession().invalidate();
        request.getSession().setAttribute(AppUiConstants.SESSION_ATTRIBUTE_MANUAL_LOGOUT, "true");
    }
    public String getEnterpriseName(EnterpriseService es) {
    	Context.getService(EnterpriseService.class);
		User user = Context.getAuthenticatedUser();
		Person person = user.getPerson();
		PersonAttribute enterprisePersonAttribute = person.getAttribute("Enterprise");
		String enterpriseIdGuid = enterprisePersonAttribute.getValue();
    	Enterprise eps = es.getEnterpriseByUuid(enterpriseIdGuid);

		return eps.getName();
    	
    }
	
	public List<Location> getLocationsbyEnterprise(List<Location> locations, EnterpriseService es) {
		List<Location> locationsbyEnterprise = new ArrayList<Location>();
		User user = Context.getAuthenticatedUser();
		PersonService ps = Context.getPersonService();
		Person person = ps.getPerson(user.getId());
		PersonAttribute enterprisePersonAttribute = person.getAttribute("Enterprise");
		String enterpriseIdGuid = enterprisePersonAttribute.getValue();
		Enterprise eps = es.getEnterpriseByUuid(enterpriseIdGuid);
		String epsGuid = eps.getUuid();
		LocationService ls = Context.getLocationService();
		LocationAttributeType latForEnterprise = ls.getLocationAttributeTypeByName("Enterprise");

		for (Location location : locations) {
			Set<LocationAttribute> locationAttributes = location.getAttributes();
			
			for (LocationAttribute locationAttribute : locationAttributes) {
				if (locationAttribute.getAttributeType().equals(latForEnterprise)
				        && locationAttribute.getValueReference().equals(epsGuid)) {
					locationsbyEnterprise.add(location);
				}
			}
		}
		return locationsbyEnterprise;
	}
}
