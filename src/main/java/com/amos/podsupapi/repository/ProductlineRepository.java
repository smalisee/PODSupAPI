package com.amos.podsupapi.repository;

import java.util.List;

import com.amos.podsupapi.model.ProductLine;

public interface ProductlineRepository {

	ProductLine getProdlineById(int prod1, int prod3);
	
	 List<ProductLine> getProdlineByUserId(int UserId);
}
