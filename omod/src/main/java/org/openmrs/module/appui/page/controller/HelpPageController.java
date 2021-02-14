package org.openmrs.module.appui.page.controller;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.openmrs.Location;
import org.openmrs.User;
import org.openmrs.api.LocationService;
import org.openmrs.api.context.Context;
import org.openmrs.api.context.UserContext;
import org.openmrs.module.appui.fragment.controller.HeaderFragmentController;
import org.openmrs.module.appui.page.controller.model.ContactUser;
import org.openmrs.module.appui.page.controller.model.ContactUserEnterprise;
import org.openmrs.module.appui.page.controller.model.ContactUserLocation;
import org.openmrs.module.appui.page.controller.model.InfoFromUser;
import org.openmrs.module.appui.utils.SSLEmail;
import org.openmrs.module.appui.utils.SendEmail;
import  org.openmrs.module.appui.utils.Utils;
import org.openmrs.module.enterprise.api.EnterpriseService;
import org.openmrs.ui.framework.Model;
import org.openmrs.ui.framework.annotation.SpringBean;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

@Controller
public class HelpPageController {
	
	private String[] _REQUIRED_FORM_INPUTS = new String[] {"email", "subject", "message"};
	private String[] _ALL_FORM_INPUTS = new String[] {"email", "subject", "message", "files"};
	private String _TO = "broomfield.technologies@gmail.com";
	private String _SMTP_HOST = "smtp.gmail.com";
	private String _SMTP_PORT = "465";
	private String _PASSWORD = "ny4Q#jjmqPj8$m9";
	
	/**
	 * Handles get requests triggered by going to /openmrs/appui/help.page.
	 * 
	 * @return
	 */
	public String get(Model model) {
		System.out.println("Calling HelpPageController get method.");
		if (Context.isAuthenticated()) {
			System.out.println("get 10");
			List<String> errors = new ArrayList<String>();
			System.out.println("get 20");
			model.addAttribute("errors", errors);
			System.out.println("get 30");
			for (String key:_REQUIRED_FORM_INPUTS) {
				model.addAttribute(key, "");
			}
			return "help";			
		} else {
			System.out.println("WARNING!!! Not authenticated. Redirecting to login page.");
			System.out.println("get 40");
			return "redirect:/login.htm";
		}
	}
	
	/**
	 * Handles post requests.
	 * 
	 * This post request is triggered by submitting form to 
	 * url /openmrs/appui/help.page.
	 * 
	 * @param request
	 * @param enterpriseService
	 * @return
	 * @throws IOException 
	 */
	public String post(
			HttpServletRequest request,
			@SpringBean EnterpriseService enterpriseService, 
			Model model
			//@RequestParam(value="files", required=false) MultipartFile[] files
			) throws IOException {
		System.out.println("Calling HelpPageController post method.");

		//Get user info.
		User user = Context.getAuthenticatedUser();
		ContactUser contactUser = new ContactUser();
		contactUser.setEmail(user.getEmail());
		contactUser.setFamilyName(user.getFamilyName());
		contactUser.setGivenName(user.getGivenName());
		contactUser.setUserName(user.getUsername());
		contactUser.setSystemId(user.getSystemId());
		contactUser.setUserId(user.getUserId());
		contactUser.setUuId(user.getUuid());
		
		//Get enterprise info.
		LocationService locationService = Context.getLocationService();
		String enterpriseGuid = locationService.getEnterpriseForLoggedinUser();
		HeaderFragmentController headerFragmentController = new HeaderFragmentController();
		String enterpriseName = headerFragmentController.getEnterpriseName(enterpriseService);
        //String enterpriseValue = Context.getAuthenticatedUser().getPerson().getAttribute("Enterprise").getValue();
		ContactUserEnterprise contactUserEnterprise = new ContactUserEnterprise();
		contactUserEnterprise.setGuid(enterpriseGuid);
		contactUserEnterprise.setName(enterpriseName);
				
		//Get user location info.
		UserContext userContext = Context.getUserContext();
		Location location = userContext.getLocation();
		ContactUserLocation contactUserLocation = new ContactUserLocation();
		contactUserLocation.setName(location.getName());
		contactUserLocation.setCountry(location.getCountry());
		contactUserLocation.setCityVillage(location.getCityVillage());
		contactUserLocation.setCountyDistrict(location.getCountyDistrict());
		contactUserLocation.setDescription(location.getDescription());
		contactUserLocation.setDisplayString(location.getDisplayString());
		contactUserLocation.setId(location.getLocationId());
		contactUserLocation.setPostalCode(location.getPostalCode());
		contactUserLocation.setAddress1(location.getAddress1());
		contactUserLocation.setAddress2(location.getAddress2());
		contactUserLocation.setAddress3(location.getAddress13());
		contactUserLocation.setAddress4(location.getAddress4());
		contactUserLocation.setAddress5(location.getAddress5());
		contactUserLocation.setAddress6(location.getAddress6());
		contactUserLocation.setAddress7(location.getAddress7());
		contactUserLocation.setAddress8(location.getAddress8());
		contactUserLocation.setAddress9(location.getAddress9());
		contactUserLocation.setAddress10(location.getAddress10());

		//Set user and location info.
		InfoFromUser infoFromUser = new InfoFromUser();
		infoFromUser.setContactUser(contactUser);
		infoFromUser.setContactUserLocation(contactUserLocation);
		infoFromUser.setContactUserEnterprise(contactUserEnterprise);
		System.out.println("Info from user is " + infoFromUser);

		//Get form input values.
		Map<String, String[]> params = request.getParameterMap();
		Map<String, String> formInputs = new HashMap<String, String>();
		for (Map.Entry<String, String[]> entry : params.entrySet()) {
		    String key = entry.getKey();
		    String[] values = entry.getValue();
	    	formInputs.put(key, values[0]);
		}
		
		System.out.println("Form inputs are " + formInputs);

		//Handle input files.
		/*
		List<File> filesToAttach = new ArrayList<File>();
		StringBuilder fileNames = new StringBuilder();
		for(MultipartFile file: files) {
			String fileName = file.getOriginalFilename();
			fileNames.append(fileName);
			File tempFile = File.createTempFile(fileName, "");
			FileOutputStream fos = new FileOutputStream(tempFile);
			fos.write(file.getBytes());
			filesToAttach.add(tempFile);
			fos.close();
		}
		*/
		
		//Validate form input values.
		StringBuilder errors = Utils.validateFormInputs(formInputs, _REQUIRED_FORM_INPUTS);
		model.addAttribute("errors", errors);
		
		//Set values for the view.
		for (String key:_ALL_FORM_INPUTS) {
			model.addAttribute(key, formInputs.getOrDefault(key, ""));
		}		
		System.out.println("Form validation errors are " + errors);
		
		String viewName = "thankyou";
		
		//If errors are found then set the form response to help page.
		if (errors.length() > 0) {
			System.out.println("Encountered errors");
			viewName = "help";
		} else {
			//Send email.
			System.out.println("No errors. Sending email.");
			String message = infoFromUser + "<p></p>" + formInputs.get("message");
			
			/*
			SendEmail.send(formInputs.get("email"), 
					_TO, 
					formInputs.get("subject"), 
					message, 
					_PASSWORD, 
					_SMTP_HOST, 
					_SMTP_PORT);
					//filesToAttach);
			*/
			System.out.println("Calling SSLEmail.send");
			SSLEmail.send(_TO, _PASSWORD, _TO, formInputs.get("email"));
			System.out.println("Done with SSLEmail.send");
			
		}
		return viewName;
	}
	

	

	

	
	
}
