package com.amos.podsupapi.services;

import java.util.List;

import com.amos.podsupapi.common.ReturnCode;
import com.amos.podsupapi.dto.admin.ShipToDTO;

public interface ShipToService {
	
	public List<ShipToDTO> getAutoPO(int pono);

	public ReturnCode updateShipTo(String payload) throws Exception;
	
	public List<ShipToDTO> getShipTo(int order);
}
