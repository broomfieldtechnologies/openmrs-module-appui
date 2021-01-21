package org.openmrs.module.appui.fragment.controller;

//US10060

	import java.io.IOException;
	import java.util.Date;
	import java.util.List;
	import java.util.Map;
	

	import javax.servlet.http.HttpServletRequest;

	import org.apache.commons.logging.Log;
	import org.apache.commons.logging.LogFactory;
	import org.openmrs.Location;
	import org.openmrs.LocationTag;
	import org.openmrs.Person;
	import org.openmrs.PersonAttribute;
	import org.openmrs.User;
	import org.openmrs.UserAcknowledge;
	import org.openmrs.api.LocationService;
	import org.openmrs.api.context.Context;
	import org.openmrs.module.appframework.AppFrameworkConstants;
	import org.openmrs.module.appframework.domain.Extension;
	import org.openmrs.module.appframework.service.AppFrameworkService;
	import org.openmrs.module.appui.AppUiConstants;
	import org.openmrs.module.appui.AppUiExtensions;
	import org.openmrs.module.enterprise.Enterprise;
	import org.openmrs.module.enterprise.api.EnterpriseService;

import org.openmrs.ui.framework.annotation.SpringBean;
	import org.openmrs.ui.framework.fragment.FragmentModel;
	import org.springframework.transaction.annotation.Transactional;
	import org.openmrs.UserAcknowledge;
	import org.openmrs.GlobalProperty;
	import org.openmrs.api.AdministrationService;
	import org.openmrs.User;
	import java.time.format.DateTimeFormatter;
	import java.time.LocalDateTime;  
	import java.util.Date;
	import org.openmrs.api.impl.UserAcknowledgeServiceImpl;
	import org.openmrs.api.UserAcknowledgeService;
	import org.springframework.web.bind.annotation.RequestParam;

	public class AcceptUserFragmentController {	
	
		protected final Log log = LogFactory.getLog(getClass());
	    
	    UserAcknowledgeService userAcknowledgeService;
	    AdministrationService adminService;

	    public void controller(@SpringBean AppFrameworkService appFrameworkService,
	    		@SpringBean EnterpriseService enterpriseService, FragmentModel fragmentModel) {
	    	adminService=Context.getAdministrationService();
	    	
	    String message =adminService.getGlobalProperty("UserAcknowledge");
	    	
	    	fragmentModel.addAttribute("dialogMessage", message);
	    	
	        
	    }
	    
	    public void acceptUserModel() {
		 
	    		
	    		
	    		UserAcknowledge userAcknowledge = new UserAcknowledge();
	      
	    				
	    				adminService = Context.getAdministrationService();
	    				String userAckTimeLimit = adminService.getGlobalProperty("UserAcknowledgeTimeLimit");
	    				int userAcknowledgeInterval = Integer.parseInt(userAckTimeLimit);
	    				
	    				userAcknowledgeService=Context.getUserAcknowledgeService();
	    				userAcknowledgeService.saveUserAcknowledge(Context.getAuthUserId(), userAcknowledgeInterval);
	         
	    				
	  }
	    
	    
	    public String logout(HttpServletRequest request) throws IOException {
	    	log.error("inside logout of fragment");
	        Context.logout();
	        request.getSession().invalidate();
	        request.getSession().setAttribute(AppUiConstants.SESSION_ATTRIBUTE_MANUAL_LOGOUT, "true");
	        
	        return "forward:/referenceApplication/index.htm";
	    }
	    
	 
	 
	
	
}
