package com.amos.podsupapi.repository;

import java.time.LocalDate;
import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;

import org.springframework.stereotype.Repository;

import com.amos.podsupapi.common.CommonUtils;
import com.amos.podsupapi.model.ShipTo;

@Repository
public class ShipToRepositoryImpl implements ShipToRepository {

  @PersistenceContext
  private EntityManager entityManager;

  @Override
  public ShipTo getShipToByOrder(int order) {
    return entityManager.find(ShipTo.class, order);
  }

  @Override
  public void updateShipTo(ShipTo shipto) {
    ShipTo shiptol = getShipToByOrder(shipto.getOrder());
    shiptol.setFirstName(shipto.getFirstName());
    shiptol.setName(shipto.getName());
    shiptol.setPhoneNo1(shipto.getPhoneNo1());
    shiptol.setAddress1(shipto.getAddress1());
    shiptol.setAddress7(shipto.getAddress7());
    shiptol.setAddress2(shipto.getAddress2());
    shiptol.setAddress3(shipto.getAddress3());
    shiptol.setCity(shipto.getCity());
    shiptol.setZipCode(shipto.getZipCode());
    shiptol.setChange(LocalDate.now());
    entityManager.flush();

  }

  @Override
  public List<Object[]> getShipto(String sqlSelect, int pono) {
    // TODO Auto-generated method stub
    Query query = entityManager.createNativeQuery(sqlSelect);
    if (!CommonUtils.isNullOrEmpty(String.valueOf(pono))) {
      query.setParameter("pono", pono);
    }

    List<Object[]> resultList = query.getResultList();
    return resultList.size() > 0 ? resultList : null;
  }

  @Override
  public void addShipto(ShipTo shipto) {
    // TODO Auto-generated method stub

    shipto.setCompany(0);
    shipto.setShipTo("O");
    shipto.setRunning(0);
    shipto.setCountry(0);
    shipto.setCreate(LocalDate.now());
    shipto.setChange(LocalDate.now());
    shipto.setOpCode("AM");
    entityManager.persist(shipto);
  }

  @Override
  public List<ShipTo> getShipto(int pono) {
    // String hql="FROM ShipTo as shipTo where shipTo.order = :order";
    // return entityManager.createQuery(hql, ShipTo.class)
    // .setParameter("order",order)
    // .getResultList();
    String hql = "FROM ShipTo s RIGHT JOIN FETCH s.autoPO a where a.order = s.order and a.pono = :pono";
    return entityManager.createQuery(hql, ShipTo.class)
        .setParameter("pono", pono)
        .getResultList();

  }

}
