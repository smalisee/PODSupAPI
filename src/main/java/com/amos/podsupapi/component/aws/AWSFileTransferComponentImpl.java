package com.amos.podsupapi.component.aws;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.stereotype.Component;

import com.amazonaws.ClientConfiguration;
import com.amazonaws.auth.AWSCredentials;
import com.amazonaws.auth.AWSStaticCredentialsProvider;
import com.amazonaws.auth.BasicAWSCredentials;
import com.amazonaws.regions.Regions;
import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.AmazonS3ClientBuilder;
import com.amazonaws.services.s3.model.CreateBucketRequest;
import com.amazonaws.services.s3.model.GetBucketLocationRequest;
import com.amazonaws.services.s3.model.ObjectMetadata;
import com.amazonaws.services.s3.model.S3Object;
import com.amazonaws.services.s3.model.S3ObjectInputStream;
import com.amazonaws.util.IOUtils;
import com.amos.podsupapi.config.AppConfig;

import java.io.ByteArrayInputStream;
import java.io.IOException;

import javax.annotation.PostConstruct;

@Component
public class AWSFileTransferComponentImpl implements AWSFileTransferComponent {

	private String awsBucketName;
	
	private AmazonS3 s3client;

	private static final Logger logger = LogManager.getLogger(AWSFileTransferComponentImpl.class);
	
	@PostConstruct
	private void postConstruct() {
		//initialize();
	}

	private void initialize() {
		ClientConfiguration config = new ClientConfiguration();
		config.setProxyHost(AppConfig.getAwsProxyHost());
		config.setProxyPort(AppConfig.getAwsProxyPort());
		
		awsBucketName = AppConfig.getAwsBucketName();
		
		AWSCredentials credentials = new BasicAWSCredentials(AppConfig.getAwsAccessKey(), AppConfig.getAwsSecretKey());
		try {
			s3client = AmazonS3ClientBuilder.standard().withCredentials(new AWSStaticCredentialsProvider(credentials))
					.withRegion(Regions.AP_SOUTHEAST_1).withClientConfiguration(config).build();
			verifyBucketExist();
		}
		catch(Exception e) {
			logger.warn("S3client can't connect to AWS");
		}
		
	}
	
	private void verifyBucketExist() {
		if (!s3client.doesBucketExistV2(awsBucketName)) {
			s3client.createBucket(new CreateBucketRequest(awsBucketName));
			String bucketLocation = s3client.getBucketLocation(new GetBucketLocationRequest(awsBucketName));
			logger.info("Bucket location: " + bucketLocation);
		}
	}

	@Override
	public void putObject(String key, byte[] source) throws IOException {
		if(s3client == null ) {
			initialize();
		}
		try(ByteArrayInputStream bis = new ByteArrayInputStream(source)){
			logger.debug("AWS putObject: " + key);
			ObjectMetadata metadata = new ObjectMetadata();
			metadata.setContentLength(source.length);
			s3client.putObject(awsBucketName, removeFirstSlash(key), bis, metadata);
		}
	}

	@Override
	public byte[] getObject(String key) throws IOException {
		if(s3client == null ) {
			initialize();
		}
		byte[] result = null;
		key=removeFirstSlash(key);
		logger.debug("AWS getObject: " + key);
		if (s3client.doesObjectExist(awsBucketName, key)) {
			S3Object s3object = s3client.getObject(awsBucketName, key);
			try (S3ObjectInputStream inputStream = s3object.getObjectContent()) {
				result = IOUtils.toByteArray(inputStream);
			}
		}
		return result;
	}

	private String removeFirstSlash(String key) {
		if (key != null && key.length() > 0 && key.charAt(0) == '/') {
			key = key.substring(1, key.length());
		}
		return key;
	}
	
}
