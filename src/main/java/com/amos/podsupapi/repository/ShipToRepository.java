package com.amos.podsupapi.repository;

import java.util.List;

import com.amos.podsupapi.model.ShipTo;

public interface ShipToRepository {

  ShipTo getShipToByOrder(int order);

  List<Object[]> getShipto(String sqlSelect, int pono);

  void updateShipTo(ShipTo shipto);

  void addShipto(ShipTo shipto);

  List<ShipTo> getShipto(int pono);
}
