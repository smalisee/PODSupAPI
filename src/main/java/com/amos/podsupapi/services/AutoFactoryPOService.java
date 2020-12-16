package com.amos.podsupapi.services;

import java.util.List;

import com.amos.podsupapi.dto.POManageReportSearchDTO;
import com.amos.podsupapi.dto.POManagementDTO;
import com.amos.podsupapi.dto.POManagentViewDTO;
import com.amos.podsupapi.dto.admin.AutoFactoryPODTO;
import com.amos.podsupapi.dto.report.POReportDTO;

public interface AutoFactoryPOService {
	
	public List<AutoFactoryPODTO> getAutoFactoryPO(int pono);
		
	public List<AutoFactoryPODTO> getAllAutoPO(int order, int pono);

//	public List<POManagementDTO> getAutoPODetail(String dateFrom, String dateTo, String orderNo, String phoneNo,
//			String cvCode, String channel, String status);

	public List<POManagementDTO> getAutoPODetail(POManageReportSearchDTO searceBean);
	
	public List<POReportDTO> getAutoPODetailReport(POManageReportSearchDTO searchBean);

	public List<POManagentViewDTO> getAutoPODetailView(String orderNo, String poNo);
	
//	public List<ShipToDTO> getAutoPO(int pono);//ต้องแก้ให้อยู่ใน shipto
	
}
