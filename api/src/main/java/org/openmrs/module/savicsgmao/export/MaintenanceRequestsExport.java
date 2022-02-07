/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.openmrs.module.savicsgmao.export;

import java.io.IOException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.List;
import java.util.Locale;
import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletResponse;
import org.apache.poi.ss.usermodel.BorderStyle;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.CellStyle;
import org.apache.poi.ss.usermodel.FillPatternType;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.xssf.usermodel.XSSFCellStyle;
import org.apache.poi.xssf.usermodel.XSSFColor;
import org.apache.poi.xssf.usermodel.XSSFFont;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.openmrs.module.savicsgmao.api.entity.MaintenanceRequest;

/**
 * @author anatoleabe
 */
public class MaintenanceRequestsExport {
	
	private XSSFWorkbook workbook;
	
	private XSSFSheet sheet;
	
	private List<MaintenanceRequest> listMaintenanceRequests;
	
	private Boolean requestValidated;
	
	public MaintenanceRequestsExport(List<MaintenanceRequest> listMaintenanceRequests, Boolean requestValidated) {
		this.listMaintenanceRequests = listMaintenanceRequests;
		this.requestValidated = requestValidated;
		workbook = new XSSFWorkbook();
	}
	
	private void writeHeaderLine() {
		sheet = workbook.createSheet("Liste des demandes de maintenance");
		
		Row row = sheet.createRow(0);
		XSSFFont font = workbook.createFont();
		font.setBold(true);
		font.setFontHeight(16);
		
		XSSFColor color = new XSSFColor(new java.awt.Color(244, 204, 204), null);
		XSSFCellStyle cellStyle = workbook.createCellStyle();
		
		cellStyle.setFillForegroundColor(color);
		cellStyle.setFillPattern(FillPatternType.SOLID_FOREGROUND);
		cellStyle.setBorderBottom(BorderStyle.THIN);
		cellStyle.setBorderTop(BorderStyle.THIN);
		cellStyle.setBorderRight(BorderStyle.THIN);
		cellStyle.setBorderLeft(BorderStyle.THIN);
		cellStyle.setFont(font);
		
		int index = 0;
		createCell(row, index++, "Numéro de régistre", cellStyle);
		createCell(row, index++, "Nature du travail", cellStyle);
		createCell(row, index++, "Demandeur", cellStyle);
		createCell(row, index++, "Priorité", cellStyle);
		createCell(row, index++, "Equipement", cellStyle);
		createCell(row, index++, "Raison", cellStyle);
		createCell(row, index++, "Date", cellStyle);
		createCell(row, index++, "Validation du mouvement", cellStyle);
	}
	
	private void createCell(Row row, int columnCount, Object value, CellStyle style) {
		sheet.autoSizeColumn(columnCount);
		Cell cell = row.createCell(columnCount);
		if (value instanceof Integer) {
			cell.setCellValue((Integer) value);
		} else if (value instanceof Double) {
			cell.setCellValue((Double) value);
		} else if (value instanceof Boolean) {
			cell.setCellValue((Boolean) value);
		} else {
			cell.setCellValue((String) value);
		}
		cell.setCellStyle(style);
	}
	
	private void writeDataLines() throws IOException {
		int rowCount = 1;
		
		CellStyle style = workbook.createCellStyle();
		XSSFFont font = workbook.createFont();
		font.setFontHeight(14);
		style.setFont(font);
		style.setBorderBottom(BorderStyle.THIN);
		style.setBorderTop(BorderStyle.THIN);
		style.setBorderRight(BorderStyle.THIN);
		style.setBorderLeft(BorderStyle.THIN);
		
		for (MaintenanceRequest item : listMaintenanceRequests) {
			Row row = null;
			if (requestValidated && ("VALID".equals(item.getStatus()))) {
                            row = sheet.createRow(rowCount++);
				
			} else if (!requestValidated) {
                            row = sheet.createRow(rowCount++);
			}
			
			if (row != null) {
				int columnCount = 0;
				createCell(row, columnCount++, item.getRegisterNumber(), style);
				createCell(row, columnCount++, item.getNatureOfWorkDisplay(item.getNatureOfWork()), style);
				createCell(row, columnCount++, item.getRequestedby(), style);
				createCell(row, columnCount++, item.getPriorityDisplay(item.getPriority()), style);
				createCell(row, columnCount++, item.getEquipment().getName(), style);
				createCell(row, columnCount++, item.getMotif(), style);
				createCell(row, columnCount++, item.getCreation() + "", style);
				createCell(row, columnCount++, item.getStatus() != null ? ("VALID".equals(item.getStatus()) ? "Oui"
				        : "REJECT".equals(item.getStatus()) ? "Non" : "") : "", style);
			}
		}
	}
	
	public void export(HttpServletResponse response) throws IOException {
		writeHeaderLine();
		writeDataLines();
		
		ServletOutputStream outputStream = response.getOutputStream();
		workbook.write(outputStream);
		workbook.close();
		
		outputStream.close();
		
	}
}
