package com.cognizant.controller;

import java.util.List; 

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RestController;
import com.cognizant.exception.ResourceNotFoundException;
import com.cognizant.model.PensionerDetail;
import com.cognizant.repository.PensionerDetailRepository;
import com.cognizant.restclient.AuthorizationClient;
import com.cognizant.service.PensionarDetailServiceImpl;

@RestController
public class PensionerDetailsController {

	private static final Logger LOGGER = LoggerFactory.getLogger(PensionerDetailsController.class);
	private AuthorizationClient authorizationClient;
	private PensionerDetailRepository pensionerDetailRepository;

	public PensionerDetailsController(){}
	@Autowired
	public PensionerDetailsController(PensionerDetailRepository pensionerDetailRepository,
			PensionarDetailServiceImpl pensionarDetailServiceImpl, AuthorizationClient authorizationClient) {
		this.pensionerDetailRepository = pensionerDetailRepository;
		this.authorizationClient = authorizationClient;
	}
	
	@PostMapping("/pensionerDetail")
	public PensionerDetail findByAadhaarNumber(@RequestHeader("Authorization") String token,
			@RequestBody String aadhaarNumber) throws Exception {
		LOGGER.info("STARTED - findByAadhaarNumber");

		if (authorizationClient.authorization(token)) {
			PensionerDetail pensionerDetail = pensionerDetailRepository.findById(aadhaarNumber)
					.orElseThrow(() -> new ResourceNotFoundException("employee with aadhaar number not found"));
			LOGGER.info("END - findByAadhaarNumber");
			return pensionerDetail;
		} else {
			LOGGER.error("EXCEPTION - findByAadhaarNumber");
			throw new Exception("token is not valid");
		}
	}

	

	@GetMapping("/allDetails")
	public List<PensionerDetail> getAllDetail() {
		LOGGER.info("STARTED - getAllDetail");
		LOGGER.info("END - getAllDetail");
		return pensionerDetailRepository.findAll();

	}
	
}
