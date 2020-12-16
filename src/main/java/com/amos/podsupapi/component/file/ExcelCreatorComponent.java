package com.amos.podsupapi.component.file;

import java.io.IOException;
import java.util.List;

import com.amos.podsupapi.dto.report.ExcelReportInterface;

public interface ExcelCreatorComponent {
	public byte[] createExcelWithSheetTableData(String docHeader, List<String> tableHeaders, List<? extends ExcelReportInterface> data) throws IOException;
}
