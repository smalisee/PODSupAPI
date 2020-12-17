package com.amos.podsupapi.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.amos.podsupapi.common.CommonUtils;
import com.amos.podsupapi.component.aws.AWSFileTransferComponent;
import com.amos.podsupapi.controller.WebResourceController;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import com.amos.podsupapi.exception.PoBusinessException;
import com.amos.podsupapi.model.PODFile;
import com.amos.podsupapi.repository.AutoFactoryPORepository;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;


@Service
@Transactional(propagation = Propagation.REQUIRES_NEW, noRollbackFor = PoBusinessException.class)
public class WebResourceServiceImpl implements WebResourceService {

	Logger logger = LogManager.getLogger(WebResourceController.class);
	
	@Autowired
	private AutoFactoryPORepository autoFactoryPORepository;

	@PersistenceContext
	private EntityManager entityManager;

	@Autowired
	private AWSFileTransferComponent awsFileTranfer;

	@Override
	public byte[] getDeliveryAllFile(String poNo, int imageId) throws Exception {

		PODFile file = autoFactoryPORepository.getFileByKey(poNo, imageId);

		String path = file.getS_url();

		byte[] imageData = null;
		try {
			imageData = awsFileTranfer.getObject(path);
		} catch (Exception e) {
			logger.warn(String.format("file not found on AWS %s.", path));
		}

		if (imageData == null) {
			imageData = CommonUtils.readFile(path);
		}

		if (imageData == null) {
			logger.warn(String.format("file not found %s.", path));
		}
		return imageData;
	}

	@Override
	public String getDeliveryDowloadAllFile(String poNo, int imageId) {

		PODFile file = autoFactoryPORepository.getFileByKey(poNo, imageId);

		String fileName = file.getS_file_name();
		
		return fileName;
	}
	

}
