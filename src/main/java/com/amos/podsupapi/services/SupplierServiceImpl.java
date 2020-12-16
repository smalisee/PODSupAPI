package com.amos.podsupapi.services;

import java.io.IOException;
import java.math.BigDecimal;
import java.sql.SQLException;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

import java.io.File;

import org.apache.logging.log4j.Logger;
import org.apache.commons.io.FileUtils;
import org.apache.logging.log4j.LogManager;
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
import com.amos.podsupapi.exception.PoBusinessException;
import com.amos.podsupapi.model.FactoryPODStatus;
import com.amos.podsupapi.model.PODFile;
import com.amos.podsupapi.repository.SupplierRepository;

@Service
@Transactional(propagation = Propagation.REQUIRES_NEW)
public class SupplierServiceImpl implements SupplierService {

  @Autowired
  private SupplierRepository supplierRepository;

  @Autowired
  private AWSFileTransferComponent awsFileTransferComponent;
  
  private static final Logger logger = LogManager.getLogger(SupplierServiceImpl.class);

  @Override
  public List<SupplierPODTO> getSupplierPOListData(String dateFrom, String dateTo, String poNo, String vendor) {

    String sqlSelect = "SELECT TO_CHAR(podstatus.i_serial) as i_serial," +
        "         TO_CHAR(polink.i_order) AS order_no," +
        "         TO_CHAR(cus.S_FIRSTNAME) AS firstname," +
        "         TO_CHAR(cus.s_name) AS lastname," +
        "         TO_CHAR(polink.i_pono) AS pono," +
        "         TO_CHAR(keygodet_status.s_value) AS delivery_status," +
        "         TO_CHAR(podstatus.d_delivery) as delivery_date," +
        "         TO_CHAR(keygodet_delivery_by.s_value) AS delivery_by," +
        "         TO_CHAR(podstatus.S_DELIVERY_TRACKING) AS tracking_no" +
        "    FROM (SELECT DISTINCT det.i_order,item.i_vendor" +
        "            FROM P_ITEM item, p_orddet det WHERE item.i_itemno = det.i_itemno) orderno," +
        "         (SELECT *" +
        "            FROM S_KEY_GOSOFT_DET" +
        "           WHERE S_KEYWORD = 'POD_SENDBYSUP_DELIVERY_BY') keygodet_delivery_by," +
        "         (SELECT *" +
        "            FROM S_KEY_GOSOFT_DET" +
        "           WHERE S_KEYWORD = 'POD_SENDBYSUP_STATUS') keygodet_status," +
        "         P_ORDSUM ordsum," +
        "         P_AUTO_FACTORY_POLINK polink," +
        "         WEBAPP.W05_P_FACTORY_POD_STATUS podstatus," +
        "         P_CUSTOMER cus" +
        "   WHERE     polink.i_order = orderno.i_order" +
        "         AND polink.i_pono = podstatus.i_pono(+)" +
        "         AND polink.i_order = ordsum.i_order(+)" +
        "         AND polink.i_vendor = orderno.i_vendor" +
        "         AND cus.i_account = ordsum.i_account" +
        "         AND podstatus.i_delivery_by = keygodet_delivery_by.s_sub_keyword(+)" +
        "         AND podstatus.C_STATUS = keygodet_status.s_sub_keyword(+)" +
        "         AND orderno.i_vendor = :vendor";

    String sqlCondition = " ";
    sqlCondition = (CommonUtils.isNullOrEmpty(poNo)) ? sqlCondition : sqlCondition + " AND polink.i_pono = :poNo ";
    sqlCondition = (CommonUtils.isNullOrEmpty(dateFrom)) ? sqlCondition
        : sqlCondition + " AND ordsum.d_create BETWEEN TO_DATE (:dateFrom, 'dd-mm-yyyy') ";
    sqlCondition = (CommonUtils.isNullOrEmpty(dateTo)) ? sqlCondition
        : sqlCondition + " AND TO_DATE (:dateTo, 'dd-mm-yyyy') ";

    String sqlOrderBy = " ORDER BY order_no ASC ";

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
      supplierData.setDelivery_date((String) resultSupplierDataList[6]);
      supplierData.setDelivery_by((String) resultSupplierDataList[7]);
      supplierData.setTrackingNo((String) resultSupplierDataList[8]);

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
        "       TO_CHAR (podstatus.s_delivery_tracking) AS trackingno" +
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
        "       AND polink.i_order = :orderNo" +
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
	@Transactional(rollbackFor = { RuntimeException.class, IOException.class, SQLException.class }, noRollbackFor = {
			PoBusinessException.class })
  public ReturnCode updateSupplierPODetail(String iuser, String iserial, String orderNo, String poNo, String vendor,
      String delivery_status, String delivery_date, String delivery_by, String trackingNo, String delivery_other,
      String remark ,MultipartFile[] files) throws Exception {

	  Integer iSerial = null;
		if (supplierRepository.checkExitsPODStatus(orderNo, poNo)) {
			FactoryPODStatus getPODDetByISerial = supplierRepository.fidPODStatusByID(Integer.valueOf(iserial));
			supplierRepository.updateFactoryPODStatusDetail(getPODDetByISerial, delivery_status, delivery_date,
					delivery_by, trackingNo, delivery_other);
		} else {

			iSerial = supplierRepository.insertPODStatus(iuser, orderNo, poNo, vendor, delivery_status, delivery_date,
					delivery_by, delivery_other, trackingNo,remark);
			persistDeliveryFile(files, poNo, iSerial);
			 
		}
    
   
    
    return ReturnCode.SUCCESS;
  }
  
//  @Override
//	@Transactional(rollbackFor = { RuntimeException.class, IOException.class, SQLException.class }, noRollbackFor = {
//			PoBusinessException.class })
//  private void persistDeliveryFile(MultipartFile[] files, String poNo , String orderNo ) throws Exception {
//		int i = 1;
//		String prefix = LocalDate.now().format(DateTimeFormatter.ofPattern("yyyy-MM-dd"));
//
//		String fileParent = String.format("%s/%s", AppConfig.getApiDeliveryBasePathFiles(), prefix);
//
//		for (MultipartFile file : files) {
//			String filepath = String.format("%s/%s.%s.%s", fileParent, poNo, orderNo, i);
//			saveResourceToStorage(file.getBytes(), fileParent, filepath);
//
//			PODFile image = new PODFile();
//			image.setId(1);
//			image.setS_file_name("testfile" + i);
//			image.setS_url(filepath);
//			image.setOrderNo(orderNo);
//			image.setPoNo(poNo);
//		    image.setD_create(LocalDateTime.now());
//			image.setI_file(i);
//			supplierRepository.addFileDB(image);
//			i++;
//		}
//	}
  
	private void saveResourceToStorage(byte[] data, String fileParentPath, String filePath) throws IOException {
		try {
			awsFileTransferComponent.putObject(filePath, data);
		} catch (Exception e) {
			logger.error("can not savefile to AWS");
			try {
				File f = new File(fileParentPath);
				f.mkdirs();
				FileUtils.writeByteArrayToFile(new File(filePath), data);
			} catch (Exception e1) {
				logger.error("can not savefile to local");
				throw (new RuntimeException("Can not save image file"));
			}
		}
	}

  @Override
  public ReturnCode addFilePODetail(MultipartFile[] files) throws Exception {
    int i = 1;
    String prefix = LocalDate.now().format(DateTimeFormatter.ofPattern("yyyy-MM-dd"));

    String fileParent = String.format("%s/%s", AppConfig.getApiDeliveryBasePathFiles(), prefix);

    for (MultipartFile file : files) {
      String filepath = String.format("%s/%s.%s.%s", fileParent, "test" + i, "test" + i, i);
      saveResourceToStorage(file.getBytes(), filepath);

      PODFile fileDB = new PODFile();
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


	public void persistDeliveryFile(MultipartFile[] files, String poNo, Integer iSerial) throws Exception {
		int i = 1;
		String prefix = LocalDate.now().format(DateTimeFormatter.ofPattern("yyyy-MM-dd"));
		String fileParent = String.format("%s/%s", AppConfig.getApiDeliveryBasePathFiles(), prefix);
//		Integer iSerial = supplierRepository.getIserialStatus(poNo, orderNo);

		for (MultipartFile file : files) {
			String fileOrigin = file.getOriginalFilename();
			String filepath = String.format("%s/PO%s.%s.%s", fileParent, poNo, i, fileOrigin);
			saveResourceToStorage(file.getBytes(), fileParent, filepath);

			PODFile image = new PODFile();
			image.setId(iSerial);
			image.setS_file_name(fileOrigin);
			image.setS_url(filepath);
			image.setPoNo(Integer.valueOf(poNo));
			image.setD_create(LocalDateTime.now());
			image.setI_file(i);
			supplierRepository.addFileDB(image);
			i++;
		}

	}

}
