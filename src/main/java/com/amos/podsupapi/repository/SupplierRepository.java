package com.amos.podsupapi.repository;

import java.text.ParseException;
import java.util.List;

import com.amos.podsupapi.common.ReturnCode;
import com.amos.podsupapi.model.FactoryPODStatus;
import com.amos.podsupapi.model.PODFile;

public interface SupplierRepository {

  List<Object[]> getSupplierPODetailWithPONO(String dateFrom, String dateTo, String poNo, String vendor, String queryAll);

  List<Object[]> getPODetail(String orderNo, String poNo, String vendor, String sqlSelect);

  ReturnCode updateFactoryPODStatus(String orderNo, String poNo, String vendor, String delivery_status, String delivery_date,
      String delivery_by, String trackingNo) throws ParseException;

  ReturnCode updateFactoryPODStatusDetail(FactoryPODStatus getPODDetByISerial, String delivery_status, String delivery_date,
      String delivery_by, String trackingNo, String delivery_other) throws ParseException;

  FactoryPODStatus fidPODStatusByID(Integer i_serialFacPODStatus);

  void addFileDB(PODFile fileDB);

  Integer insertPODStatus(String iuser, String orderNo, String poNo, String vendor, String delivery_status, String delivery_date,
      String delivery_by, String delivery_other, String trackingNo, String remark) throws ParseException;
  
//  Integer getIserialStatus (String poNo, String orderNo);
  
  boolean checkExitsPODStatus(String orderNo, String poNo);

}
