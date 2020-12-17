package com.amos.podsupapi.services;

import com.amos.podsupapi.common.ReturnCode;
import com.amos.podsupapi.dto.admin.ShipToDTO;

public interface ShipToService {

  public ShipToDTO getAutoPO(int pono);

  public ReturnCode updateShipTo(String payload) throws Exception;

  public ShipToDTO getShipTo(int order);
}
