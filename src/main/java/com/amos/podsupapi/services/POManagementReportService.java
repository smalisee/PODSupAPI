package com.amos.podsupapi.services;

import java.util.List;

import com.amos.podsupapi.dto.POManageReportSearchDTO;

public interface POManagementReportService {

  List<?> getReportView(String view, POManageReportSearchDTO searchBean);

  byte[] getReportExcel(String view, POManageReportSearchDTO searchBean) throws Exception;

  // byte[] getSupplierPOReportExcel(POManageReportSearchDTO searchBean) throws Exception;
}
