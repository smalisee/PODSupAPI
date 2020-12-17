package com.amos.podsupapi.controller;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.amos.podsupapi.common.ReturnCode;
import com.amos.podsupapi.dto.APIResultDTO;
import com.amos.podsupapi.dto.ReturnStatusDTO;
import com.amos.podsupapi.dto.admin.ShipToDTO;
import com.amos.podsupapi.services.ShipToService;
import com.fasterxml.jackson.core.JsonParseException;

@RestController
public class ShipToController {

  @Autowired
  private ShipToService shipToService;

  private static Logger logger = LogManager.getLogger(ShipToController.class);

  //
  @CrossOrigin
  @GetMapping(value = "/get_shipto", produces = { "application/json; charset=UTF-8" })
  public ResponseEntity<APIResultDTO> getAutoPO(@RequestParam(name = "pono", required = false) int pono) {
    ShipToDTO result = shipToService.getAutoPO(pono);
    if (result == null) {
      return new ResponseEntity<>(new APIResultDTO(new ReturnStatusDTO(ReturnCode.NO_DATA_AUTO_PO), result), HttpStatus.OK);
    } else {
      return new ResponseEntity<>(new APIResultDTO(new ReturnStatusDTO(ReturnCode.SUCCESS), result), HttpStatus.OK);
    }

  }

  //
  @CrossOrigin
  @RequestMapping(value = "/update_shipto", consumes = { "application/json" }, produces = {
      "application/json; charset=UTF-8" })
  public ResponseEntity<APIResultDTO> updateShipTo(@RequestBody String payload) {

    ReturnCode code = ReturnCode.SUCCESS;

    try {
      code = shipToService.updateShipTo(payload);
    } catch (JsonParseException ex) {
      code = ReturnCode.INVALID_REQUEST_PARAM;
      logger.error(String.format("Update ShipTo ERROR. payload: %s", payload), ex);
    } catch (Exception ex) {
      code = ReturnCode.INTERNAL_WEB_SERVICE_ERROR;
      logger.error(String.format("Update ShipTo ERROR. payload: %s", payload), ex);
    }
    return new ResponseEntity<>(new APIResultDTO(new ReturnStatusDTO(code)), HttpStatus.OK);
  }
  
  //HEllo By Tle

}
