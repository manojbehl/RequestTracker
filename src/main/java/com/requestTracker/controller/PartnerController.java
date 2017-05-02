package com.requestTracker.controller;

import java.awt.Graphics2D;
import java.awt.image.BufferedImage;
import java.io.BufferedOutputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Properties;

import javax.imageio.ImageIO;
import javax.servlet.ServletContext;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.commons.CommonsMultipartFile;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.requestTracker.domain.Application;
import com.requestTracker.domain.Partner;
import com.requestTracker.domain.User;
import com.requestTracker.exception.BusinessException;
import com.requestTracker.exception.ExceptionConstant;
import com.requestTracker.json.ResponseJSON;
import com.requestTracker.service.IAdminService;
import com.requestTracker.service.IApplicationService;
import com.requestTracker.service.IPartnerService;

/**
 * @author PC33
 *
 */

@RestController // && (partner.getAppIdAray()!=null &&
				// partner.getAppIdAray().length>0)
public class PartnerController {
	@Autowired
	IPartnerService partnerService;
	@Autowired
	IApplicationService applicationService;
	@Autowired
	IAdminService iadminService;

	private static final int IMG_WIDTH = 160;
	private static final int IMG_HEIGHT = 150;

	Logger logger = Logger.getLogger(PartnerController.class);

	private static final String UPLOAD_DIRECTORY = "/assets/partnerImages";

	@ResponseStatus(value = HttpStatus.NOT_FOUND, reason = "IOException occured")
	@ExceptionHandler(IOException.class)
	public ModelAndView handleIOException() {
		logger.info("IOException handler executed");
		ModelAndView modelAndView = getManagerPartner();
		return modelAndView.addObject("Errmsg", "Please provide a valid Image.");
		// returning 404 error code
	}

	@GetMapping(value = "/partner")
	public ResponseJSON getAllPartner() {
		ResponseJSON responseJSON = new ResponseJSON();
		List<Partner> partnetList = new ArrayList<Partner>();

		partnetList = iadminService.getAllPartner();
		if (partnetList == null || partnetList.size() < 1) {
			try {
				throw new BusinessException(ExceptionConstant.NO_DATA_FOUND);
			} catch (BusinessException e) {
				e.printStackTrace();
				responseJSON.setError(true);
				responseJSON.setMessage(e.getExceptionMessage());
				return responseJSON;
			}
		}
		responseJSON.setResult(partnetList);
		return responseJSON;

	}

	/*
	 * Return the partner applications and the rest of apps as separate lists
	 * for the update partner section on Manage partner page
	 */

	@GetMapping(produces = MediaType.APPLICATION_JSON_VALUE, value = "partnerApps/{partnerId}")
	public Map<String, List<Application>> showPartnerApps(@PathVariable("partnerId") int partnerId,
			HttpServletRequest request, HttpServletResponse response) throws IOException {
		List<Application> partnerAppList = null;
		List<Application> restAppList = null;
		List<Application> allAppList = null;
		Map<String, List<Application>> partnerDataMap = new HashMap<String, List<Application>>();

		logger.info("Recieved partnerId in controller is : " + partnerId);
		try {
			partnerAppList = applicationService.getAppsByPartnerId(partnerId);

			logger.info("PRINTING THE ELEMENTS IN PARTNER APP LIST");
			for (Application application : partnerAppList) {
				logger.info("The app is : " + application.getApp_Name());
			}
			allAppList = applicationService.getAllApps();
			logger.info("PRINTING THE ELEMENTS IN ALL APP LIST");
			for (Application application : allAppList) {
				logger.info("The app is : " + application.getApp_Name());
			}
			restAppList = new ArrayList<Application>();
			for (Application application : allAppList) {
				if (partnerAppList.contains(application)) {
					logger.info("Partner App : " + application.getApp_Name() + " Was Not Added In restAppList");
				} else {
					restAppList.add(application);
					logger.info("App : " + application.getApp_Name() + " Was Added In restAppList");
				}
			}
			partnerDataMap.put("restAppList", restAppList);
			partnerDataMap.put("myApps", partnerAppList);
		} catch (BusinessException e) {
			logger.info("EXCEPTION OCCURED :" + e.getExceptionMessage());
			e.printStackTrace();
			// handleIOException();
		}
		return partnerDataMap;
	}

	/*
	 * INsert a new Partner
	 */
	@PostMapping("/partner")
	public ModelAndView createNewPartner(@RequestParam CommonsMultipartFile partnerLogo, HttpSession session,
		Partner partner, RedirectAttributes redirectAttrs) throws IOException {
		ModelAndView modelview = new ModelAndView();
		User someObject = (User) session.getAttribute("userSessionObject");
		if (someObject == null) {
			modelview.setViewName("login");
			return modelview;
		}
		if(partner == null || partner.getPartnerName().equals("") || partner.getPartnerName().trim().equals("")){
			modelview = getManagerPartner();
			return modelview.addObject("Errmsg", "Please provide a correct Parthner Name!");
			}
		try {
			if (getPartnerWithName(partner.getPartnerName())) {
				throw new BusinessException(ExceptionConstant.INSUFFICIENT_PARAMS);
			}
			System.out.println("The content type is :" + partnerLogo.getContentType());
			if (partnerLogo.getContentType() == null || (!partnerLogo.getContentType().equals("image/png")
					&& !partnerLogo.getContentType().endsWith("image/jpeg"))) {
				return handleIOException();
			}
			int pId = partnerService.addNewPartner(partner);
			if (pId > 0) {
				String filename = partnerLogo.getOriginalFilename();
				Properties prop = new Properties();
				prop.load(getClass().getClassLoader().getResourceAsStream("variable.properties"));
				logger.info("The file name is : " + filename);
				logger.info("Does UPLOAD_DIRECTORY exist : " + prop.containsKey("UPLOAD_DIRECTORY")
						+ " and the value is : " + prop.getProperty("UPLOAD_DIRECTORY"));
				String path = prop.getProperty("UPLOAD_DIRECTORY");
				logger.info("PATH TO SAVE IMAGE IS : " + prop.getProperty("UPLOAD_DIRECTORY") + pId);
				if (!filename.equals("") && !filename.trim().equals("")) {
					byte[] bytes = partnerLogo.getBytes();
					BufferedOutputStream stream = new BufferedOutputStream(
							new FileOutputStream(new File(path + pId + ".png")));

					stream.write(bytes);
					stream.flush();
					stream.close();
				}
				modelview.addObject("msg", "" + partner.getPartnerName() + ", Partner Created Successfully!");
				modelview = getManagerPartner();
				modelview.addObject("msg", "" + partner.getPartnerName() + ", Partner Created Successfully!");
				return modelview;
			}
		} catch (BusinessException e) {
			e.printStackTrace();
			modelview = getManagerPartner();
			modelview.addObject("Errmsg", "Partner With name" + partner.getPartnerName() + " already exists!");
		}
		modelview = getManagerPartner();
		modelview.addObject("Errmsg", "Partner Creation Failed");
		return modelview;
	}

	private static BufferedImage resizeImage(BufferedImage originalImage, int type) {
		BufferedImage resizedImage = new BufferedImage(IMG_WIDTH, IMG_HEIGHT, type);
		Graphics2D g = resizedImage.createGraphics();
		g.drawImage(originalImage, 0, 0, IMG_WIDTH, IMG_HEIGHT, null);
		g.dispose();

		return resizedImage;
	}

	/*
	 * Update an existing Partner
	 */
	@SuppressWarnings("null")
	@PostMapping("/partnerUpdate")
	public ModelAndView updatePartner(Partner partner, HttpSession session, RedirectAttributes redirectAttrs) {
		ModelAndView modelview = new ModelAndView();
		User someObject = (User) session.getAttribute("userSessionObject");
		if (someObject == null) {
			modelview.setViewName("login");

			// modelview.setViewName("redirect:/");
			return modelview;
		}
		try {
			if (partner == null && partner.getPartnerId() == 0) {
				throw new BusinessException(ExceptionConstant.INSUFFICIENT_PARAMS);
			}
			if (partner.getAppIdAray() != null) {
				logger.info("inside updatePartner with : " + partner.getAppIdAray().length);
				for (int i = 0; i < partner.getAppIdAray().length; i++) {
					logger.info("element at index " + i + " is" + partner.getAppIdAray()[i]);
				}
			} else {
				logger.info("NO Array was recieved at all");
			}
			if (partnerService.updateExistingPartner(partner)) {
				redirectAttrs.addFlashAttribute("msg", "Partner Updated Successfully!");
				// modelview.setViewName("redirect:/managePartner");
				modelview = getManagerPartner();
				modelview.addObject("msg", "Partner Updated Successfully!");

				return modelview;
			}
		} catch (BusinessException e) {
			e.printStackTrace();
		}
		// redirectAttrs.addFlashAttribute("Errmsg", "No changes were Made!");
		modelview.addObject("Errmsg", "No changes were Made!");
		// modelview.setViewName("redirect:/managePartner");
		modelview = getManagerPartner();
		modelview.addObject("Errmsg", "No changes were Made!");

		return modelview;
	}

	/*
	 * Deliver the partner logo
	 */
	@GetMapping(value = "/api/image/{partnerId}")
	public void acknowledge(HttpServletResponse response, HttpServletRequest request,
			@PathVariable("partnerId") int partnerId) throws Exception {
		if (partnerId > 0) {
			Properties prop = new Properties();
			prop.load(getClass().getClassLoader().getResourceAsStream("variable.properties"));
			String path = prop.getProperty("UPLOAD_DIRECTORY");
			logger.info("THE PATH TO IMAGE IS : " + path + partnerId);
			InputStream myStream = new FileInputStream(new File(path + partnerId + ".png"));
			ByteArrayOutputStream buffer = new ByteArrayOutputStream();
			int nRead = 0;
			byte[] data = new byte[16384];
			while ((nRead = myStream.read(data, 0, data.length)) != -1) {
				logger.info("Value of nRead is : " + nRead);
				buffer.write(data, 0, nRead);
			}
			myStream.close();
			buffer.flush();
			response.getOutputStream().write(buffer.toByteArray());
			response.flushBuffer();
		}
		// System.out.println("Image delivered");
	}

	@GetMapping("/partner/name/{partnerName}")
	public boolean getPartnerWithName(@PathVariable("partnerName") String partnerName) {

		return partnerService.searchPartnerWithName(partnerName);

	}

	private ModelAndView getManagerPartner() {
		ModelAndView modelview = new ModelAndView();
		List<Partner> partners = new ArrayList<Partner>();
		List<Partner> allPartners = iadminService.getAllPartner();
		if (allPartners != null) {
			for (Partner partner : allPartners) {
				if (partner.getStatus().equals("ACTIVE")) {
					partners.add(partner);
				}
			}
		}
		modelview.addObject("partnerList", partners);
		try {
			modelview.addObject("appList", applicationService.getAllApps());
		} catch (BusinessException e) {
			e.printStackTrace();
			// handleIOException();
		}
		modelview.setViewName("createPartner");
		return modelview;
	}

}
