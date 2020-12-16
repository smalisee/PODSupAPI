package com.amos.podsupapi.controller;

import java.util.List;

import javax.persistence.NoResultException;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.http.MediaType;
import com.amos.podsupapi.common.ReturnCode;
import com.amos.podsupapi.dto.APIResultDTO;
import com.amos.podsupapi.dto.POManageReportSearchDTO;
import com.amos.podsupapi.dto.POManagementDTO;
import com.amos.podsupapi.dto.POManagentViewDTO;
import com.amos.podsupapi.dto.ReturnStatusDTO;
import com.amos.podsupapi.dto.admin.AutoFactoryPODTO;
import com.amos.podsupapi.services.AutoFactoryPOService;
import com.amos.podsupapi.services.WebResourceService;

@RestController
public class AutoFactoryPOController {

  @Autowired
  private AutoFactoryPOService autoFactoryPOService;
  
  @Autowired
  private WebResourceService webResourceService;

  private static Logger logger = LogManager.getLogger(AutoFactoryPOController.class);

  @CrossOrigin
  @GetMapping(value = "/get_autopo", produces = { "application/json; charset=UTF-8" })
  public ResponseEntity<APIResultDTO> getAutoFactoryPO(@RequestParam(name = "pono", required = false) int pono) {
    List<AutoFactoryPODTO> result = autoFactoryPOService.getAutoFactoryPO(pono);
    return new ResponseEntity<>(new APIResultDTO(new ReturnStatusDTO(ReturnCode.SUCCESS), result), HttpStatus.OK);

  }

  @CrossOrigin
  @GetMapping(value = "/get_autopo_detail", produces = { "application/json; charset=UTF-8" })
  public ResponseEntity<APIResultDTO> getAutoPODetail(
      @RequestParam(name = "dateFrom", required = false) String dateFrom,
      @RequestParam(name = "dateTo", required = false) String dateTo,
      @RequestParam(name = "orderNo", required = false) String orderNo,
      @RequestParam(name = "phoneNo", required = false) String phoneNo,
      @RequestParam(name = "cvCode", required = false) String cvCode,
      @RequestParam(name = "channel", required = false) String channel,
      @RequestParam(name = "status", required = false) String status) {
    List<POManagementDTO> result = null;
    try {
      logger.debug(String.format("get_autopo_detail [%s, %s, %s, %s, %s, %s,%s] ", dateFrom, dateTo, orderNo,
          phoneNo, cvCode, channel, status));
      POManageReportSearchDTO searchBean = new POManageReportSearchDTO();

      searchBean.setDateFrom(dateFrom);
      searchBean.setDateTo(dateTo);
      searchBean.setOrderNo(orderNo);
      searchBean.setPhoneNo(phoneNo);
      searchBean.setCvCode(cvCode);
      searchBean.setChannel(channel);
      searchBean.setStatus(status);

      result = autoFactoryPOService.getAutoPODetail(searchBean);
    } catch (Exception e) {
      logger.debug(String.format("ERROR get_autopo_detail [%s, %s, %s, %s, %s, %s,%s] ", dateFrom, dateTo,
          orderNo, phoneNo, cvCode, channel, status), e);
      return new ResponseEntity<>(new APIResultDTO(new ReturnStatusDTO(ReturnCode.INTERNAL_WEB_ERROR)),
          HttpStatus.OK);
    }

    return new ResponseEntity<>(new APIResultDTO(new ReturnStatusDTO(ReturnCode.SUCCESS), result), HttpStatus.OK);

  }

  @CrossOrigin
  @GetMapping(value = "/get_autopo_detail_by_id", produces = { "application/json; charset=UTF-8" })
  public ResponseEntity<APIResultDTO> getAutoPODetailById(
      @RequestParam(name = "orderNo", required = false) String orderNo,
      @RequestParam(name = "poNo", required = false) String poNo) {
    List<POManagentViewDTO> result = null;
    try {
      logger.debug(String.format("get_autopo_detail [%s, %s] ", orderNo, poNo));

      result = autoFactoryPOService.getAutoPODetailView(orderNo, poNo);
    } catch (Exception e) {
      logger.debug(String.format("ERROR get_autopo_detail [%s, %s] ", orderNo, poNo), e);
      return new ResponseEntity<>(new APIResultDTO(new ReturnStatusDTO(ReturnCode.INTERNAL_WEB_ERROR)),
          HttpStatus.OK);
    }

    return new ResponseEntity<>(new APIResultDTO(new ReturnStatusDTO(ReturnCode.SUCCESS), result), HttpStatus.OK);

  }
  

}
