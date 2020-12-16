package com.amos.podsupapi.dto.report;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonInclude;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@JsonInclude(JsonInclude.Include.NON_NULL)
public class POReportDTO implements Serializable, ExcelReportInterface {

	private static final long serialVersionUID = 1L;
	
	private String orderDate;
	private String channel;
	private String orderNo;
	private String payMode;
	private String firstName;
	private String lastName;
	private String phoneNo;
	private String cvCode;
	private String cvName;
	private Integer poNo;
	private Integer productLine;
	private String statusSend;

	@JsonIgnore
	@Override
	public List<String> getExcelHeader() {
		ArrayList<String> tableHeaders = new ArrayList<>();
		tableHeaders.add("Ordder Date");		
		tableHeaders.add("Chanel");
		tableHeaders.add("Ordder No.");
		tableHeaders.add("WH Code");
		tableHeaders.add("Paymode");
		tableHeaders.add("ชื่อ");
		tableHeaders.add("นามสกุล ");
		tableHeaders.add("เบอร์โทร    ");
		tableHeaders.add("CV Code");
		tableHeaders.add("CV Name   ");		
		tableHeaders.add("PO Number");
		tableHeaders.add("Product Line");
		tableHeaders.add("สถานะการจัดส่ง");
		return tableHeaders;
	}

	@Override
	public List<Object> getExcelData() {
		ArrayList<Object> rowData = new ArrayList<>();
		rowData.add(this.getOrderDate());
		rowData.add(this.getChannel());
		rowData.add(this.getOrderNo());
		rowData.add(this.getPayMode());
		rowData.add(this.getFirstName());
		rowData.add(this.getLastName());
		rowData.add(this.getPhoneNo());
		rowData.add(this.getCvCode());
		rowData.add(this.getCvName());
		rowData.add(this.getPoNo());
		rowData.add(this.getProductLine());
		rowData.add(this.getStatusSend());
		return rowData;
	}

	
}
