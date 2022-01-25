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
import org.openmrs.api.context.Context;
import org.openmrs.messagesource.MessageSourceService;
import org.openmrs.module.addresshierarchy.AddressHierarchyEntry;
import org.openmrs.module.savicsgmao.api.entity.Mouvement;
import org.openmrs.module.savicsgmao.api.service.GmaoService;
import org.springframework.beans.factory.annotation.Autowired;

/**
 * @author anatoleabe
 */
public class MovementExport {
	
	private XSSFWorkbook workbook;
	
	private XSSFSheet sheet;
	
	private List<Mouvement> listMouvements;
	
	private Boolean movementValidated;
	
	@Autowired
	private MessageSourceService messageSourceService;
	
	public MovementExport(List<Mouvement> listMouvements, Boolean movementValidated) {
		this.listMouvements = listMouvements;
		this.movementValidated = movementValidated;
		workbook = new XSSFWorkbook();
	}
	
	private void writeHeaderLine() {
		sheet = workbook.createSheet("Liste des mouvements sur équipement");
		
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
		createCell(row, index++, "Type de Mouvement", cellStyle);
		createCell(row, index++, "Date du mouvement", cellStyle);
		createCell(row, index++, "Motif", cellStyle);
		createCell(row, index++, "Nom de l'équipement", cellStyle);
		createCell(row, index++, "Numéro de série", cellStyle);
		createCell(row, index++, "Type d'équipement", cellStyle);
		
		createCell(row, index++, "Region source", cellStyle);
		createCell(row, index++, "District source", cellStyle);
		createCell(row, index++, "HD/CSI source", cellStyle);
		createCell(row, index++, "Departement HD/CSI source", cellStyle);
		createCell(row, index++, "Site source", cellStyle);
		
		createCell(row, index++, "Region destination", cellStyle);
		createCell(row, index++, "District destination", cellStyle);
		createCell(row, index++, "HD/CSI destination", cellStyle);
		createCell(row, index++, "Departement HD/CSI destination", cellStyle);
		createCell(row, index++, "Site destination", cellStyle);
		createCell(row, index++, "Statut", cellStyle);
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
		
		for (Mouvement item : listMouvements) {
			Row row = null;
			if (movementValidated && ("VALID".equals(item.getStatus()))) {
				row = sheet.createRow(rowCount++);
				
			} else if (!movementValidated) {
				row = sheet.createRow(rowCount++);
			}
			DateFormat simpleDateFormat = new SimpleDateFormat("dd/MM/yyyy'T'HH:mm:ss.SSS'Z'", Locale.ENGLISH);
			
			if (row != null) {
				
				AddressHierarchyEntry addressHierarchyEntrySource = (AddressHierarchyEntry) Context.getService(
				    GmaoService.class).getEntityByid(AddressHierarchyEntry.class, "addressHierarchyEntryId",
				    item.getSiteBySourceId().getService().getHealthcenter().getDistrict().getRegionid());
				
				AddressHierarchyEntry addressHierarchyEntryDest = (AddressHierarchyEntry) Context.getService(
				    GmaoService.class).getEntityByid(AddressHierarchyEntry.class, "addressHierarchyEntryId",
				    item.getSiteByDestinationId().getService().getHealthcenter().getDistrict().getRegionid());
				
				int columnCount = 0;
				
				createCell(row, columnCount++, item.getTypeDisplay(), style);
				createCell(row, columnCount++, simpleDateFormat.format(item.getDate()) + "", style);
				createCell(row, columnCount++, item.getMotif(), style);
				createCell(row, columnCount++, item.getEquipment().getName(), style);
				createCell(row, columnCount++, item.getEquipment().getSerialNumber(), style);
				createCell(row, columnCount++, item.getEquipment().getEquipmentType().getName(), style);
				
				createCell(row, columnCount++, addressHierarchyEntrySource.getName() + "", style);
				createCell(row, columnCount++, item.getSiteBySourceId().getService().getHealthcenter().getDistrict()
				        .getName()
				        + "", style);
				createCell(row, columnCount++, item.getSiteBySourceId().getService().getHealthcenter().getName() + "", style);
				createCell(row, columnCount++, item.getSiteBySourceId().getService().getName() + "", style);
				createCell(row, columnCount++, item.getSiteBySourceId().getName() + "", style);
				
				createCell(row, columnCount++, addressHierarchyEntryDest.getName() + "", style);
				createCell(row, columnCount++, item.getSiteByDestinationId().getService().getHealthcenter().getDistrict()
				        .getName()
				        + "", style);
				createCell(row, columnCount++, item.getSiteByDestinationId().getService().getHealthcenter().getName() + "",
				    style);
				createCell(row, columnCount++, item.getSiteByDestinationId().getService().getName() + "", style);
				createCell(row, columnCount++, item.getSiteByDestinationId().getName() + "", style);
				createCell(row, columnCount++, item.getStatus() != null ? item.getStatus() : "" + "", style);
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
