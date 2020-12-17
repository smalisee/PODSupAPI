package com.amos.podsupapi.services;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.amos.podsupapi.common.ReturnCode;
import com.amos.podsupapi.dto.admin.ShipToDTO;
import com.amos.podsupapi.model.AutoFactoryPO;
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
  public ShipToDTO getAutoPO(int pono) {
    if (autoFactoryPORepository.autoPOExists(pono)) {

      ShipToDTO shipTo = getShipTo(pono);

      return shipTo;

    } else {
      return null;
    }

  }

  @Override
  public ReturnCode updateShipTo(String payload) throws Exception {
    ObjectMapper mapper = new ObjectMapper();
    ShipTo shipto = mapper.readValue(payload, ShipTo.class);

    if (autoFactoryPORepository.autoPOCheckStatus(shipto.getOrder())) {
      // find shipto from p_shipto
      if (shipToRepository.getShipToByOrder(shipto.getOrder()) == null) {
        List<AutoFactoryPO> aupoByOrder = autoFactoryPORepository.getAutoFactoryPOByOrder(shipto.getOrder());
        for (AutoFactoryPO result : aupoByOrder) {
          shipto.setAccount(result.getAccount());
        }
        // add shipto
        shipToRepository.addShipto(shipto);

        // update status p_ordsum and p_auto_factory_polink
        for (AutoFactoryPO result : aupoByOrder) {
          if (result.getShipTo().equalsIgnoreCase("C") || result.getShipTo().equalsIgnoreCase("S")) {
            result.setShipTo("R");
            autoFactoryPORepository.updatePOrdsum(result.getOrder());
            autoFactoryPORepository.updatePOFacoryShipto(result.getPono());
          }
        }

      } else {
        shipToRepository.updateShipTo(shipto);
      }
      return ReturnCode.SUCCESS;
    } else {
      return ReturnCode.STATUS_AUTO_PO;
    }
  }

  @Override
  public ShipToDTO getShipTo(int pono) {

    String sqlSelect = "SELECT " +
        "       CASE" +
        "          WHEN ordsum.c_shipto <> 'S'" +
        "          THEN" +
        "                shipto.s_firstname" +
        "             || ','" +
        "             || shipto.s_name" +
        "             || ','" +
        "             || NVL (shipto.s_address7, '-')" +
        "             || ','" +
        "             || shipto.s_address1" +
        "             || ','" +
        "             || shipto.s_address2" +
        "             || ','" +
        "             || shipto.s_address3" +
        "             || ','" +
        "             || shipto.s_city" +
        "             || ','" +
        "             || TO_CHAR (shipto.i_zipcode)" +
        "             || ','" +
        "             || shipto.s_phoneno1" +
        "          ELSE" +
        "                cus.s_firstname" +
        "             || ','" +
        "             || cus.s_name" +
        "             || ','" +
        "             || NVL (cus.s_address7, '-')" +
        "             || ','" +
        "             || cus.s_address1" +
        "             || ','" +
        "             || cus.s_address2" +
        "             || ','" +
        "             || cus.s_address3" +
        "             || ','" +
        "             || cus.s_city" +
        "             || ','" +
        "             || TO_CHAR (cus.i_zipcode)" +
        "             || ','" +
        "             || cus.s_phoneno1" +
        "       END" +
        "          AS shipto" +
        "  FROM p_ordsum ordsum," +
        "       p_shipto shipto," +
        "       p_customer cus," +
        "       (SELECT * FROM P_AUTO_FACTORY_POLINK) polink" +
        " WHERE     ordsum.c_shipto IN ('R', 'S', 'C', 'N')" +
        "       AND ordsum.i_order = shipto.i_order(+)" +
        "       AND ordsum.i_account = cus.i_account" +
        "       AND ordsum.i_order = polink.i_order" +
        "       AND polink.i_pono = :pono";

    List<Object[]> dataShipto = shipToRepository.getShipto(sqlSelect, pono);
    String[] spliteStrShipto = (dataShipto.toString().substring(1, dataShipto.toString().length() - 1)).split(",");
    // System.out.println(spliteStrShipto);

    ShipToDTO shiptoDTO = new ShipToDTO();
    shiptoDTO.setFirstName(spliteStrShipto[0]);
    shiptoDTO.setName(spliteStrShipto[1]);
    shiptoDTO.setAddress7(spliteStrShipto[2]);
    shiptoDTO.setAddress1(spliteStrShipto[3]);
    shiptoDTO.setAddress2(spliteStrShipto[4]);
    shiptoDTO.setAddress3(spliteStrShipto[5]);
    shiptoDTO.setCity(spliteStrShipto[6]);
    shiptoDTO.setZipCode(Integer.valueOf(spliteStrShipto[7]));
    shiptoDTO.setPhoneNo1(spliteStrShipto[8]);

    // for (ShipTo source : shipToRepository.getShipto(pono)) {
    // ShipToDTO target = new ShipToDTO();
    // BeanUtils.copyProperties(source, target);
    // shiptoL.add(target);
    // }

    return shiptoDTO;
  }

}
