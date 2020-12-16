package com.amos.podsupapi.services;

import java.util.List;

import com.amos.podsupapi.dto.ChannelDTO;
import com.amos.podsupapi.dto.OrderStatusDTO;
import com.amos.podsupapi.dto.ProdLineDTO;
import com.amos.podsupapi.dto.VendorDTO;

public interface KeyService {

  public List<ChannelDTO> getAllChannel();

  public List<VendorDTO> getAllVendor();

  public List<OrderStatusDTO> getAllOrderStatus();

  public List<ProdLineDTO> getAllProdline();

  public List<OrderStatusDTO> getAllDeliveryBy();

  public String getAllProvince();
}
