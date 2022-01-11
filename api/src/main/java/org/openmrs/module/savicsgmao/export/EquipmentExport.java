/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.openmrs.module.savicsgmao.export;

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
import org.openmrs.module.savicsgmao.api.entity.Equipment;
import org.openmrs.module.savicsgmao.api.entity.MaintenanceRequest;
import org.springframework.beans.factory.annotation.Autowired;

/**
 * @author anatoleabe
 */
public class EquipmentExport {
	
	private XSSFWorkbook workbook;
	
	private XSSFSheet sheet;
	
	private List<Equipment> equipments;
	
	@Autowired
	private MessageSourceService messageSourceService;
	
	public EquipmentExport(List<Equipment> equipments) {
		this.equipments = equipments;
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
		createCell(row, index++, "Nom de l'équipement", cellStyle);
		createCell(row, index++, "Numéro de série", cellStyle);
		createCell(row, index++, "Type d'équipement", cellStyle);
		createCell(row, index++, "Model", cellStyle);
		createCell(row, index++, "Puissance requise", cellStyle);
		createCell(row, index++, "Service après-vente", cellStyle);
		createCell(row, index++, "Personne responsable", cellStyle);
		createCell(row, index++, "Utilisateur", cellStyle);
		createCell(row, index++, "Status", cellStyle);
		createCell(row, index++, "Composants de remplacement", cellStyle);
		createCell(row, index++, "Explication", cellStyle);
		createCell(row, index++, "Manuels disponibles", cellStyle);
		createCell(row, index++, "Region", cellStyle);
		createCell(row, index++, "District", cellStyle);
		createCell(row, index++, "HD/CSI", cellStyle);
		createCell(row, index++, "Departement HD/CSI", cellStyle);
		createCell(row, index++, "Site", cellStyle);
		createCell(row, index++, "Date d'acquisition", cellStyle);
		createCell(row, index++, "Année de mise en service", cellStyle);
		createCell(row, index++, "Valeur d'acquisition", cellStyle);
		createCell(row, index++, "Mode d'acquisition", cellStyle);
		createCell(row, index++, "Suivi", cellStyle);
		createCell(row, index++, "Poids", cellStyle);
		createCell(row, index++, "Volume", cellStyle);
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
		
		for (Equipment item : equipments) {
			Row row = sheet.createRow(rowCount++);
			int columnCount = 0;
			createCell(row, columnCount++, item.getName(), style);
			createCell(row, columnCount++, item.getSerialNumber(), style);
			createCell(row, columnCount++, item.getEquipmentType().getName(), style);
			createCell(row, columnCount++, item.getModel(), style);
			createCell(row, columnCount++, item.getPower(), style);
			createCell(row, columnCount++, item.getAftersaleservice(), style);
			createCell(row, columnCount++, item.getResponsibleperson() + "", style);
			createCell(row, columnCount++, item.getUseraffectedto() + "", style);
			createCell(row, columnCount++, item.getServiceStatus() + "", style);
			createCell(row, columnCount++, item.getReplacementcomponent() + "", style);
			createCell(row, columnCount++, item.getExplainMore() + "", style);
			createCell(row, columnCount++, item.getManuals() + "", style);
			createCell(row, columnCount++, item.getSite().getService().getHealthcenter().getDistrict().getRegionid() + "",
			    style);
			createCell(row, columnCount++, item.getSite().getService().getHealthcenter().getDistrict().getName() + "", style);
			createCell(row, columnCount++, item.getSite().getService().getHealthcenter().getName() + "", style);
			createCell(row, columnCount++, item.getSite().getService().getName() + "", style);
			createCell(row, columnCount++, item.getSite().getName() + "", style);
			createCell(row, columnCount++, item.getAcquisitionDate() + "", style);
			createCell(row, columnCount++, item.getCommisionningYear() + "", style);
			createCell(row, columnCount++, item.getAcquisitionValue() + "", style);
			createCell(row, columnCount++, item.getAcquisitionMode() + "", style);
			createCell(row, columnCount++, item.getTracking() + "", style);
			createCell(row, columnCount++, item.getWeight() + "", style);
			createCell(row, columnCount++, item.getVolume() + "", style);
			
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