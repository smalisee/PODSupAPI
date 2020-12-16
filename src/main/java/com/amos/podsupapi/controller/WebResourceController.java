package com.amos.podsupapi.controller;

import java.io.IOException;

import javax.persistence.NoResultException;
import javax.servlet.http.HttpServletRequest;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.InputStreamResource;
import org.springframework.core.io.Resource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.amos.podsupapi.services.WebResourceService;

@RestController
public class WebResourceController {

	@Autowired
	private WebResourceService webResourceService;


	private static Logger logger = LogManager.getLogger(AutoFactoryPOController.class);

	@RequestMapping(value = "/getDeliveryImage_file", method = RequestMethod.GET, produces = MediaType.APPLICATION_PDF_VALUE)
	public ResponseEntity<byte[]> getDeliveryAllFileSystem(@RequestParam("poNo") String poNo,
			@RequestParam("imageId") Integer imageId, HttpServletRequest request) {

		logger.debug(String.format("getDeliveryAll_file : %s, %d", poNo, imageId));

		try {
			byte[] imageData = webResourceService.getDeliveryAllFile(poNo, imageId);
			
			

			if (imageData == null) {
				return new ResponseEntity<>(HttpStatus.NOT_FOUND);
			}
			HttpHeaders headers = new HttpHeaders();
			headers.setContentType(MediaType.APPLICATION_PDF);
			
			// Here you have to set the actual filename of your pdf
			String filename = webResourceService.getDeliveryDowloadAllFile(poNo , imageId);
			headers.setContentDispositionFormData(filename, filename);
			headers.setCacheControl("must-revalidate, post-check=0, pre-check=0");
			ResponseEntity<byte[]> response = new ResponseEntity<>(imageData, headers, HttpStatus.OK);
			return response;
//			return new ResponseEntity<>(imageData, HttpStatus.OK);
		} catch (NoResultException e) {
			logger.warn(String.format("DeliveryAllFile not found : %s, %d", poNo, imageId));
			return new ResponseEntity<>(HttpStatus.NOT_FOUND);
		} catch (Exception e) {
			return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}
}
