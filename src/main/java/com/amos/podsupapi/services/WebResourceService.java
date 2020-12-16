package com.amos.podsupapi.services;


public interface WebResourceService {

	byte[] getDeliveryAllFile( String poNo , int imageId) throws Exception;
	
	String getDeliveryDowloadAllFile( String poNo , int imageId);
	
	
//	 String getFileByKey(String poNo, int imageId);

}
