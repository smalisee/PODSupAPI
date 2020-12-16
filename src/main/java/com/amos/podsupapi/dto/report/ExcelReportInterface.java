package com.amos.podsupapi.dto.report;

import java.util.List;

public interface ExcelReportInterface {
	
	List<String> getExcelHeader();

	List<Object> getExcelData();

}
