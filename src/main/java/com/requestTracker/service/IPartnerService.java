package com.requestTracker.service;

import org.springframework.stereotype.Service;

import com.requestTracker.domain.Partner;
import com.requestTracker.exception.BusinessException;

@Service
public interface IPartnerService {

	public int addNewPartner(Partner	partner);

	public boolean updateExistingPartner(Partner partner) throws BusinessException;

	public String getPartnerLogoLocation(int partnerId);
	public boolean searchPartnerWithName(String partnerName);
}
