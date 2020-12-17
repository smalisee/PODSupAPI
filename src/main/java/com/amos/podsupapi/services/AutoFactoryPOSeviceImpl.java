package com.amos.podsupapi.services;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.amos.podsupapi.common.CommonUtils;
import com.amos.podsupapi.controller.AutoFactoryPOController;
import com.amos.podsupapi.dto.ItemDetailDTO;
import com.amos.podsupapi.dto.POManageReportSearchDTO;
import com.amos.podsupapi.dto.POManagementDTO;
import com.amos.podsupapi.dto.POManagentViewDTO;
import com.amos.podsupapi.dto.admin.AutoFactoryPODTO;
import com.amos.podsupapi.dto.report.POReportDTO;
import com.amos.podsupapi.model.AutoFactoryPO;
import com.amos.podsupapi.repository.AutoFactoryPORepository;

@Service
@Transactional(propagation = Propagation.REQUIRES_NEW)
public class AutoFactoryPOSeviceImpl implements AutoFactoryPOService {

  private static Logger logger = LogManager.getLogger(AutoFactoryPOController.class);

  @Autowired
  private AutoFactoryPORepository autoFactoryPOReository;

  @PersistenceContext
  private EntityManager entityManager;

  // private POManagementDTO pomanage;

  @Override
  public List<AutoFactoryPODTO> getAutoFactoryPO(int pono) {
    List<AutoFactoryPODTO> autoPOList = new ArrayList<>();
    for (AutoFactoryPO source : autoFactoryPOReository.getAutoFactoryPO(pono)) {
      AutoFactoryPODTO target = new AutoFactoryPODTO();
      BeanUtils.copyProperties(source, target);
      autoPOList.add(target);
    }
    autoPOList.get(0);
    return autoPOList;
  }

  @Override
  public List<AutoFactoryPODTO> getAllAutoPO(int pono, int order) {
    List<AutoFactoryPODTO> userList = new ArrayList<>();
    for (AutoFactoryPO source : autoFactoryPOReository.getAllAutoPO(pono, order)) {
      AutoFactoryPODTO target = new AutoFactoryPODTO();
      BeanUtils.copyProperties(source, target);
      userList.add(target);
    }
    return userList;

  }

  @Override
  public List<POManagementDTO> getAutoPODetail(POManageReportSearchDTO searceBean) {

    String dateFrom = searceBean.getDateFrom();
    String dateTo = searceBean.getDateTo();
    String orderNo = searceBean.getOrderNo();
    String phoneNo = searceBean.getPhoneNo();
    String cvCode = searceBean.getCvCode();
    String channel = searceBean.getChannel();
    String status = searceBean.getStatus();

    List<Object[]> dataList = getAutoPODetailList(dateFrom, dateTo, orderNo, phoneNo, cvCode, channel, status);

    List<POManagementDTO> POManageDTOlist = new ArrayList<>();

    POManagementDTO pomanage = null;

    try {
      for (Object[] obj : dataList) {
        pomanage = new POManagementDTO();
        pomanage.setOrderDate((String) obj[0]);
        pomanage.setChannel((String) obj[1]);
        pomanage.setOrderNo((String) obj[2]);
        pomanage.setIorder((String) obj[3]);
        pomanage.setPayMode((String) obj[4]);
        pomanage.setC_payMode((String) obj[5]);
        pomanage.setFirstName((String) obj[6]);
        pomanage.setLastName((String) obj[7]);
        pomanage.setPhoneNo((String) obj[8]);
        pomanage.setCvCode((String) obj[9]);
        pomanage.setCvName((String) obj[10]);
        pomanage.setPoNo(((BigDecimal) obj[11]).intValue());
        pomanage.setProductLine(((BigDecimal) obj[12]).intValue());
        pomanage.setStatusSend((String) obj[13]);

        POManageDTOlist.add(pomanage);
      }

    } catch (Exception e) {

      logger.error(String.format("ERROR getAutoPODetail() : "), e);
    }

    return POManageDTOlist;
  }

  @Override
  public List<POReportDTO> getAutoPODetailReport(POManageReportSearchDTO searceBean) {

    String dateFrom = searceBean.getDateFrom();
    String dateTo = searceBean.getDateTo();
    String orderNo = searceBean.getOrderNo();
    String phoneNo = searceBean.getPhoneNo();
    String cvCode = searceBean.getCvCode();
    String channel = searceBean.getChannel();
    String status = searceBean.getStatus();

    List<Object[]> dataList = getAutoPODetailList(dateFrom, dateTo, orderNo, phoneNo, cvCode, channel, status);

    List<POReportDTO> POManageDTOlist = new ArrayList<>();

    POReportDTO pomanage = null;

    try {
      for (Object[] obj : dataList) {
        pomanage = new POReportDTO();
        pomanage.setOrderDate((String) obj[0]);
        pomanage.setChannel((String) obj[1]);
        pomanage.setOrderNo((String) obj[2]);
        pomanage.setPayMode((String) obj[3]);
        pomanage.setFirstName((String) obj[4]);
        pomanage.setLastName((String) obj[5]);
        pomanage.setPhoneNo((String) obj[6]);
        pomanage.setCvCode((String) obj[7]);
        pomanage.setCvName((String) obj[8]);
        pomanage.setPoNo(((BigDecimal) obj[9]).intValue());
        pomanage.setProductLine(((BigDecimal) obj[10]).intValue());

        POManageDTOlist.add(pomanage);
      }

    } catch (Exception e) {

      logger.error(String.format("ERROR getAutoPODetailReport() : "), e);
    }

    return POManageDTOlist;
  }

  @Override
  public List<POManagentViewDTO> getAutoPODetailView(String orderNo, String poNo) {
    List<Object[]> dataList = getAutoPODetailViewList(orderNo, poNo);

    List<POManagentViewDTO> POManageDTOlist = new ArrayList<>();

    POManagentViewDTO pomanage = null;

    try {
      for (Object[] obj : dataList) {
        pomanage = new POManagentViewDTO();

        pomanage.setOrderDate((String) obj[0]);
        pomanage.setChannel((String) obj[1]);
        pomanage.setOrderNo((String) obj[2]);
        pomanage.setPayMode((String) obj[3]);
        pomanage.setFirstNameCus((String) obj[4]);
        pomanage.setLastNameCus((String) obj[5]);
        pomanage.setPhoneNoCus((String) obj[6]);
        pomanage.setFirstNameShip((String) obj[7]);
        pomanage.setLastNameShip((String) obj[8]);
        pomanage.setPhoneNoShip((String) obj[9]);
        pomanage.setAddress1((String) obj[10]);
        pomanage.setAddress7((String) obj[11]);
        pomanage.setAddress2((String) obj[12]);
        pomanage.setAddress3((String) obj[13]);
        pomanage.setCity((String) obj[14]);
        pomanage.setZipcode((String) obj[15]);
        pomanage.setStatusSend((String) obj[16]);
        pomanage.setStatusSend_keyword((String) obj[17]);
        pomanage.setSendDate((String) obj[18]);
        pomanage.setSendBy((String) obj[19]);
        pomanage.setTracking((String) obj[20]);
        pomanage.setI_order((String) obj[21]);
        pomanage.setPreview((String) obj[22]);

        List<Object[]> dataItemList = getItemAutoPODetailViewList(poNo);
        ArrayList<ItemDetailDTO> itmList = new ArrayList<>();

        for (Object[] objItm : dataItemList) {
          ItemDetailDTO itmDto = new ItemDetailDTO();

          itmDto.setItemNo(((BigDecimal) objItm[0]).intValue());
          itmDto.setQty(((BigDecimal) objItm[1]).intValue());
          itmDto.setItemName((String) objItm[2]);
          itmDto.setTotalPrice(((BigDecimal) objItm[3]).doubleValue());

          itmList.add(itmDto);

        }

        pomanage.setItemList(itmList);

        POManageDTOlist.add(pomanage);
      }

    } catch (Exception e) {

      logger.error(String.format("ERROR getAutoPODetailReport() : "), e);
    }

    return POManageDTOlist;
  }

  private List<Object[]> getItemAutoPODetailViewList(String poNo) {

    String sqlSelect = "SELECT a.I_ITEMNO, a.I_NEWPOQTY, b.S_NOTE50, "
        + " a.I_NEWPOQTY * NVL (DECODE (a.f_newbuyprice, 0, a.f_buyprice, a.f_newbuyprice),a.f_buyprice) AS f_totalprice "
        + " FROM p_posize a, p_item b " + " WHERE a.I_ITEMNO = b.I_ITEMNO ";

    String sqlWhere = " ";
    sqlWhere = (CommonUtils.isNullOrEmpty(poNo)) ? sqlWhere : sqlWhere + " AND i_pono = :poNo ";

    String queryAll = sqlSelect + sqlWhere;
    Query query = entityManager.createNativeQuery(queryAll);
    if (!CommonUtils.isNullOrEmpty(poNo)) {
      query.setParameter("poNo", poNo);
    }

    @SuppressWarnings("unchecked")
    List<Object[]> dataList = query.getResultList();
    return dataList;
  }

  private List<Object[]> getAutoPODetailList(String dateFrom, String dateTo, String orderNo, String phoneNo,
      String cvCode, String channel, String status) {

    String sqlSelect = " SELECT TO_CHAR (po.D_CVS_ORDER, 'dd-mm-yyyy') AS D_CVS_ORDER," +
        "       K.S_SHORTNAME AS channel," +
        "       po.S_EXTERN_ORDERNO," +
        "       TO_CHAR (po.I_ORDER) AS I_ORDER," +
        "       paymode.s_shortname AS paymode," +
        "       ord.C_PAYMODE," +
        "       cus.S_FIRSTNAME," +
        "       cus.S_NAME," +
        "       cus.S_PHONENO1," +
        "       CV.S_CVCODE," +
        "       CV.S_CVNAME," +
        "       po.I_PONO," +
        "       itm.I_PRODLINE1," +
        "       ks.S_VALUE AS Staus_SEND" +
        "  FROM P_AUTO_FACTORY_POLINK po," +
        "       P_ORDSUM ord," +
        "       p_customer cus," +
        "       p_cv CV," +
        "       p_posize pz," +
        "       p_item itm," +
        "       p_posum posum," +
        "       s_key k," +
        "       WEBAPP.W05_P_FACTORY_POD_STATUS fpod," +
        "       (SELECT *" +
        "          FROM s_key_gosoft_det" +
        "         WHERE S_KEYWORD = 'POD_SENDBYSUP_STATUS') ks," +
        "       (SELECT *" +
        "          FROM s_key" +
        "         WHERE s_topic = 'cpaymode') paymode" +
        " WHERE     po.i_order = ord.i_order" +
        "       AND po.I_ACCOUNT = ord.I_ACCOUNT" +
        "       AND po.I_ACCOUNT = cus.I_ACCOUNT" +
        "       AND po.I_PONO = fpod.I_PONO(+)" +
        "       AND cus.I_ZIPCODE = CV.I_ZIPCODE(+)" +
        "       AND cus.S_CITY = CV.S_CITY(+)" +
        "       AND po.I_PONO = pz.I_PONO" +
        "       AND pz.I_ITEMNO = itm.I_ITEMNO" +
        "       AND po.I_PONO = posum.I_PONO" +
        "       AND ord.I_ORDERSOURCE = k.I_VALUE" +
        "       AND fpod.C_STATUS = ks.S_SUB_KEYWORD(+)" +
        "       AND K.S_TOPIC = 'iordersource'" +
        "       AND posum.c_status NOT IN ('C', 'J')" +
        "       AND ord.C_PAYMODE = paymode.c_value";

    String sqlWhere = "";
    sqlWhere = (CommonUtils.isNullOrEmpty(dateFrom)) ? sqlWhere
        : sqlWhere + " AND posum.D_ORDER BETWEEN to_date(:dateFrom,'dd-mm-yyyy') ";
    sqlWhere = (CommonUtils.isNullOrEmpty(dateTo)) ? sqlWhere : sqlWhere + " and to_date(:dateTo,'dd-mm-yyyy') ";
    sqlWhere = (CommonUtils.isNullOrEmpty(orderNo)) ? sqlWhere : sqlWhere + " and po.S_EXTERN_ORDERNO = :orderNo ";
    sqlWhere = (CommonUtils.isNullOrEmpty(phoneNo)) ? sqlWhere : sqlWhere + " and cus.S_PHONENO1 = :phoneNo ";
    sqlWhere = (CommonUtils.isNullOrEmpty(cvCode)) ? sqlWhere : sqlWhere + " and CV.S_CVCODE = :cvCode  ";
    sqlWhere = (CommonUtils.isNullOrEmpty(channel)) ? sqlWhere : sqlWhere + " and ( ";
    // String[] channelString = channel.split(",");
    // String[] statusList = status.split(",");

    String stringSplitWord = ",";
    String[] channelList = channel.split(stringSplitWord);

    if (channelList.length > 0) {
      int countRound = 1;
      for (int indexChannel = 1; indexChannel <= channelList.length; indexChannel++) {
        sqlWhere = (CommonUtils.isNullOrEmpty(channel)) ? sqlWhere : sqlWhere + " K.s_shortname =:channel" + indexChannel;
        if (countRound <= channelList.length - 1) {
          sqlWhere = (CommonUtils.isNullOrEmpty(channel)) ? sqlWhere : sqlWhere + " OR";
          countRound++;
        } else {
          sqlWhere = (CommonUtils.isNullOrEmpty(channel)) ? sqlWhere : sqlWhere + ")";
        }
      }
    }

    String[] statusList = status.split(stringSplitWord);
    sqlWhere = (CommonUtils.isNullOrEmpty(status)) ? sqlWhere : sqlWhere + " and ( ";

    if (statusList.length > 0) {
      int countRound = 1;
      for (int indexStatus = 1; indexStatus <= statusList.length; indexStatus++) {
        sqlWhere = (CommonUtils.isNullOrEmpty(status)) ? sqlWhere : sqlWhere + " KS.S_SUB_KEYWORD =:status" + indexStatus;
        if (countRound <= statusList.length - 1) {
          sqlWhere = (CommonUtils.isNullOrEmpty(status)) ? sqlWhere : sqlWhere + " OR ";
          countRound++;
        } else {
          sqlWhere = (CommonUtils.isNullOrEmpty(status)) ? sqlWhere : sqlWhere + " ) ";
        }
      }
    }

    // sqlWhere = (CommonUtils.isNullOrEmpty(channel))? sqlWhere:sqlWhere+" and ";
    // sqlWhere = (CommonUtils.isNullOrEmpty(status))? sqlWhere:sqlWhere+" and ";

    String sqlGroupBy = " GROUP BY po.D_CVS_ORDER," +
        "         K.S_SHORTNAME," +
        "         K.S_KEYWORD," +
        "         po.S_EXTERN_ORDERNO," +
        "         ord.C_PAYMODE," +
        "         cus.S_FIRSTNAME," +
        "         cus.S_NAME," +
        "         cus.S_PHONENO1," +
        "         CV.S_CVCODE," +
        "         CV.S_CVNAME," +
        "         po.I_PONO," +
        "         itm.I_PRODLINE1," +
        "         ks.S_VALUE," +
        "         po.I_ORDER," +
        "         paymode.s_shortname";

    String queryAll = sqlSelect + sqlWhere + sqlGroupBy;
    System.out.print(queryAll);

    Query query = entityManager.createNativeQuery(queryAll);
    if (!CommonUtils.isNullOrEmpty(dateFrom)) {
      query.setParameter("dateFrom", dateFrom);
    }
    if (!CommonUtils.isNullOrEmpty(dateTo)) {
      query.setParameter("dateTo", dateTo);
    }
    if (!CommonUtils.isNullOrEmpty(orderNo)) {
      query.setParameter("orderNo", orderNo);
    }
    if (!CommonUtils.isNullOrEmpty(phoneNo)) {
      query.setParameter("phoneNo", phoneNo);
    }
    if (!CommonUtils.isNullOrEmpty(cvCode)) {
      query.setParameter("cvCode", cvCode);
    }
    if (!CommonUtils.isNullOrEmpty(channel)) {
      for (int indexChannel = 1; indexChannel <= channelList.length; indexChannel++) {
        query.setParameter("channel" + indexChannel, channelList[indexChannel - 1]);
      }
      // query.setParameter("channel", channel);
    }
    if (!CommonUtils.isNullOrEmpty(status)) {
      for (int indexStatus = 1; indexStatus <= statusList.length; indexStatus++) {
        query.setParameter("status" + indexStatus, statusList[indexStatus - 1]);
      }
      // query.setParameter("status", status);
    }

    @SuppressWarnings("unchecked")
    List<Object[]> dataList = query.getResultList();
    return dataList;
  }

  private List<Object[]> getAutoPODetailViewList(String orderNo, String poNo) {

    String sqlSelect = " SELECT TO_CHAR (po.D_CVS_ORDER, 'dd-mm-yyyy') AS D_CVS_ORDER, " +
        "                 K.S_SHORTNAME                          AS channel, " +
        "                 po.S_EXTERN_ORDERNO, " +
        "                 ord.C_PAYMODE, " +
        "                 cus.S_FIRSTNAME, " +
        "                 cus.S_NAME, " +
        "                 cus.S_PHONENO1, " +
        "                 sh.S_FIRSTNAME AS FIRSTNAME_SHIP, " +
        "                 sh.S_NAME AS LASTNAME_SHIP, " +
        "                 sh.S_PHONENO1 AS PHONE1_SHIP, " +
        "                 sh.S_ADDRESS1 ,  " +
        "                 sh.S_ADDRESS7 , " +
        "                 sh.S_ADDRESS2 , " +
        "                 sh.S_ADDRESS3 , " +
        "                 sh.S_CITY, " +
        "                 to_char (sh.I_ZIPCODE) AS ZIPCODE, " +
        "                 ks.S_VALUE AS Staus_SEND, " +
        "                 ks.S_SUB_KEYWORD as status_keyword, " +
        "                 to_char(fpod.D_DELIVERY,'dd-mm-yyyy') AS D_DELIVERY , " +
        "                 kd.S_VALUE AS Send_by , " +
        "                 fpod.S_DELIVERY_TRACKING," +
        "                 TO_CHAR (po.I_ORDER) as i_order, " +
        "                 po.S_PREVIEW " +
        "            FROM P_AUTO_FACTORY_POLINK po, " +
        "                 P_ORDSUM            ord, " +
        "                 p_customer          cus, " +
        "                 p_cv                CV, " +
        "                 p_posize            pz, " +
        "                 p_item              itm, " +
        "                 p_posum             posum, " +
        "                 s_key               k, " +
        "                 p_shipto           sh, " +
        "                 WEBAPP.W05_P_FACTORY_POD_STATUS fpod, " +
        "                (select * from  s_key_gosoft_det where S_KEYWORD = 'POD_SENDBYSUP_STATUS')  ks, " +
        "                (select * from  s_key_gosoft_det where S_KEYWORD = 'POD_SENDBYSUP_DELIVERY_BY')  kd " +
        "           WHERE     po.i_order = ord.i_order " +
        "                 AND po.I_ACCOUNT = ord.I_ACCOUNT " +
        "                 AND po.I_ACCOUNT = cus.I_ACCOUNT " +
        "                 AND po.I_ACCOUNT = sh.I_ACCOUNT " +
        "                 AND po.I_ORDER = sh.I_ORDER " +
        "                 AND po.I_PONO = fpod.I_PONO(+)" +
        "                 AND cus.I_ZIPCODE = CV.I_ZIPCODE(+) " +
        "                 AND cus.S_CITY = CV.S_CITY(+) " +
        "                 AND po.I_PONO = pz.I_PONO " +
        "                 AND pz.I_ITEMNO = itm.I_ITEMNO " +
        "                 AND po.I_PONO = posum.I_PONO " +
        "                 AND ord.I_ORDERSOURCE = k.I_VALUE " +
        "                 AND fpod.I_DELIVERY_BY = kd.S_SUB_KEYWORD(+)" +
        "                 AND fpod.C_STATUS = ks.S_SUB_KEYWORD(+)" +
        "                 AND K.S_SUBTOPIC = 'iordersource' ";

    String sqlWhere = "";
    sqlWhere = (CommonUtils.isNullOrEmpty(orderNo)) ? sqlWhere : sqlWhere + "  AND po.S_EXTERN_ORDERNO = :orderNo ";
    sqlWhere = (CommonUtils.isNullOrEmpty(poNo)) ? sqlWhere : sqlWhere + " AND po.I_PONO = :poNo ";

    String sqlGroupBy = "GROUP BY po.D_CVS_ORDER, " +
        "         K.S_SHORTNAME," +
        "         po.S_EXTERN_ORDERNO," +
        "         po.I_ORDER, " +
        "         ord.C_PAYMODE," +
        "         cus.S_FIRSTNAME," +
        "         cus.S_NAME," +
        "         cus.S_PHONENO1," +
        "         sh.S_FIRSTNAME," +
        "         sh.S_NAME," +
        "         sh.S_PHONENO1," +
        "         sh.S_ADDRESS1 ," +
        "         sh.S_ADDRESS7 ," +
        "         sh.S_ADDRESS2 ," +
        "         sh.S_ADDRESS3 ," +
        "         sh.S_CITY," +
        "         sh.I_ZIPCODE," +
        "         ks.S_VALUE ," +
        "         fpod.D_DELIVERY," +
        "         kd.S_VALUE  , " +
        "         fpod.S_DELIVERY_TRACKING," +
        "         po.S_PREVIEW," +
        "         ks.S_SUB_KEYWORD";

    String queryAll = sqlSelect + sqlWhere + sqlGroupBy;
    Query query = entityManager.createNativeQuery(queryAll);
    if (!CommonUtils.isNullOrEmpty(orderNo)) {
      query.setParameter("orderNo", orderNo);
    }
    if (!CommonUtils.isNullOrEmpty(poNo)) {
      query.setParameter("poNo", poNo);
    }

    @SuppressWarnings("unchecked")
    List<Object[]> dataList = query.getResultList();

    return dataList;
  }

}
