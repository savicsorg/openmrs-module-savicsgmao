/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.openmrs.module.savicsgmao.export;

import java.io.IOException;
import java.util.List;
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
import org.openmrs.messagesource.MessageSourceService;
import org.openmrs.module.savicsgmao.api.entity.Maintenance;
import org.springframework.beans.factory.annotation.Autowired;

/**
 * @author anatoleabe
 */
public class MaintenanceExport {
	
	private XSSFWorkbook workbook;
	
	private XSSFSheet sheet;
	
	private List<Maintenance> listMaintenance;
	
	@Autowired
	private MessageSourceService messageSourceService;
	
	public MaintenanceExport(List<Maintenance> listMaintenance) {
		this.listMaintenance = listMaintenance;
		workbook = new XSSFWorkbook();
	}
	
	private void writeHeaderLine() {
		sheet = workbook.createSheet("Liste des maintenances");
		
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
		createCell(row, index++, "Equipement", cellStyle);
		createCell(row, index++, "Numéro de série", cellStyle);
		createCell(row, index++, "Site", cellStyle);
		createCell(row, index++, "Service", cellStyle);
		createCell(row, index++, "Type de maintenance", cellStyle);
		createCell(row, index++, "Fait par", cellStyle);
		createCell(row, index++, "Raison", cellStyle);
		createCell(row, index++, "Tâche", cellStyle);
		createCell(row, index++, "Description", cellStyle);
		createCell(row, index++, "Début", cellStyle);
		createCell(row, index++, "Fin", cellStyle);
		createCell(row, index++, "Statut", cellStyle);
		createCell(row, index++, "Date de création", cellStyle);
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
		
		for (Maintenance item : listMaintenance) {
			Row row = sheet.createRow(rowCount++);
			int columnCount = 0;
			
			createCell(row, columnCount++, item.getEquipment().getSerialNumber(), style);
			createCell(row, columnCount++, item.getEquipment().getName(), style);
			createCell(row, columnCount++, item.getEquipment().getSite().getService().getName(), style);
			createCell(row, columnCount++, item.getEquipment().getSite().getName(), style);
			createCell(row, columnCount++, item.getEquipment().getName(), style);
			createCell(row, columnCount++, item.getMaintenanceType().getName(), style);
			createCell(row, columnCount++, item.getDoneby(), style);
			createCell(row, columnCount++, "", style);
			createCell(row, columnCount++, "" + "", style);
			createCell(row, columnCount++, item.getDescription() + "", style);
			createCell(row, columnCount++, item.getStartdate() + "", style);
			createCell(row, columnCount++, item.getEnddate() + "", style);
			createCell(row, columnCount++, item.getStatusDisplay(item.getStatus()) + "", style);
			createCell(row, columnCount++, item.getCreation() + "", style);
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
