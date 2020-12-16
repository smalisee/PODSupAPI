package com.amos.podsupapi.services;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.amos.podsupapi.component.file.ExcelCreatorComponent;
import com.amos.podsupapi.dto.POManageReportSearchDTO;
import com.amos.podsupapi.dto.report.ExcelReportInterface;
import com.amos.podsupapi.dto.report.POReportDTO;
import com.amos.podsupapi.dto.report.SupplierPOReportDTO;
import com.amos.podsupapi.exception.PoBusinessException;

@Service
public class POManagementReportServiceImpl implements POManagementReportService {

  @Autowired
  private AutoFactoryPOService autoFactoryPOService;

  @Autowired
  private SupplierService supplierService;

  @Autowired
  private ExcelCreatorComponent excelCreator;

  @Override
  public List<?> getReportView(String view, POManageReportSearchDTO searchBean) {
    // TODO Auto-generated method stub
    return null;
  }

  @Override
  @Transactional(propagation = Propagation.REQUIRES_NEW, noRollbackFor = PoBusinessException.class)
  public byte[] getReportExcel(String view, POManageReportSearchDTO searchBean) throws Exception {
    List<? extends ExcelReportInterface> lData = null;
    String docHeader;
    List<String> tableHeaders;
    switch (view) {
    case "P":
      docHeader = "ข้อมูล PO Management" + searchBean.toString();
      lData = autoFactoryPOService.getAutoPODetailReport(searchBean);
      tableHeaders = new POReportDTO().getExcelHeader();
      break;

    case "S":
      String vendor = searchBean.getVendor();

      docHeader = "ข้อมูล PO ของ  Supplier " + vendor;
      lData = supplierService.getSupplierPODetailReport(searchBean);
      tableHeaders = new SupplierPOReportDTO().getExcelHeader();
      break;

    default:
      throw (new Exception("Wrong Report Type input"));
    }

    byte[] result = excelCreator.createExcelWithSheetTableData(docHeader, tableHeaders, lData);
    return result;
  }

  // @Override
  // @Transactional(propagation = Propagation.REQUIRES_NEW, noRollbackFor = PoBusinessException.class)
  // public byte[] getSupplierPOReportExcel(POManageReportSearchDTO searchBean) throws Exception {
  // List<? extends ExcelReportInterface> lData = null;
  // String docHeader;
  // List<String> tableHeaders;
  // try {
  //
  // String vendor = searchBean.getVendor();
  //
  // docHeader = "ข้อมูล PO ของ Supplier " + vendor;
  // lData = supplierService.getSupplierPODetailReport(searchBean);
  // tableHeaders = new SupplierPOReportDTO().getExcelHeader();
  // } catch (Exception e) {
  // throw (new Exception("Wrong Report Type input"));
  // }
  //
  // byte[] result = excelCreator.createExcelWithSheetTableData(docHeader, tableHeaders, lData);
  // return result;
  // }

}
