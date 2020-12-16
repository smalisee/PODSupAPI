package com.amos.podsupapi.component.file;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.List;

import org.apache.poi.ss.usermodel.BorderStyle;
import org.apache.poi.ss.usermodel.CellStyle;
import org.apache.poi.ss.util.CellRangeAddress;
import org.apache.poi.xssf.usermodel.XSSFCell;
import org.apache.poi.xssf.usermodel.XSSFFont;
import org.apache.poi.xssf.usermodel.XSSFRow;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.stereotype.Component;

import com.amos.podsupapi.dto.report.ExcelReportInterface;

@Component
public class ExcelCreatorComponentImpl implements ExcelCreatorComponent {

	private static final double START_DATA_ROWINDEX = 3;
	@Override
	public byte[] createExcelWithSheetTableData(String docHeader, List<String> tableHeaders,
			List<? extends ExcelReportInterface> data) throws IOException {
		
		ByteArrayOutputStream bos = new ByteArrayOutputStream();
		
		try (XSSFWorkbook workbook = new XSSFWorkbook()){
			/*
			 *  Font and Cell Style
			 */
			XSSFFont headerFont = workbook.createFont();
			headerFont.setFontName("Tahoma"); headerFont.setFontHeightInPoints((short) 12); headerFont.setBold(true);
			CellStyle headerStyle = workbook.createCellStyle();
			headerStyle.setBorderTop(BorderStyle.THIN); headerStyle.setBorderBottom(BorderStyle.THIN);
			headerStyle.setBorderLeft(BorderStyle.THIN); headerStyle.setBorderRight(BorderStyle.THIN);
		    headerStyle.setFont(headerFont);
		    
		    XSSFFont bodyFont = workbook.createFont();
		    bodyFont.setFontName("Tahoma"); bodyFont.setFontHeightInPoints((short) 11); bodyFont.setBold(false);
			CellStyle bodyStyle = workbook.createCellStyle();
			bodyStyle.setBorderBottom(BorderStyle.THIN); bodyStyle.setBorderLeft(BorderStyle.THIN);
			bodyStyle.setBorderRight(BorderStyle.THIN); bodyStyle.setBorderTop(BorderStyle.THIN);
			bodyStyle.setFont(bodyFont);
			/*
			 *  End Font and Cell Style
			 */
			
			XSSFSheet sheet = workbook.createSheet();
			XSSFRow row = sheet.createRow(0);
			XSSFCell cell = row.createCell(0);
			
			cell.setCellStyle(headerStyle); cell.setCellValue(docHeader);
			sheet.addMergedRegion(new CellRangeAddress( 0,  0,  0,  tableHeaders.size() ));
			
			row = sheet.createRow(1);			
			row = sheet.createRow(2);
			cell = row.createCell(0); cell.setCellValue("No."); cell.setCellStyle(headerStyle);
			
			int hIndex=1;
			for (String header : tableHeaders) {
				cell = row.createCell(hIndex); cell.setCellValue(header); cell.setCellStyle(headerStyle);
				sheet.autoSizeColumn(hIndex);
				hIndex++;
			}
			
			int rowIndex = 3;
			for (ExcelReportInterface reportRow : data) {
				row = sheet.createRow(rowIndex++);
				List<Object> rowData = reportRow.getExcelData();
				
				cell = row.createCell(0); cell.setCellValue((double)(rowIndex - START_DATA_ROWINDEX)); cell.setCellStyle(bodyStyle);
				
				int bIndex=1;
				for (Object d : rowData) {
					setCellValue(row, bIndex, d, bodyStyle);
					bIndex++;
				}
			}
			workbook.write(bos);
		}
		
		return bos.toByteArray();
	}

	private void setCellValue(XSSFRow row, int columnIndex, Object data, CellStyle bodyStyle) {
		XSSFCell cell;
		cell = row.createCell(columnIndex);
		if (data instanceof String) { cell.setCellValue((String) data); }
		else if(data instanceof Integer) { cell.setCellValue(((Integer) data).doubleValue()); }
		else if(data instanceof Double) { cell.setCellValue((Double) data); }
		else if(data == null){ cell.setCellValue(""); }
		else { cell.setCellValue(data.toString()); }
		if(bodyStyle != null) { cell.setCellStyle(bodyStyle); }
	}

}
