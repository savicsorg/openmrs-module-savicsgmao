/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.openmrs.module.savicsgmao.export;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
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
import org.json.simple.parser.ParseException;
import org.openmrs.messagesource.MessageSourceService;
import org.openmrs.module.savicsgmao.api.entity.MaintenanceRequest;
import org.springframework.beans.factory.annotation.Autowired;

/**
 * @author anatoleabe
 */
public class MaintenanceRequestsExport {
	
	private XSSFWorkbook workbook;
	
	private XSSFSheet sheet;
	
	private List<MaintenanceRequest> listMaintenanceRequests;
	
	@Autowired
	private MessageSourceService messageSourceService;
	
	public MaintenanceRequestsExport(List<MaintenanceRequest> listMaintenanceRequests) {
		this.listMaintenanceRequests = listMaintenanceRequests;
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
	
	private void writeDataLines() {
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
			try {
				Row row = sheet.createRow(rowCount++);
				int columnCount = 0;
				
				createCell(row, columnCount++, item.getRegisterNumber(), style);
				createCell(row, columnCount++, item.getNatureOfWorkDisplay(item.getNatureOfWork()), style);
				createCell(row, columnCount++, item.getRequestedby(), style);
				createCell(row, columnCount++, item.getPriorityDisplay(item.getPriority()), style);
				createCell(row, columnCount++, item.getEquipment().getName(), style);
				createCell(row, columnCount++, item.getMotif(), style);
				createCell(row, columnCount++, item.getCreation() + "", style);
			}
			catch (IOException ex) {
				Logger.getLogger(MaintenanceRequestsExport.class.getName()).log(Level.SEVERE, null, ex);
			}
			catch (ParseException ex) {
				Logger.getLogger(MaintenanceRequestsExport.class.getName()).log(Level.SEVERE, null, ex);
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