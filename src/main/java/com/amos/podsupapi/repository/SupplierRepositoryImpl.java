package com.amos.podsupapi.repository;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDateTime;
import java.util.Date;
import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import javax.transaction.Transactional;

import org.springframework.stereotype.Repository;

import com.amos.podsupapi.common.CommonUtils;
import com.amos.podsupapi.common.ReturnCode;
import com.amos.podsupapi.model.FactoryPODStatus;
import com.amos.podsupapi.model.File;
import com.amos.podsupapi.model.PODStatusHistory;

@Transactional
@Repository
public class SupplierRepositoryImpl implements SupplierRepository {

  @PersistenceContext
  private EntityManager entityManager;

  @Override
  public List<Object[]> getSupplierPODetailWithPONO(String dateFrom, String dateTo, String poNo, String vendor, String queryAll) {
    // TODO Auto-generated method stub
    Query query = entityManager.createNativeQuery(queryAll);
    if (!CommonUtils.isNullOrEmpty(dateFrom)) {
      query.setParameter("dateFrom", dateFrom);
    }
    if (!CommonUtils.isNullOrEmpty(dateTo)) {
      query.setParameter("dateTo", dateTo);
    }
    if (!CommonUtils.isNullOrEmpty(poNo)) {
      query.setParameter("poNo", poNo);
    }
    if (!CommonUtils.isNullOrEmpty(vendor)) {
      query.setParameter("vendor", vendor);
    }

    List<Object[]> supplierDetailDataList = query.getResultList();
    return supplierDetailDataList;

  }

  @Override
  public List<Object[]> getPODetail(String orderNo, String poNo, String vendor, String sqlSelect) {
    // TODO Auto-generated method stub
    Query queryPoDet = entityManager.createNativeQuery(sqlSelect);
    if (!CommonUtils.isNullOrEmpty(orderNo)) {
      queryPoDet.setParameter("orderNo", orderNo);
    }
    if (!CommonUtils.isNullOrEmpty(poNo)) {
      queryPoDet.setParameter("poNo", poNo);
    }
    if (!CommonUtils.isNullOrEmpty(vendor)) {
      queryPoDet.setParameter("vendor", vendor);
    }

    List<Object[]> supplierPODetailList = queryPoDet.getResultList();
    return supplierPODetailList;
  }

  @Override
  public ReturnCode updateFactoryPODStatus(String orderNo, String poNo, String vendor, String delivery_status,
      String delivery_date,
      String delivery_by, String trackingNo) throws ParseException {
    // TODO Auto-generated method stub

    Date dateDelivery = new SimpleDateFormat("dd/MM/yyyy").parse(delivery_date);

    FactoryPODStatus facPODStatus = new FactoryPODStatus();
    facPODStatus.setD_change(LocalDateTime.now());
    facPODStatus.setD_delivery(dateDelivery);
    facPODStatus.setDelivery_by(Integer.valueOf(delivery_by));
    facPODStatus.setDelivery_other("-");
    facPODStatus.setStatus(delivery_status);
    facPODStatus.setPono(Integer.valueOf(poNo));
    facPODStatus.setTracking(trackingNo);
    facPODStatus.setUserId(125);

    entityManager.persist(facPODStatus);

    return ReturnCode.SUCCESS;
  }

  @Override
  public ReturnCode updateFactoryPODStatusDetail(FactoryPODStatus getPODDetByISerial, String delivery_status,
      String delivery_date,
      String delivery_by, String trackingNo, String delivery_other) throws ParseException {
    // TODO Auto-generated method stub
    if (!CommonUtils.isNullOrEmpty(delivery_status) || !CommonUtils.isNullOrEmpty(delivery_date)
        || !CommonUtils.isNullOrEmpty(delivery_by) || !CommonUtils.isNullOrEmpty(trackingNo)) {
      if (!CommonUtils.isNullOrEmpty(delivery_status)) {
        getPODDetByISerial.setStatus(delivery_status);
      }
      if (!CommonUtils.isNullOrEmpty(delivery_date)) {
        Date dateDelivery = new SimpleDateFormat("dd/MM/yyyy").parse(delivery_date);
        getPODDetByISerial.setD_delivery(dateDelivery);
      }
      if (!CommonUtils.isNullOrEmpty(delivery_by)) {
        getPODDetByISerial.setDelivery_by(Integer.valueOf(delivery_by));
      }
      if (!CommonUtils.isNullOrEmpty(trackingNo)) {
        getPODDetByISerial.setTracking(trackingNo);
      }

      getPODDetByISerial.setDelivery_other(delivery_other);
      getPODDetByISerial.setD_change(LocalDateTime.now());

      entityManager.flush();
    }

    return ReturnCode.SUCCESS;

  }

  @Override
  public FactoryPODStatus fidPODStatusByID(Integer i_serialFacPODStatus) {
    // TODO Auto-generated method stub
    return entityManager.find(FactoryPODStatus.class, i_serialFacPODStatus);
  }

  @Override
  public void addFileDB(File fileDB) {
    // TODO Auto-generated method stub
    entityManager.persist(fileDB);
  }

  @Override
  public void insertPODStatus(String iuser, String orderNo, String poNo, String vendor, String delivery_status,
      String delivery_date, String delivery_by, String delivery_other, String trackingNo) throws ParseException {
    // TODO Auto-generated method stub
    FactoryPODStatus setNewPODStatus = new FactoryPODStatus();
    setNewPODStatus.setPono(Integer.valueOf(poNo));
    setNewPODStatus.setD_change(LocalDateTime.now());

    Date dateDelivery = new SimpleDateFormat("dd/MM/yyyy").parse(delivery_date);
    setNewPODStatus.setD_delivery(dateDelivery);

    setNewPODStatus.setStatus(delivery_status);
    setNewPODStatus.setDelivery_by(Integer.valueOf(delivery_by));
    setNewPODStatus.setDelivery_other(delivery_other);
    setNewPODStatus.setTracking(trackingNo);
    setNewPODStatus.setUserId(Integer.valueOf(iuser));
    setNewPODStatus.setOrderNo(Integer.valueOf(orderNo));

    entityManager.persist(setNewPODStatus);
  }

  @Override
  public boolean checkExitsPODStatus(String orderNo, String poNo) {
    // TODO Auto-generated method stub
    String sql = "SELECT podstatus.*" +
        "  FROM webapp.W05_P_FACTORY_POD_STATUS podstatus," +
        "       P_AUTO_FACTORY_POLINK polink" +
        " WHERE     polink.i_order = podstatus.i_order" +
        "       AND polink.i_pono = podstatus.i_pono" +
        "       AND polink.S_EXTERN_ORDERNO = :orderNo" +
        "       AND polink.i_pono = :poNo";
    Query query = entityManager.createNativeQuery(sql);

    if (!CommonUtils.isNullOrEmpty(orderNo)) {
      query.setParameter("orderNo", orderNo);
    }
    if (!CommonUtils.isNullOrEmpty(poNo)) {
      query.setParameter("poNo", poNo);
    }

    int count = query.getResultList().size();
    return (count == 0) ? true : false;
  }

  @Override
  public void addHistory(PODStatusHistory podHis) {
    // TODO Auto-generated method stub
    entityManager.persist(podHis);
  }

}
