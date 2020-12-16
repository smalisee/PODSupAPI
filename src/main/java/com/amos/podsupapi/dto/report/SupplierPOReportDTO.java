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
public class SupplierPOReportDTO implements Serializable, ExcelReportInterface {

  private static final long serialVersionUID = 1L;

  private String orderNo;
  private String firstName;
  private String lastName;
  private String pono;
  private String delivery_status;
  private String delivery_date;
  private String delivery_by;
  private String trackingNo;

  @Override
  public List<String> getExcelHeader() {
    // TODO Auto-generated method stub
    ArrayList<String> tableHeaders = new ArrayList<>();
    tableHeaders.add("เลขออเดอร์");
    tableHeaders.add("ชื่อ                           ");
    tableHeaders.add("นามสกุล");
    tableHeaders.add("เลข PO");
    tableHeaders.add("สถานะจัดส่ง");
    tableHeaders.add("วันที่จัดส่ง                          ");
    tableHeaders.add("เลขพัสดุ");
    return tableHeaders;
  }

  @JsonIgnore
  @Override
  public List<Object> getExcelData() {
    // TODO Auto-generated method stub
    ArrayList<Object> rowData = new ArrayList<>();
    rowData.add(this.getOrderNo());
    rowData.add(this.getFirstName());
    rowData.add(this.getLastName());
    rowData.add(this.getPono());
    rowData.add(this.getDelivery_status());
    rowData.add(this.getDelivery_date());
    rowData.add(this.getTrackingNo());
    return rowData;
  }

}
