package com.amos.podsupapi.services;

import java.util.List;

import org.springframework.web.multipart.MultipartFile;

import com.amos.podsupapi.common.ReturnCode;
import com.amos.podsupapi.dto.POManageReportSearchDTO;
import com.amos.podsupapi.dto.SupplierPODTO;
import com.amos.podsupapi.dto.SupplierPODetailDTO;
import com.amos.podsupapi.dto.report.ExcelReportInterface;
import com.amos.podsupapi.exception.PoBusinessException;

public interface SupplierService {

  List<SupplierPODTO> getSupplierPOListData(String dateFrom, String dateTo, String poNo, String vendor);

  List<SupplierPODetailDTO> getSupplierDetail(String orderNo, String poNo, String vendor);

  public ReturnCode updateSupplierPODetail(String iuser, String iserial, String orderNo, String poNo, String vendor, String delivery_status,
      String delivery_date,
      String delivery_by, String trackingNo, String delivery_other, String remark , MultipartFile[] files) throws PoBusinessException,Exception;

//  ReturnCode addFilePODetail(MultipartFile[] files) throws Exception;

  List<? extends ExcelReportInterface> getSupplierPODetailReport(POManageReportSearchDTO searchBean);

}
