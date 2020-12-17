package com.amos.podsupapi.repository;

import java.util.List;

import com.amos.podsupapi.model.AutoFactoryPO;

public interface AutoFactoryPORepository {

  List<AutoFactoryPO> getAutoFactoryPO(int pono);

  List<AutoFactoryPO> getAllAutoPO(int pono, int order);

  AutoFactoryPO getAutoPOByPOno(int pono);

  boolean autoPOCheckStatus(int order);

  boolean autoPOExists(int pono);

  List<AutoFactoryPO> getAutoFactoryPOByOrder(int order);

  void updatePOrdsum(Integer order);

  void updatePOFacoryShipto(int pono);

}
