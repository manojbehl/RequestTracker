package com.requestTracker.controller;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Properties;

import javax.servlet.http.HttpSession;

import java.util.Collection;

import javax.servlet.ServletContext;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.multipart.commons.CommonsMultipartFile;

import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import com.requestTracker.domain.Application;
import com.requestTracker.domain.Login;
import com.requestTracker.domain.Partner;
import com.requestTracker.domain.User;
import com.requestTracker.exception.BusinessException;
import com.requestTracker.service.IAdminService;
import com.requestTracker.service.IApplicationService;


import java.io.*;

@RestController
public class AdminRequestController {

	private static final String UPLOAD_DIRECTORY = "/assets/AppImg";
	List<Application> appList;

	@Autowired
	private IAdminService iadminService;

	@Autowired
	private IApplicationService applicationService;
	
	
	
	Logger logger = Logger.getLogger(AdminRequestController.class);

	@ResponseStatus(value=HttpStatus.NOT_FOUND, reason="IOException occured")
	@ExceptionHandler(IOException.class)
	public ModelAndView handleIOException(){
		logger.info("IOException handler executed");
		ModelAndView	modelAndView = new ModelAndView();
		return	modelAndView.addObject("Errmsg", "Please provide a valid Image.");
		//returning 404 error code
	}
	
	@RequestMapping(value = "/getPartnerList", method = RequestMethod.GET)
	public ModelAndView getAllPartner( HttpSession session) throws IOException {
		ModelAndView modelview = new ModelAndView();
		User someObject = (User) session.getAttribute("userSessionObject");
		if(someObject==null){
			modelview.setViewName("login");

//			modelview.setViewName("redirect:/");
			return modelview;
		}
		modelview.addObject("PartnerList", iadminService.getAllPartner());
		modelview.setViewName("dashboard");
		return modelview;
	}

	@GetMapping(value = "/createUser")
	public ModelAndView getUserInfo( HttpSession session) {
		ModelAndView modelview = new ModelAndView();
		User someObject = (User) session.getAttribute("userSessionObject");
		if(someObject==null){
			modelview.setViewName("login");

//			modelview.setViewName("redirect:/");
			return modelview;
		}
		modelview.addObject("PartnerList", iadminService.getAllActivePartners());
		modelview.setViewName("createUser");
		return modelview;
	}

	@GetMapping(value = "/newUserCreation")
	public ModelAndView createUser( HttpSession session ) {
		return getNewUserCreation(session);
	}
	
	private ModelAndView getNewUserCreation(HttpSession session){
		ModelAndView modelview = new ModelAndView();
		User someObject = (User) session.getAttribute("userSessionObject");
		if(someObject==null){
			modelview.setViewName("login");
//			modelview.setViewName("redirect:/");
			return modelview;
		}
		System.out.println("New User Creation List");
		modelview.addObject("PartnerList", iadminService.getAllActivePartners());
		modelview.setViewName("newUserCreation");
		return modelview;
		
	}

	@PostMapping(value = "/addUser")
	public ModelAndView addUser(@ModelAttribute User user, ModelMap msg, RedirectAttributes redirectAttributes, HttpSession session) {
		
		ModelAndView modelview = new ModelAndView();
		//	modelview.setViewName("redirect:/newUserCreation");
		modelview = getNewUserCreation(session);
		if ( (user.getUserEmail()!=null || user.getUserEmail().trim()!="") && (user.getFname().trim()!="" || user.getFname()!=null) && (user.getLname().trim()!="" || user.getLname()!=null) && user.getPartnerId()>0) {
			System.out.println("At Controller"+user.getUserEmail()+"\t"+user.getFname());
			String password = iadminService.addPartnerUser(user);
			if(password==null){
				modelview.addObject("ErrorMsg", "User with "+user.getUserEmail() +" Already Exists ");
				return modelview;
			}
			redirectAttributes.addFlashAttribute("password", password);
			redirectAttributes.addFlashAttribute("SuccessMsg", "User Created Succesully ");
			modelview.addObject("password", password);
			modelview.addObject("SuccessMsg", "User Created Succesully ");
			
			return modelview;
		} else {
			modelview.addObject("ErrorMsg", "User Creation Failed ");
			return modelview;
		}
	}

	@GetMapping(value = "/getUser/{partnerId}")
	@ResponseBody
	public List<User> userList(@PathVariable("partnerId") int partnerId) {

		List<User> userlist = iadminService.getUserList(partnerId);

		if (userlist != null) {
			return userlist;
		} else {
			return null;
		}
	}

	@GetMapping(value = "/updateStatus/{statusId}/{userId}")
	@ResponseBody
	public void updateStatus(@PathVariable("statusId") int statusId, @PathVariable("userId") int userId) {
		User user = new User();
		user.setUserId(userId);
		int viewStatusId = statusId;
		if (statusId == 1) {
			user.setStatusId(2);
		} else {
			user.setStatusId(1);
		}
		logger.info("Status Changed " + iadminService.updateStatus(user, viewStatusId));

	}

	@GetMapping(value = "/addPartnerIp")
	public ModelAndView addPartnerIp( HttpSession session) {
		return returnAddPartnerIP(session);
	}

	private ModelAndView returnAddPartnerIP(HttpSession session){
		ModelAndView modelview = new ModelAndView();
		User someObject = (User) session.getAttribute("userSessionObject");
		if(someObject==null){
			modelview.setViewName("login");
//			modelview.setViewName("redirect:/");
			return modelview;
		}
		List<Partner> partners = new ArrayList<Partner>();
		List<Partner> allPartners = iadminService.getAllPartner();
		modelview.setViewName("addPartnerIp");
		if(allPartners != null){
		for (Partner partner : allPartners) {
			if (partner.getStatus().equals("ACTIVE")) {
				partners.add(partner);
			}
		}
		}
		modelview.addObject("PartnerList", partners);
		return modelview;
		
	}
	
	
	@PostMapping(value = "/Applist/{pid}")
	@ResponseBody
	public Collection<Application> getAppList(@PathVariable("pid") int pid) {
		ModelAndView modelview = new ModelAndView();
		modelview.addObject("PartnerAppList", iadminService.getPartnerApps(pid));
		return iadminService.getPartnerApps(pid);
		}

	
	@PostMapping(value = "/editUser")
	@ResponseBody
	public boolean editUserInfo(@RequestBody User user) {
		if((user.getFname()==null||user.getFname().trim()=="")&&(user.getLname()==null||user.getLname().trim()=="")){
			return false;
		}
		return iadminService.updateUserInfo(user);
		}

	@RequestMapping(value = "/partnerDashboard", method = RequestMethod.GET)
	public ModelAndView reqpartnerDashboard(HttpSession session) {
		ModelAndView modelview = new ModelAndView();
		User someObject = (User) session.getAttribute("userSessionObject");
		if(someObject==null){
			modelview.setViewName("login");

//			modelview.setViewName("redirect:/");
			return modelview;
		}
		int partnerId = someObject.getPartnerId();
		modelview.addObject("PartnerAppIpInfo", iadminService.getPartnerApps(partnerId));
		modelview.setViewName("partnerDashboard");
		return modelview;
		}

	@RequestMapping(value = "/getPartnerAppIp/{id}", method = RequestMethod.GET)
	@ResponseBody
	public List<Partner> getPartnerAppIp(@PathVariable("id") int partnerId) {
		List<Partner> getAppIpMap = iadminService.getAppIpMap(partnerId);
		if (getAppIpMap != null) {
			return getAppIpMap;
		}
		return null;
		}

	@RequestMapping(value = "/setPartnerStatus/{id}/{viewStatus}", method = RequestMethod.GET)
	@ResponseBody

	public List<Partner> setPartnerStatus(@PathVariable("id") int partnerId,
			@PathVariable("viewStatus") String viewStatus) {
		
		iadminService.setPartnerStatus(partnerId, viewStatus);
		List<Partner> PartnerList = iadminService.getAllPartner();
		if (PartnerList != null) {
			return PartnerList;
		}
		return null;
	}

	@RequestMapping(value = "/moveToCreateApp", method = RequestMethod.GET)
	public ModelAndView moveToCreateApp(HttpSession	session) {

		ModelAndView modelview = new ModelAndView();
		User someObject = (User) session.getAttribute("userSessionObject");
		if(someObject==null){
			modelview.setViewName("login");
			//modelview.setViewName("redirect:/");
			return modelview;
		}
		modelview.setViewName("createApplication");
		return modelview;
	}

	@RequestMapping(value = "/mapAppIps", method = RequestMethod.POST)
	public ModelAndView mapAppIps(int partnerId, String appId[], String appIps[], HttpSession session,RedirectAttributes redirectAttrs) {
		ModelAndView modelview = new ModelAndView();
		User someObject = (User) session.getAttribute("userSessionObject");
		if(someObject==null){
			modelview.setViewName("login");

//			modelview.setViewName("redirect:/");
			return modelview;
		}
		modelview = returnAddPartnerIP(session);
		if(appId	!= null && appId.length	>	0	&&	appIps	!=	null	&&	appIps.length	>	0){
			if (iadminService.mapAppIps(partnerId, appId, appIps) != null) {
				redirectAttrs.addFlashAttribute("msg", " Ip-Addresses Maped Successfully ");
				modelview .addObject("msg", " Ip-Addresses Maped Successfully ");
//				modelview.setViewName("redirect:/addPartnerIp");
			}else {
				modelview .addObject("Errmsg", " No changes were made ");
				//redirectAttrs.addFlashAttribute("Errmsg", " No changes were made ");
//				modelview.setViewName("redirect:/addPartnerIp");
			}
		}else{
			modelview .addObject("Errmsg", " The Partner either has no Apps or no IP Addresses! ");
		}
		return modelview;
	}

	@GetMapping("managePartner")
	public ModelAndView showManagePartnerPage( HttpSession session) {
		ModelAndView modelview = new ModelAndView();
		User someObject = (User) session.getAttribute("userSessionObject");
		if(someObject==null){
			modelview.setViewName("login");

//			modelview.setViewName("redirect:/");
			return modelview;
		}
			/*List<Partner> partners = new ArrayList<Partner>();
			List<Partner> allPartners = iadminService.getAllPartner();
			if(allPartners!=null){
			for (Partner partner : allPartners) {
				if (partner.getStatus().equals("ACTIVE")) {
					partners.add(partner);
				}
			}}*/
		modelview.addObject("partnerList", iadminService.getAllActivePartners());
		try {
			modelview.addObject("appList", applicationService.getAllApps());
		} catch (BusinessException e) {
			e.printStackTrace();
			//handleIOException();
		}
		modelview.setViewName("createPartner");
		return modelview;
	}


	@RequestMapping(value = "/createApp", method = RequestMethod.POST)
	public ModelAndView registerApplication(@RequestParam CommonsMultipartFile appLogo, HttpSession session,
			Application application, RedirectAttributes redirectAttrs) throws Exception {
		ModelAndView modelview = new ModelAndView();
		User someObject = (User) session.getAttribute("userSessionObject");
		if(someObject==null){
			modelview.setViewName("login");
//			modelview.setViewName("redirect:/");
			return modelview;
		}
		
		if(application	==	null || application.getApp_Name()	==	null	||	application.getApp_Name().trim()==""	||	application.getAppURL()==null || application.getAppURL().trim()==""){
			modelview.addObject("Errmsg", "Please provide correct App name");
			modelview.setViewName("createApplication"); 
			return modelview;
		}
		if(!checkApplicationName(application.getApp_Name())){
			modelview.addObject("Errmsg", "Application with name "+ application.getApp_Name()+" already exists!");
			modelview.setViewName("createApplication"); 
			return modelview;
		}
		if(application.getAppURL()==null || application.getAppURL().trim()==""){
			modelview.addObject("Errmsg", "Please provide an App Url");
			modelview.setViewName("createApplication"); 
			return modelview;
		}
		if(appLogo.getContentType() == null || (!appLogo.getContentType().equals("image/png") && !appLogo.getContentType().endsWith("image/jpeg"))){
			return	handleIOException();
		}
		int appId	=	iadminService.createNewApplication(application);
		if (appId	>	0) {
			String filename = appLogo.getOriginalFilename();
			Properties prop = new Properties();
	    	prop.load(getClass().getClassLoader().getResourceAsStream("variable.properties"));
	    	logger.info("The file name is : "+filename);
	    	logger.info("Does UPLOAD_DIRECTORY exist : "+prop.containsKey("UPLOAD_DIRECTORY")+" and the value is : "+prop.getProperty("UPLOAD_DIRECTORY"));
	    	String path =prop.getProperty("UPLOAD_DIRECTORY");
	    	logger.info("PATH TO SAVE IMAGE IS : "+prop.getProperty("UPLOAD_DIRECTORY")+ appId);
			if(!filename.equals("")	&& 	!filename.trim().equals("")){
				byte[] bytes = appLogo.getBytes();  
				BufferedOutputStream stream =new BufferedOutputStream(new FileOutputStream(  
		        new File(path+ "app"+appId+".png"))); 
		        stream.write(bytes);  
		        stream.flush();  
		        stream.close();
			}
			//redirectAttrs.addFlashAttribute("msg", "Application Created SuccessFully");
			modelview.addObject("msg", "Application Created SuccessFully");
		} else {
			//redirectAttrs.addFlashAttribute("Errmsg", "Application Creation Failed");
			modelview.addObject("Errmsg", "Application Creation Failed");
		}
			modelview.setViewName("createApplication"); 
//			modelview.setViewName("redirect:/moveToCreateApp");
			return modelview;
		 
	}
	
	
	@RequestMapping(value = "/checkAppName/{AppName}", method = RequestMethod.GET)
	public boolean checkApplicationName(@PathVariable("AppName") String appName) throws Exception {
		
		if (applicationService.checkAppNameExists(appName)) {
			return true;
		} 
		return false;
	}

}
