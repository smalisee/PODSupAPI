package com.amos.podsupapi.services;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.amos.podsupapi.common.ReturnCode;
import com.amos.podsupapi.dto.admin.ShipToDTO;
import com.amos.podsupapi.model.ShipTo;
import com.amos.podsupapi.repository.AutoFactoryPORepository;
import com.amos.podsupapi.repository.ShipToRepository;
import com.fasterxml.jackson.databind.ObjectMapper;

@Service
@Transactional(propagation = Propagation.REQUIRES_NEW)
public class ShipToServiceImpl implements ShipToService {

  @Autowired
  private AutoFactoryPORepository autoFactoryPORepository;

  @Autowired
  private ShipToRepository shipToRepository;

  @Override
  public List<ShipToDTO> getAutoPO(int pono) {
    if (autoFactoryPORepository.autoPOExists(pono)) {

      List<ShipToDTO> shipToList = new ArrayList<>();
      shipToList = getShipTo(pono);

      return shipToList;

    } else {
      return new ArrayList<>();
    }

  }

  @Override
  public ReturnCode updateShipTo(String payload) throws Exception {
    ObjectMapper mapper = new ObjectMapper();
    ShipTo shipto = mapper.readValue(payload, ShipTo.class);

    if (autoFactoryPORepository.autoPOCheckStatus(shipto.getOrder())) {
      shipToRepository.updateShipTo(shipto);

      return ReturnCode.SUCCESS;
    } else {
      return ReturnCode.STATUS_AUTO_PO;
    }
  }

  @Override
  public List<ShipToDTO> getShipTo(int pono) {
    System.out.println(pono);
    List<ShipToDTO> shiptoL = new ArrayList<>();
    for (ShipTo source : shipToRepository.getShipto(pono)) {
      ShipToDTO target = new ShipToDTO();
      BeanUtils.copyProperties(source, target);
      shiptoL.add(target);
    }

    return shiptoL;
  }

}
