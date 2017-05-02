package com.requestTracker.service;

import java.io.UnsupportedEncodingException;
import java.util.List;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.Base64Utils;

import com.requestTracker.dao.IPartnerDao;
import com.requestTracker.domain.Application;
import com.requestTracker.domain.Partner;
import com.requestTracker.exception.BusinessException;

@Service
public class PartnerServiceimpl implements IPartnerService {
	@Autowired
	IPartnerDao partnerDao;
	@Autowired
	IApplicationService applicationService;

	Logger logger = Logger.getLogger(PartnerServiceimpl.class);

	public int addNewPartner(Partner partner) {
		int partnerId = 0;

		partnerId = partnerDao.addNewPartner(partner);
		if (partnerId > 0 && partner.getAppIdAray() != null && partner.getAppIdAray().length > 0) {
			partner.setPartnerId(partnerId);
			if (mapPartnerWithApps(partner)) {
			}
		}
		return partnerId;
	}

	private boolean mapPartnerWithApps(Partner partner) {
		partner.setAppKeyAray(generateAppKeysForPartner(partner));
		return (partnerDao.mapPartnerWithApps(partner));
	}

	private String[] generateAppKeysForPartner(Partner partner) {
		String[] partnerAppKeyArray = new String[partner.getAppIdAray().length];
		logger.info("THe length of recieved partner.getAppIdAray() is : " + partner.getAppIdAray().length);
		for (int i = 0; i < partner.getAppIdAray().length; i++) {
			String stringToEnccrypt = "" + partner.getPartnerId() + partner.getAppIdAray()[i];
			try {
				// Encode using basic encoder LAYER =1
				String base64encodedString = Base64Utils.encodeToString(stringToEnccrypt.getBytes("utf-8"));
				System.out.println("Base64 Encoded String (Basic) :" + base64encodedString);
				// Encode again LAYER =2
				base64encodedString = Base64Utils.encodeToString(base64encodedString.getBytes("utf-8"));
				System.out.println("Dual encryption Key : " + base64encodedString);
				partnerAppKeyArray[i] = base64encodedString;
			} catch (UnsupportedEncodingException e) {
				System.out.println("Error :" + e.getMessage());
			}

		}
		return partnerAppKeyArray;
	}

	@SuppressWarnings("null")
	public boolean updateExistingPartner(Partner partner) throws BusinessException {
		List<Application> applications = null;
		applications = applicationService.getAppsByPartnerId(partner.getPartnerId());
		boolean match = false;
		if ((applications == null || applications.size() == 0)
				&& (partner.getAppIdAray() == null || partner.getAppIdAray().length == 0)) {
			logger.info("Both Lists were null so : NO ACTION REQUIRED");
		} else if ((applications == null || applications.size() == 0)
				&& (partner.getAppIdAray() != null || partner.getAppIdAray().length > 0)) {
			for (int i = 0; i < partner.getAppIdAray().length; i++) {
				logger.info("New App To Be Added : " + partner.getAppIdAray()[i]);
				partnerDao.mapPartnerWithApps(partner.getPartnerId(), partner.getAppIdAray()[i],
						partnerAppKeyGenerator(partner.getPartnerId(), partner.getAppIdAray()[i]));
			}
			return true;
		} else if ((applications != null || applications.size() > 0)
				&& (partner.getAppIdAray() == null || partner.getAppIdAray().length == 0)) {
			logger.info("AppId Array is null so		:	 ACTION REQUIRED	:	UNMAP ALL APPS OF PARTNER "
					+ partner.getPartnerId());
			for (Application application : applications) {
				logger.info("Removing mapping for APP-ID	:	" + application.getAppId() + "	and PARTNER-ID	"
						+ partner.getPartnerId());
				partnerDao.removePartnerAppMapping(partner.getPartnerId(), application.getAppId());
			}
			return true;
		} else {
			logger.info("NEITHER OF THE LISTS ARE NULL");
			logger.info("ADDING IF NEW APPS ARE OPTED");
			for (int i = 0; i < partner.getAppIdAray().length; i++) {
				match = false;
				for (Application application : applications) {
					match = partner.getAppIdAray()[i] == application.getAppId();
					if (match) {
						logger.info("Matched : " + partner.getAppIdAray()[i] + " BREAKING THE INNER LOOP");
						break;
					}
				}
				if (match) {
					logger.info("Matched : " + partner.getAppIdAray()[i] + " No Action Required");
				} else {
					logger.info("New App To Be Added : " + partner.getAppIdAray()[i]);
					partnerDao.mapPartnerWithApps(partner.getPartnerId(), partner.getAppIdAray()[i],
							partnerAppKeyGenerator(partner.getPartnerId(), partner.getAppIdAray()[i]));
				}

			}
			logger.info("REMOVING OLD APPS ");
			for (Application application : applications) {
				match = false;
				for (int i = 0; i < partner.getAppIdAray().length; i++) {
					match = application.getAppId() == partner.getAppIdAray()[i];
					if (match) {
						logger.info("Matched : "+application.getAppId()+" BREAKING THE INNER LOOP");
						break;
					}
				}
				if (match) {
					logger.info("Matched :  No Action Required");
				} else {
					logger.info("Previous App To Be Removed : " + application.getAppId());
					partnerDao.removePartnerAppMapping(partner.getPartnerId(), application.getAppId());
				}
			}
			return true;
		}
		return false;

	}

	private String partnerAppKeyGenerator(int paernerId, int appId) {
		String partnerAppKey = null;
		String stringToEnccrypt = "" + paernerId + appId;
		try {
			// Encode using basic encoder LAYER =1
			String base64encodedString =  Base64Utils.encodeToString(stringToEnccrypt.getBytes("utf-8"));
			System.out.println("Base64 Encoded String (Basic) :" + base64encodedString);
			// Encode again LAYER =2
			base64encodedString = Base64Utils.encodeToString(base64encodedString.getBytes("utf-8"));
			System.out.println("Dual encryption Key : " + base64encodedString);
			partnerAppKey = base64encodedString;
		} catch (UnsupportedEncodingException e) {
			System.out.println("Error :" + e.getMessage());
		}

		return partnerAppKey;
	}

	public String getPartnerLogoLocation(int partnerId) {
		return partnerDao.fetchPartnerLogoLocation(partnerId);
	}
	public boolean searchPartnerWithName(String partnerName) {
		return partnerDao.doesPartnerWithNameExist(partnerName);
		
	}

}
