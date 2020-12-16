package com.amos.podsupapi.controller;

import java.util.Base64;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.joda.time.LocalDateTime;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.amos.podsupapi.dto.POManageReportSearchDTO;
import com.amos.podsupapi.services.POManagementReportService;

@RestController
public class ReportController {
  private static Logger logger = LogManager.getLogger(RoleController.class);

  @Autowired
  private POManagementReportService pamanageService;

  @RequestMapping(method = RequestMethod.GET, value = "/exportExcelPOmanagement", produces = { "application/vnd.ms-excel" })
  public ResponseEntity<String> getReportExportExcel(@RequestParam(name = "view", required = true) String view,
      @RequestParam(name = "dateFrom", required = false) String dateFrom,
      @RequestParam(name = "dateTo", required = false) String dateTo,
      @RequestParam(name = "orderNo", required = false) String orderNo,
      @RequestParam(name = "phoneNo", required = false) String phoneNo,
      @RequestParam(name = "cvCode", required = false) String cvCode,
      @RequestParam(name = "channel", required = false) String channel,
      @RequestParam(name = "status", required = false) String status) {

    POManageReportSearchDTO searchBean = new POManageReportSearchDTO();

    searchBean.setDateFrom(dateFrom);
    searchBean.setDateTo(dateTo);
    searchBean.setOrderNo(orderNo);
    searchBean.setPhoneNo(phoneNo);
    searchBean.setCvCode(cvCode);
    searchBean.setChannel(channel);
    searchBean.setStatus(status);

    try {
      HttpHeaders headers = new HttpHeaders();

      headers.add("Content-Disposition",
          String.format("filename=POManagementReportExport_%s_%s.xlsx", view, LocalDateTime.now().toString()));
      headers.add("Cache-Control", "no-cache, no-store, must-revalidate");
      headers.add("Pragma", "no-cache");
      headers.add("Expires", "0");

      byte[] encoded = Base64.getEncoder().encode(pamanageService.getReportExcel(view, searchBean));
      String encodedString = new String(encoded);

      return new ResponseEntity<>(encodedString, headers, HttpStatus.OK);
    } catch (Exception e) {
      logger.error(String.format("getReportExportExcel Error: %", e));
      return new ResponseEntity<>("INTERNAL_SERVER_ERROR", HttpStatus.INTERNAL_SERVER_ERROR);
    }
  }

  @RequestMapping(method = RequestMethod.GET, value = "/exportExcelSupplierPO", produces = { "application/vnd.ms-excel" })
  public ResponseEntity<String> getReportExportExcelSupplier(@RequestParam(name = "dateFrom", required = false) String dateFrom,
      @RequestParam(name = "dateTo", required = false) String dateTo,
      @RequestParam(name = "poNo", required = false) String poNo,
      @RequestParam(name = "vendor", required = false) String vendor,
      @RequestParam(name = "view", required = true) String view) {

    POManageReportSearchDTO search = new POManageReportSearchDTO();
    search.setDateFrom(dateFrom);
    search.setDateTo(dateTo);
    search.setPoNo(poNo);
    search.setVendor(vendor);

    try {
      HttpHeaders headers = new HttpHeaders();

      headers.add("Content-Disposition",
          String.format("filename=SupplierPOReportExport_%s_%s.xlsx", view, LocalDateTime.now().toString()));
      headers.add("Cache-Control", "no-cache, no-store, must-revalidate");
      headers.add("Pragma", "no-cache");
      headers.add("Expires", "0");

      byte[] encoded = Base64.getEncoder().encode(pamanageService.getReportExcel(view, search));
      String encodedString = new String(encoded);

      return new ResponseEntity<>(encodedString, headers, HttpStatus.OK);
    } catch (Exception e) {
      logger.error(String.format("getReportExportExcel Error: %", e));
      return new ResponseEntity<>("INTERNAL_SERVER_ERROR", HttpStatus.INTERNAL_SERVER_ERROR);
    }
  }
}
