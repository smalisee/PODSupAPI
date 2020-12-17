package com.amos.podsupapi.controller;

import java.text.ParseException;
import java.time.LocalDateTime;
import java.util.List;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.amos.podsupapi.common.ReturnCode;
import com.amos.podsupapi.dto.APIResultDTO;
import com.amos.podsupapi.dto.ReturnStatusDTO;
import com.amos.podsupapi.dto.SupplierPODTO;
import com.amos.podsupapi.dto.SupplierPODetailDTO;
import com.amos.podsupapi.model.PODFile;
import com.amos.podsupapi.services.SupplierService;

@RestController
public class SupplierController {
	
private static final Logger logger = LogManager.getLogger(SupplierController.class);

  @Autowired
  private SupplierService supplierService;

  @CrossOrigin
  @RequestMapping(value = "/supplier_listdata", method = RequestMethod.POST,
      produces = { MediaType.APPLICATION_JSON_UTF8_VALUE, "application/json; charset=UTF-8" })
  public ResponseEntity<APIResultDTO> getSupplierListData(@RequestParam(name = "dateFrom", required = false) String dateFrom,
      @RequestParam(name = "dateTo", required = false) String dateTo,
      @RequestParam(name = "poNo", required = false) String poNo,
      @RequestParam(name = "vendor", required = false) String vendor) {

    List<SupplierPODTO> result = supplierService.getSupplierPOListData(dateFrom, dateTo, poNo, vendor);

    return new ResponseEntity<>(new APIResultDTO(new ReturnStatusDTO(ReturnCode.SUCCESS), result), HttpStatus.OK);
  }

  @CrossOrigin
  @RequestMapping(value = "/get_supplier_detail", method = RequestMethod.GET,
      produces = { MediaType.APPLICATION_JSON_UTF8_VALUE })
  public ResponseEntity<APIResultDTO> getSupplierDetail(@RequestParam(name = "orderNo", required = false) String orderNo,
      @RequestParam(name = "poNo", required = false) String poNo,
      @RequestParam(name = "vendor", required = false) String vendor) {

    List<SupplierPODetailDTO> result = supplierService.getSupplierDetail(orderNo, poNo, vendor);

    return new ResponseEntity<>(new APIResultDTO(new ReturnStatusDTO(ReturnCode.SUCCESS), result), HttpStatus.OK);
  }

  // @CrossOrigin
  // @RequestMapping(value = "/update_supplier_po_detail", method = RequestMethod.POST,
  // produces = { MediaType.APPLICATION_JSON_UTF8_VALUE })
  // public ResponseEntity<APIResultDTO> updateSupplierPODetail(@RequestParam(name = "orderNo", required = false) String orderNo,
  // @RequestParam(name = "poNo", required = false) String poNo,
  // @RequestParam(name = "vendor", required = false) String vendor,
  // @RequestParam(name = "delivery_status", required = false) String delivery_status,
  // @RequestParam(name = "delivery_date", required = false) String delivery_date,
  // @RequestParam(name = "delivery_by", required = false) String delivery_by,
  // @RequestParam(name = "trackingNo", required = false) String trackingNo,
  // @RequestParam(name = "delivery_other", required = false) String delivery_other,
  // @RequestParam(name = "iserial", required = false) int iserial) throws ParseException {
  //
  // supplierService.updateSupplierPODetail(iserial, orderNo, poNo, vendor, delivery_status, delivery_date, delivery_by,
  // trackingNo,
  // delivery_other);
  // return new ResponseEntity<>(new APIResultDTO(new ReturnStatusDTO(ReturnCode.SUCCESS)), HttpStatus.OK);
  //
  // }

  @CrossOrigin
  @RequestMapping(value = "/update_supplier_po_detail", method = RequestMethod.POST,
      produces = { MediaType.APPLICATION_JSON_UTF8_VALUE, "application/json; charset=UTF-8" })
  public ResponseEntity<APIResultDTO> updateSupplierPODetail(@RequestParam(name = "orderNo", required = false) String orderNo,
      @RequestParam(name = "poNo", required = false) String poNo,
      @RequestParam(name = "vendor", required = false) String vendor,
      @RequestParam(name = "delivery_status", required = false) String delivery_status,
      @RequestParam(name = "delivery_date", required = false) String delivery_date,
      @RequestParam(name = "delivery_by", required = false) String delivery_by,
      @RequestParam(name = "trackingNo", required = false) String trackingNo,
      @RequestParam(name = "delivery_other", required = false) String delivery_other,
      @RequestParam(name = "iserial", required = false) String iserial,
      @RequestParam(name = "iuser", required = false) String iuser,
      @RequestParam(name = "remark", required = false) String remark,
      @RequestParam(name = "files", required = false) MultipartFile[] files)  {

	  try {
		  
		  supplierService.updateSupplierPODetail(iuser, iserial, orderNo, poNo, vendor, delivery_status, delivery_date, delivery_by,
			        trackingNo, delivery_other,remark, files);
		  
//		  supplierService.persistDeliveryFile(files, poNo , orderNo);
	  }
	  catch(Exception e) {
		  logger.error(String.format("update_supplier_po_detail %s %s Transaction was NOT Rollback [%s]", e.getClass().getName(),
					e.getMessage(), iuser, iserial, orderNo, poNo, vendor, delivery_status, delivery_date, delivery_by,
			        trackingNo, delivery_other, files), e);
			return new ResponseEntity<>(new APIResultDTO(new ReturnStatusDTO(ReturnCode.INTERNAL_WEB_ERROR)),
					HttpStatus.INTERNAL_SERVER_ERROR);
	  }
   
    return new ResponseEntity<>(new APIResultDTO(new ReturnStatusDTO(ReturnCode.SUCCESS)), HttpStatus.OK);

  }

}
