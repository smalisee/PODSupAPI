package com.amos.podsupapi.controller;

import java.util.ArrayList;
import java.util.List;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.amos.podsupapi.common.ReturnCode;
import com.amos.podsupapi.dto.APIResultDTO;
import com.amos.podsupapi.dto.ChannelDTO;
import com.amos.podsupapi.dto.OrderStatusDTO;
import com.amos.podsupapi.dto.ProdLineDTO;
import com.amos.podsupapi.dto.ReturnStatusDTO;
import com.amos.podsupapi.dto.VendorDTO;
import com.amos.podsupapi.model.Key;
import com.amos.podsupapi.repository.KeyRepository;
import com.amos.podsupapi.services.KeyService;
import com.amos.podsupapi.viewmodel.CompanyViewModel;

@RestController
public class KeyController {
  private static final Logger LOGGER = LogManager.getLogger(KeyController.class);

  @Autowired
  private KeyRepository keyRepository;

  @Autowired
  private KeyService keyService;

  @CrossOrigin
  @RequestMapping(method = RequestMethod.GET,
      value = "/get_all_company",
      path = "/get_all_company",
      produces = { "application/json; charset=UTF-8" })
  public ResponseEntity<APIResultDTO> getAllCompany() {
    LOGGER.debug("getAllCompany()");
    List<Key> listCompany = keyRepository.getAllCompany();

    List<CompanyViewModel> compList = new ArrayList<>();

    for (Key key : listCompany) {
      CompanyViewModel comp = new CompanyViewModel();
      comp.setItem(key.getPk().getItem());
      comp.setName(key.getName());
      comp.setValue(key.getPk().getValue());
      comp.setSorting(key.getSorting());
      compList.add(comp);
    }

    APIResultDTO ret = new APIResultDTO(new ReturnStatusDTO(ReturnCode.SUCCESS), compList);
    return new ResponseEntity<>(ret, HttpStatus.OK);

  }

  @CrossOrigin
  @GetMapping(value = "/get_all_vendor", produces = { "application/json; charset=UTF-8" })
  public ResponseEntity<APIResultDTO> getAllVendor() {
    LOGGER.debug("getAllVendor()");
    List<VendorDTO> listCompany = keyService.getAllVendor();
    APIResultDTO ret = new APIResultDTO(new ReturnStatusDTO(ReturnCode.SUCCESS), listCompany);
    return new ResponseEntity<>(ret, HttpStatus.OK);

  }

  @CrossOrigin
  @GetMapping(value = "/get_all_prodline", produces = { "application/json; charset=UTF-8" })
  public ResponseEntity<APIResultDTO> getAllProdline() {
    LOGGER.debug("getAllProdline()");
    List<ProdLineDTO> listCompany = keyService.getAllProdline();
    APIResultDTO ret = new APIResultDTO(new ReturnStatusDTO(ReturnCode.SUCCESS), listCompany);
    return new ResponseEntity<>(ret, HttpStatus.OK);

  }

  @CrossOrigin
  @GetMapping(value = "/get_all_channel", produces = { "application/json; charset=UTF-8" })
  public ResponseEntity<APIResultDTO> getAllChannel() {
    LOGGER.debug("getAllChannel");
    List<ChannelDTO> listCompany = keyService.getAllChannel();
    APIResultDTO ret = new APIResultDTO(new ReturnStatusDTO(ReturnCode.SUCCESS), listCompany);
    return new ResponseEntity<>(ret, HttpStatus.OK);

  }

  @CrossOrigin
  @GetMapping(value = "/get_all_orderstatus", produces = { "application/json; charset=UTF-8" })
  public ResponseEntity<APIResultDTO> getAllOrderStatus() {
    LOGGER.debug("getAllOrderStatus()");
    List<OrderStatusDTO> listCompany = keyService.getAllOrderStatus();
    APIResultDTO ret = new APIResultDTO(new ReturnStatusDTO(ReturnCode.SUCCESS), listCompany);
    return new ResponseEntity<>(ret, HttpStatus.OK);

  }

  @CrossOrigin
  @GetMapping(value = "/get_all_delivery_by", produces = { "application/json; charset=UTF-8" })
  public ResponseEntity<APIResultDTO> getAllDeliveryBy() {
    LOGGER.debug("getAllDeliveryBy()");
    List<OrderStatusDTO> listCompany = keyService.getAllDeliveryBy();
    APIResultDTO ret = new APIResultDTO(new ReturnStatusDTO(ReturnCode.SUCCESS), listCompany);
    return new ResponseEntity<>(ret, HttpStatus.OK);

  }

  @CrossOrigin
  @GetMapping(value = "/get_all_province", produces = { "application/json; charset=UTF-8" })
  public String getAllProvince() {
    LOGGER.debug("get_all_province()");
    String listCompany = keyService.getAllProvince();
    // APIResultDTO ret = new APIResultDTO(new ReturnStatusDTO(ReturnCode.SUCCESS), listCompany);
    return listCompany;
    // return new ResponseEntity<>(ret, HttpStatus.OK);

  }

}
