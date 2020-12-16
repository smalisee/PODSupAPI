package com.amos.podsupapi.repository;

import java.util.List;

import com.amos.podsupapi.model.ShipTo;

public interface ShipToRepository {

	ShipTo getShipToByOrder(int order);
	
	List<ShipTo> getShipto(int order);
	
	void updateShipTo(ShipTo shipto);
}
