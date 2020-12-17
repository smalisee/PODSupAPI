package com.amos.podsupapi.services;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import com.amos.podsupapi.common.CommonUtils;
import com.amos.podsupapi.common.ReturnCode;
import com.amos.podsupapi.component.aws.AWSFileTransferComponent;
import com.amos.podsupapi.config.AppConfig;
import com.amos.podsupapi.dto.POManageReportSearchDTO;
import com.amos.podsupapi.dto.SupplierLineItemDTO;
import com.amos.podsupapi.dto.SupplierPODTO;
import com.amos.podsupapi.dto.SupplierPODetailDTO;
import com.amos.podsupapi.dto.report.SupplierPOReportDTO;
import com.amos.podsupapi.model.FactoryPODStatus;
import com.amos.podsupapi.model.File;
import com.amos.podsupapi.model.PODStatusHistory;
import com.amos.podsupapi.repository.SupplierRepository;

@Service
@Transactional(propagation = Propagation.REQUIRES_NEW)
public class SupplierServiceImpl implements SupplierService {

  @Autowired
  private SupplierRepository supplierRepository;

  @Autowired
  private AWSFileTransferComponent awsFileTransferComponent;

  @Override
  public List<SupplierPODTO> getSupplierPOListData(String dateFrom, String dateTo, String poNo, String vendor) {

    String sqlSelect = "SELECT TO_CHAR (podstatus.i_serial) AS i_serial," +
        "         TO_CHAR (polink.S_EXTERN_ORDERNO) AS order_no," +
        "         TO_CHAR (cus.S_FIRSTNAME) AS firstname," +
        "         TO_CHAR (cus.s_name) AS lastname," +
        "         TO_CHAR (polink.i_pono) AS pono," +
        "         TO_CHAR (keygodet_status.s_value) AS delivery_status," +
        "         TO_CHAR (podstatus.S_REMARK) AS remark," +
        "         TO_CHAR (podstatus.d_delivery) AS delivery_date," +
        "         TO_CHAR (keygodet_delivery_by.s_value) AS delivery_by," +
        "         TO_CHAR (podstatus.S_DELIVERY_TRACKING) AS tracking_no" +
        "    FROM (SELECT *" +
        "            FROM S_KEY_GOSOFT_DET" +
        "           WHERE S_KEYWORD = 'POD_SENDBYSUP_DELIVERY_BY') keygodet_delivery_by," +
        "         (SELECT *" +
        "            FROM S_KEY_GOSOFT_DET" +
        "           WHERE S_KEYWORD = 'POD_SENDBYSUP_STATUS') keygodet_status," +
        "         P_ORDSUM ordsum," +
        "         P_AUTO_FACTORY_POLINK polink," +
        "         WEBAPP.W05_P_FACTORY_POD_STATUS podstatus," +
        "         P_CUSTOMER cus," +
        "         P_POSUM posum," +
        "         P_POSIZE pz," +
        "         P_ITEM itm" +
        "   WHERE     polink.i_order = ordsum.i_order" +
        "         AND polink.i_pono = podstatus.i_pono(+)" +
        "         AND polink.S_EXTERN_ORDERNO = ordsum.S_EXTERN_ORDERNO(+)" +
        "         AND cus.i_account = ordsum.i_account" +
        "         AND podstatus.i_delivery_by = keygodet_delivery_by.s_sub_keyword(+)" +
        "         AND podstatus.C_STATUS = keygodet_status.s_sub_keyword(+)" +
        "         AND polink.d_create = posum.d_order" +
        "         AND polink.C_STATUS NOT IN ('G', 'D')" +
        "         AND posum.c_status NOT IN ('C', 'J')" +
        "         AND polink.i_pono = posum.i_pono" +
        "         AND polink.I_PONO = pz.I_PONO" +
        "         AND pz.I_ITEMNO = itm.I_ITEMNO" +
        "         AND polink.i_vendor = :vendor";

    String sqlCondition = " ";
    sqlCondition = (CommonUtils.isNullOrEmpty(poNo)) ? sqlCondition : sqlCondition + " AND polink.i_pono = :poNo ";
    sqlCondition = (CommonUtils.isNullOrEmpty(dateFrom)) ? sqlCondition
        : sqlCondition + " AND polink.d_create BETWEEN TO_DATE (:dateFrom, 'dd-mm-yyyy') ";
    sqlCondition = (CommonUtils.isNullOrEmpty(dateTo)) ? sqlCondition
        : sqlCondition + " AND TO_DATE (:dateTo, 'dd-mm-yyyy') ";

    String sqlOrderBy = " Group by podstatus.i_serial," +
        "                 polink.S_EXTERN_ORDERNO," +
        "                 cus.S_FIRSTNAME," +
        "                 cus.s_name," +
        "                 polink.i_pono," +
        "                 keygodet_status.s_value," +
        "                 podstatus.S_REMARK," +
        "                 podstatus.d_delivery," +
        "                 keygodet_delivery_by.s_value," +
        "                 podstatus.S_DELIVERY_TRACKING" +
        "                 ORDER BY order_no ASC" +
        "                  ";

    String queryAll = sqlSelect + sqlCondition + sqlOrderBy;

    List<Object[]> supplierDataList = supplierRepository.getSupplierPODetailWithPONO(dateFrom, dateTo, poNo, vendor, queryAll);

    List<SupplierPODTO> supplierDetailList = new ArrayList<>();

    for (Object[] resultSupplierDataList : supplierDataList) {
      SupplierPODTO supplierData = new SupplierPODTO();

      supplierData.setIserial((String) resultSupplierDataList[0]);
      supplierData.setOrderNo((String) resultSupplierDataList[1]);
      supplierData.setFirstname((String) resultSupplierDataList[2]);
      supplierData.setLastname((String) resultSupplierDataList[3]);
      supplierData.setPoNo((String) resultSupplierDataList[4]);
      supplierData.setDelivery_status((String) resultSupplierDataList[5]);
      supplierData.setRemark((String) resultSupplierDataList[6]);
      supplierData.setDelivery_date((String) resultSupplierDataList[7]);
      supplierData.setDelivery_by((String) resultSupplierDataList[8]);
      supplierData.setTrackingNo((String) resultSupplierDataList[9]);

      supplierDetailList.add(supplierData);
    }

    return supplierDetailList;
  }

  @Override
  public List<SupplierPODetailDTO> getSupplierDetail(String orderNo, String poNo, String vendor) {
    // TODO Auto-generated method stub

    String sqlSelect = "SELECT TO_CHAR (podstatus.i_serial) AS serial," +
        "       TO_CHAR (polink.i_order) AS orderno," +
        "       TO_CHAR (cus.s_title || cus.s_firstname) AS firstname," +
        "       TO_CHAR (cus.s_name) AS lastname," +
        "       TO_CHAR (item.i_itemno) AS itemno," +
        "       TO_CHAR (item.s_note50) AS itemname," +
        "       TO_CHAR (orddet.I_ORDERQTY) AS qty," +
        "       TO_CHAR (polink.i_pono) pono," +
        "       TO_CHAR (keygodet_delivery_by.s_sub_keyword) AS delivery_key," +
        "       TO_CHAR (keygodet_delivery_by.s_value) AS delivery_word," +
        "       TO_CHAR (keygodet_status.s_sub_keyword) AS status_key," +
        "       TO_CHAR (keygodet_status.s_value) AS status_word," +
        "       TO_CHAR (podstatus.d_delivery) AS delivery_date," +
        "       TO_CHAR (podstatus.s_delivery_tracking) AS trackingno," +
        "       TO_CHAR (podstatus.S_REMARK) as remark" +
        "  FROM P_AUTO_FACTORY_POLINK polink," +
        "       WEBAPP.W05_P_FACTORY_POD_STATUS podstatus," +
        "       P_ORDDET orddet," +
        "       P_CUSTOMER cus," +
        "       P_ITEM item," +
        "       P_ORDSUM ordsum," +
        "       (SELECT *" +
        "          FROM S_KEY_GOSOFT_DET" +
        "         WHERE S_KEYWORD = 'POD_SENDBYSUP_DELIVERY_BY') keygodet_delivery_by," +
        "       (SELECT *" +
        "          FROM S_KEY_GOSOFT_DET" +
        "         WHERE S_KEYWORD = 'POD_SENDBYSUP_STATUS') keygodet_status" +
        " WHERE     polink.i_order = podstatus.i_order(+)" +
        "       AND polink.i_pono = podstatus.i_pono(+)" +
        "       AND polink.i_order = orddet.i_order" +
        "       AND polink.i_vendor = item.i_vendor" +
        "       AND ordsum.i_account = cus.i_account" +
        "       AND ordsum.i_order = orddet.i_order" +
        "       AND orddet.i_itemno = item.i_itemno" +
        "       AND podstatus.i_delivery_by = keygodet_delivery_by.s_sub_keyword(+)" +
        "       AND podstatus.c_status = keygodet_status.s_sub_keyword(+)" +
        "       AND polink.S_EXTERN_ORDERNO = :orderNo" +
        "       AND polink.i_pono = :poNo" +
        "       AND item.i_vendor = :vendor";

    List<Object[]> poDetailObj = supplierRepository.getPODetail(orderNo, poNo, vendor, sqlSelect);

    List<SupplierPODetailDTO> poDetailList = new ArrayList<>();
    List<SupplierLineItemDTO> itemList = new ArrayList<>();

    int count = 1;

    for (Object[] resultPODet : poDetailObj) {
      SupplierPODetailDTO poDetail = new SupplierPODetailDTO();

      if (count == 1) {
        poDetail.setIserial((String) resultPODet[0]);
        poDetail.setOrderNo((String) resultPODet[1]);
        poDetail.setFirstname((String) resultPODet[2]);
        poDetail.setLastname((String) resultPODet[3]);
        poDetail.setPoNo((String) resultPODet[7]);
        poDetail.setDelivery_status_keyword((String) resultPODet[8]);
        poDetail.setDelivery_status_value((String) resultPODet[9]);
        poDetail.setDelivery_by_keyword((String) resultPODet[10]);
        poDetail.setDelivery_by_value((String) resultPODet[11]);
        poDetail.setDelivery_date((String) resultPODet[12]);
        poDetail.setTracking_no((String) resultPODet[13]);
        poDetail.setRemark((String) resultPODet[14]);

        poDetailList.add(poDetail);
        count++;
      }
    }

    for (SupplierPODetailDTO resultCurrentPO : poDetailList) {
      for (Object[] resutGetLineItem : poDetailObj) {
        SupplierLineItemDTO lineItem = new SupplierLineItemDTO();

        lineItem.setItemNo((String) resutGetLineItem[4]);
        lineItem.setItemName((String) resutGetLineItem[5]);
        lineItem.setItemQty((String) resutGetLineItem[6]);
        itemList.add(lineItem);

        resultCurrentPO.setItemList(itemList);
      }
    }

    return poDetailList;

  }

  @Override
  public void updateSupplierPODetail(String iuser, String iserial, String orderNo, String poNo, String vendor,
      String delivery_status, String delivery_date, String delivery_by, String trackingNo, String delivery_other,
      MultipartFile[] files) throws ParseException {
    // TODO Auto-generated method stub
    // insert history
    if (!CommonUtils.isNullOrEmpty(iuser) && !CommonUtils.isNullOrEmpty(orderNo) && !CommonUtils.isNullOrEmpty(poNo)
        && !CommonUtils.isNullOrEmpty(delivery_status) && !CommonUtils.isNullOrEmpty(delivery_date)
        && !CommonUtils.isNullOrEmpty(delivery_by)) {

      try {
        PODStatusHistory podHis = new PODStatusHistory();
        podHis.setPono(Integer.valueOf(poNo));

        Date dateDelivery = new SimpleDateFormat("dd/MM/yyyy").parse(delivery_date);
        podHis.setD_delivery(dateDelivery);

        podHis.setDelivery_by(Integer.valueOf(delivery_by));
        podHis.setUserId(Integer.valueOf(iuser));
        podHis.setIorder(Integer.valueOf(orderNo));
        podHis.setStatus(delivery_status);
        podHis.setD_create(LocalDateTime.now());

        supplierRepository.addHistory(podHis);

        if (supplierRepository.checkExitsPODStatus(orderNo, poNo)) {
          FactoryPODStatus getPODDetByISerial = supplierRepository.fidPODStatusByID(Integer.valueOf(iserial));
          supplierRepository.updateFactoryPODStatusDetail(getPODDetByISerial, delivery_status, delivery_date, delivery_by,
              trackingNo, delivery_other);
        } else {
          supplierRepository.insertPODStatus(iuser, orderNo, poNo, vendor, delivery_status, delivery_date, delivery_by,
              delivery_other, trackingNo);
        }
      } catch (Exception e) {
        System.out.println("error");
      }

    }
    // try {
    // addFilePODetail(files);
    // } catch (Exception e) {
    // // TODO Auto-generated catch block
    // e.printStackTrace();
    // }
  }

  @Override
  public ReturnCode addFilePODetail(MultipartFile[] files) throws Exception {
    int i = 1;
    String prefix = LocalDate.now().format(DateTimeFormatter.ofPattern("yyyy-MM-dd"));

    String fileParent = String.format("%s/%s", AppConfig.getApiDeliveryBasePathImages(), prefix);

    for (MultipartFile file : files) {
      String filepath = String.format("%s/%s.%s.%s", fileParent, "test" + i, "test" + i, i);
      saveResourceToStorage(file.getBytes(), filepath);

      File fileDB = new File();
      fileDB.setI_file(i);
      fileDB.setS_file_name("testfile" + i);
      fileDB.setS_url(filepath);
      fileDB.setD_create(LocalDateTime.now());

      supplierRepository.addFileDB(fileDB);
      i++;
    }

    return ReturnCode.SUCCESS;
  }

  private void saveResourceToStorage(byte[] bytes, String filepath) {
    // TODO Auto-generated method stub
    try {
      awsFileTransferComponent.putObject(filepath, bytes);
    } catch (Exception e) {
      // logger.error("can not savefile to AWS");
      System.out.println("error จ้า เพราะไรไม่รู้" + e);
    }

  }

  @Override
  public List<SupplierPOReportDTO> getSupplierPODetailReport(POManageReportSearchDTO searchBean) {
    // TODO Auto-generated method stub

    String dateFrom = searchBean.getDateFrom();
    String dateTo = searchBean.getDateTo();
    String poNo = searchBean.getPoNo();
    String vendor = searchBean.getVendor();

    List<SupplierPODTO> dataList = getSupplierPOListData(dateFrom, dateTo, poNo, vendor);

    List<SupplierPOReportDTO> supPOReportList = new ArrayList<>();

    for (SupplierPODTO result : dataList) {
      SupplierPOReportDTO supPOReport = new SupplierPOReportDTO();
      supPOReport.setOrderNo(result.getOrderNo());
      supPOReport.setFirstName(result.getFirstname());
      supPOReport.setLastName(result.getLastname());
      supPOReport.setPono(result.getPoNo());
      supPOReport.setDelivery_status(result.getDelivery_status());
      supPOReport.setDelivery_date(result.getDelivery_date());
      supPOReport.setDelivery_by(result.getDelivery_by());
      supPOReport.setTrackingNo(result.getTrackingNo());

      supPOReportList.add(supPOReport);
    }
    return supPOReportList;
  }

}
