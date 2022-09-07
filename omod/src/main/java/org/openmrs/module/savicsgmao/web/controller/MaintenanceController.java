/**
 * This Source Code Form is subject to the terms of the Mozilla Public License,
 * v. 2.0. If a copy of the MPL was not distributed with this file, You can
 * obtain one at http://mozilla.org/MPL/2.0/. OpenMRS is also distributed under
 * the terms of the Healthcare Disclaimer located at http://openmrs.org/license.
 *
 * Copyright (C) OpenMRS Inc. OpenMRS is a registered trademark and the OpenMRS
 * graphic logo is a trademark of OpenMRS Inc.
 */
package org.openmrs.module.savicsgmao.web.controller;

import java.io.IOException;
import java.text.DateFormat;
import java.text.DecimalFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.concurrent.TimeUnit;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.poi.ss.usermodel.BorderStyle;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.CellStyle;
import org.apache.poi.ss.usermodel.FillPatternType;
import org.apache.poi.ss.usermodel.HorizontalAlignment;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.util.CellRangeAddress;
import org.apache.poi.xssf.usermodel.XSSFCellStyle;
import org.apache.poi.xssf.usermodel.XSSFColor;
import org.apache.poi.xssf.usermodel.XSSFFont;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.openmrs.api.context.Context;
import org.openmrs.api.db.hibernate.DbSession;
import org.openmrs.module.savicsgmao.SavicsGmaoModuleActivator;
import org.openmrs.module.savicsgmao.api.dto.EquipmentFailureRate;
import org.openmrs.module.savicsgmao.api.entity.Maintenance;
import org.openmrs.module.savicsgmao.api.entity.MaintenanceEvent;
import org.springframework.stereotype.Controller;
import org.openmrs.module.savicsgmao.api.entity.MaintenanceRequest;
import org.openmrs.module.savicsgmao.api.service.GmaoService;
import org.openmrs.module.savicsgmao.export.MaintenanceExport;
import org.openmrs.module.savicsgmao.export.MaintenanceRequestsExport;
import org.openmrs.module.savicsgmao.rest.v1_0.resource.GmaoRest;
import org.openmrs.module.webservices.rest.web.RestConstants;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

/**
 * @author anatoleabe The main controller.
 */
@Controller
public class MaintenanceController {
	
	protected final Log log = LogFactory.getLog(getClass());
	
	@Autowired
	GmaoService gmaoService;
	
	@RequestMapping(method = RequestMethod.GET, value = "/rest/" + RestConstants.VERSION_1 + GmaoRest.GMAO_NAMESPACE
	        + "/maintenancerequests/export")
	public void exportToExcel(HttpServletResponse response, HttpServletRequest request) throws IOException {
		response.setContentType("application/octet-stream");
		DateFormat dateFormatter = new SimpleDateFormat("yyyy-MM-dd_HH:mm:ss");
		String currentDateTime = dateFormatter.format(new Date());
		
		String headerKey = "Content-Disposition";
		String headerValue = "attachment; filename=Maintenance_Requests_" + currentDateTime + ".xlsx";
		response.setHeader(headerKey, headerValue);
		
		Boolean requestValidated = false;
		if (request.getParameter("requestValidated") != null) {
			requestValidated = Boolean.valueOf(request.getParameter("requestValidated"));
		}
		
		List<MaintenanceRequest> maintenanceRequestList = gmaoService.getAll(MaintenanceRequest.class);
		
		MaintenanceRequestsExport excelExporter = new MaintenanceRequestsExport(maintenanceRequestList, requestValidated);
		
		excelExporter.export(response);
	}
	
	@RequestMapping(method = RequestMethod.GET, value = "/rest/" + RestConstants.VERSION_1 + GmaoRest.GMAO_NAMESPACE
	        + "/maintenances/export")
	public void exportMaintenanceListToExcel(HttpServletResponse response) throws IOException {
		response.setContentType("application/octet-stream");
		DateFormat dateFormatter = new SimpleDateFormat("yyyy-MM-dd_HH:mm:ss");
		String currentDateTime = dateFormatter.format(new Date());
		
		String headerKey = "Content-Disposition";
		String headerValue = "attachment; filename=Maintenance_Requests_" + currentDateTime + ".xlsx";
		response.setHeader(headerKey, headerValue);
		
		List<Maintenance> maintenanceList = gmaoService.getAll(Maintenance.class);
		
		MaintenanceExport excelExporter = new MaintenanceExport(maintenanceList);
		
		excelExporter.export(response);
	}
	
	@RequestMapping(method = RequestMethod.GET, value = "/rest/" + RestConstants.VERSION_1 + GmaoRest.GMAO_NAMESPACE
	        + "/maintenances/withFailureRate")
	public void exportMaintenanceWithFailureRate(HttpServletResponse response, HttpServletRequest request)
	        throws IOException {
		response.setContentType("application/octet-stream");
		DateFormat dateFormatter = new SimpleDateFormat("yyyy-MM-dd_HH:mm:ss");
		String currentDateTime = dateFormatter.format(new Date());
		
		String headerKey = "Content-Disposition";
		String headerValue = "attachment; filename=Maintenance_with_higher_failure_rate_" + currentDateTime + ".xlsx";
		response.setHeader(headerKey, headerValue);
		
		DateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd");
		DateFormat simpleDateFormat2 = new SimpleDateFormat("dd-MM-yyyy");
		List<Maintenance> maintenances = new ArrayList<Maintenance>();
		Date toDate = null;
		Date fromDate = null;
		String sFromDate = null;
		SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");
		
		String fromDateString = "";
		String toDateString = "";
		
		try {
			if (request.getParameter("to") != null) {
				toDate = simpleDateFormat.parse(request.getParameter("to"));
				toDateString = formatter.format(toDate);
			}
			if (request.getParameter("from") != null) {
				fromDate = simpleDateFormat.parse(request.getParameter("from"));
				fromDateString = formatter.format(fromDate);
				;
			}
		}
		catch (ParseException ex) {
			Logger.getLogger(MaintenanceController.class.getName()).log(Level.SEVERE, null, ex);
		}
		
		DbSession session = Context.getService(GmaoService.class).getSession();
		List<EquipmentFailureRate> equipmentFailureRates = null;
		
		String dateRangeCond = "";
		long diffDays = 0;
		long days_difference = 0;
		if (toDate != null && fromDate != null) {
			long diffInMillies = toDate.getTime() - fromDate.getTime();
			diffDays = TimeUnit.DAYS.convert(diffInMillies, TimeUnit.DAYS) + 1;//Inclusive days
			days_difference = (diffInMillies / (1000 * 60 * 60 * 24)) % 365;
		}
		if (fromDateString != null && toDateString != null) {
			dateRangeCond = "and m.startdate >= \"" + fromDateString + "\" and m.enddate <= \"" + toDateString + "\" ";
		}
		
		String failureRate = "select q1.id, e2.name, e2.serial_number, q1.inactiveDays, q2.nbMaintenances " + "from ("
		        + "select id, sum(inactiveDays) as inactiveDays" + " from" + " (SELECT" + "  e.id, e.name,"
		        + "  DATEDIFF(enddate, startdate) + 1 AS inactiveDays "
		        + "FROM `gmao_maintenance` m inner join `gmao_equipment` e on m.`equipment_id` = e.id "
		        + "inner join `gmao_maintenance_type` mt on m.`maintenance_type_id` = mt.id "
		        + "where m.`maintenance_requested_id` is null and  mt.`uuid` = \"b0000001-d22b-493e-98e8-4d1de0621a8v\" "
		        + dateRangeCond
		        + " "
		        + "union all "
		        + " "
		        + "SELECT "
		        + "  e.id, e.name,"
		        + "  DATEDIFF(enddate, startdate) + 1 AS inactiveDays "
		        + "FROM `gmao_maintenance` m inner join `gmao_equipment` e on m.`equipment_id` = e.id "
		        + "inner join `gmao_maintenance_request` mr on m.maintenance_requested_id = mr.id "
		        + "inner join `gmao_maintenance_type` mt on m.`maintenance_type_id` = mt.id "
		        + "where m.`maintenance_requested_id` is not null and  mt.`uuid` = \"b0000001-d22b-493e-98e8-4d1de0621a8v\" "
		        + dateRangeCond
		        + ") z "
		        + "group by id ) q1 "
		        + "inner join "
		        + "(select e.id , count(e.id) as nbMaintenances from `gmao_equipment` e "
		        + "inner join `gmao_maintenance` m on e.id = m.`equipment_id` inner join `gmao_maintenance_type` mt on m.`maintenance_type_id` = mt.id "
		        + "where mt.`uuid` = \"b0000001-d22b-493e-98e8-4d1de0621a8v\" "
		        + dateRangeCond
		        + "group by e.id) q2 on q1.id = q2.id" + " inner join gmao_equipment e2 on q1.id = e2.id" + " ";
		
		List<List<Object>> results = Context.getAdministrationService().executeSQL(failureRate, true);
		List<EquipmentFailureRate> equipmentFailureRates1 = new ArrayList<EquipmentFailureRate>();
		
		Double globalRate = 60.0;
		String gpRate = Context.getAdministrationService().getGlobalProperty(
		    SavicsGmaoModuleActivator.GLOBAL_PROPERTY_EQUIPMENT_FAILURE_RATE);
		if (gpRate != null && gpRate != "") {
			globalRate = new Double(gpRate);
		}
		
		for (List<Object> temp : results) {
			EquipmentFailureRate efr = new EquipmentFailureRate();
			
			if (temp.get(0) != null && temp.get(1) != null) {
				efr.setEquipmentId(new Integer(temp.get(0).toString()));
				efr.setName(temp.get(1).toString());
				efr.setSerial(temp.get(2).toString());
				efr.setInactiveDays(new Integer(temp.get(3).toString()));
				efr.setCountMaintenance(new Integer(temp.get(4).toString()));
				Double rowRate = ((days_difference == 0) ? 0.0 : (efr.getInactiveDays() + 0.0) / (days_difference + 0.0)) * 100;
				
				efr.setRate(rowRate);
				if (rowRate >= globalRate) {
					equipmentFailureRates1.add(efr);
				}
			}
			
		}
		
		XSSFWorkbook workbook = new XSSFWorkbook();
		XSSFSheet sheet;
		
		sheet = workbook.createSheet("Taux de pannes par equipement");
		/*--------0--------- Table Header rows ----------------------*/
		Row row = sheet.createRow(0);
		XSSFFont font = workbook.createFont();
		font.setBold(true);
		font.setFontHeight(16);
		
		XSSFColor color = new XSSFColor(new java.awt.Color(232, 232, 232), null);
		XSSFCellStyle cellStyle = workbook.createCellStyle();
		
		cellStyle.setFillForegroundColor(color);
		cellStyle.setFillPattern(FillPatternType.SOLID_FOREGROUND);
		cellStyle.setBorderBottom(BorderStyle.THIN);
		cellStyle.setBorderTop(BorderStyle.THIN);
		cellStyle.setBorderRight(BorderStyle.THIN);
		cellStyle.setBorderLeft(BorderStyle.THIN);
		cellStyle.setFont(font);
		cellStyle.setAlignment(HorizontalAlignment.CENTER);
		
		int index = 0;
		sheet.addMergedRegion(CellRangeAddress.valueOf("A1:E1"));
		
		createCell(sheet, row, index++, "Taux de pannes par equipement (>=" + globalRate + "%)", cellStyle);
		/*--------0--------- Table Header rows ----------------------*/
		row = sheet.createRow(1);
		font = workbook.createFont();
		font.setBold(true);
		font.setFontHeight(16);
		
		color = new XSSFColor(new java.awt.Color(232, 232, 232), null);
		cellStyle = workbook.createCellStyle();
		
		cellStyle.setFillForegroundColor(color);
		cellStyle.setFillPattern(FillPatternType.SOLID_FOREGROUND);
		cellStyle.setBorderBottom(BorderStyle.THIN);
		cellStyle.setBorderTop(BorderStyle.THIN);
		cellStyle.setBorderRight(BorderStyle.THIN);
		cellStyle.setBorderLeft(BorderStyle.THIN);
		cellStyle.setFont(font);
		cellStyle.setAlignment(HorizontalAlignment.CENTER);
		
		index = 0;
		createCell(sheet, row, index++, "Période:", cellStyle);
		createCell(sheet, row, index++, "Début:", cellStyle);
		createCell(sheet, row, index++, request.getParameter("from"), cellStyle);
		createCell(sheet, row, index++, "Fin:", cellStyle);
		createCell(sheet, row, index++, request.getParameter("to"), cellStyle);
		
		/*--------1--------- Table Header rows ----------------------*/
		row = sheet.createRow(2);
		font = workbook.createFont();
		font.setBold(true);
		font.setFontHeight(16);
		
		color = new XSSFColor(new java.awt.Color(244, 204, 204), null);
		cellStyle = workbook.createCellStyle();
		
		cellStyle.setFillForegroundColor(color);
		cellStyle.setFillPattern(FillPatternType.SOLID_FOREGROUND);
		cellStyle.setBorderBottom(BorderStyle.THIN);
		cellStyle.setBorderTop(BorderStyle.THIN);
		cellStyle.setBorderRight(BorderStyle.THIN);
		cellStyle.setBorderLeft(BorderStyle.THIN);
		cellStyle.setFont(font);
		
		index = 0;
		createCell(sheet, row, index++, "Nom équipement", cellStyle);
		createCell(sheet, row, index++, "Numéro de série", cellStyle);
		createCell(sheet, row, index++, "Nombre de pannes", cellStyle);
		createCell(sheet, row, index++, "Nombre de jours innactifs", cellStyle);
		createCell(sheet, row, index++, "Taux de panne", cellStyle);
		/*--------2--------- Data rows ----------------------*/
		int rowCount = 3;
		CellStyle style = workbook.createCellStyle();
		font = workbook.createFont();
		font.setFontHeight(14);
		style.setFont(font);
		style.setBorderBottom(BorderStyle.THIN);
		style.setBorderTop(BorderStyle.THIN);
		style.setBorderRight(BorderStyle.THIN);
		style.setBorderLeft(BorderStyle.THIN);
		
		//TODO check failure rate here
		for (EquipmentFailureRate item : equipmentFailureRates1) {
			row = sheet.createRow(rowCount++);
			int columnCount = 0;
			createCell(sheet, row, columnCount++, item.getName(), style);
			createCell(sheet, row, columnCount++, item.getSerial(), style);
			createCell(sheet, row, columnCount++, item.getCountMaintenance(), style);
			createCell(sheet, row, columnCount++, item.getInactiveDays(), style);
			createCell(sheet, row, columnCount++, new DecimalFormat("#.##").format(item.getRate()) + "%", style);
		}
		
		ServletOutputStream outputStream = response.getOutputStream();
		workbook.write(outputStream);
		workbook.close();
		outputStream.close();
	}
	
	@RequestMapping(method = RequestMethod.GET, value = "/rest/" + RestConstants.VERSION_1 + GmaoRest.GMAO_NAMESPACE
	        + "/maintenances/planning")
	public void exportMaintenancePlanning(HttpServletResponse response, HttpServletRequest request) throws IOException {
		response.setContentType("application/octet-stream");
		DateFormat dateFormatter = new SimpleDateFormat("yyyy-MM-dd_HH:mm:ss");
		String currentDateTime = dateFormatter.format(new Date());
		
		String headerKey = "Content-Disposition";
		String headerValue = "attachment; filename=Planning_annuel_des maintenances_" + currentDateTime + ".xlsx";
		response.setHeader(headerKey, headerValue);
		
		DateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd");
		DateFormat simpleDateFormat2 = new SimpleDateFormat("dd-MM-yyyy");
		List<Maintenance> maintenances = new ArrayList<Maintenance>();
		Date toDate = null;
		Date fromDate = null;
		String periodicity = "Semaine";
		SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");
		
		String fromDateString = "";
		String toDateString = "";
		
		try {
			if (request.getParameter("to") != null) {
				toDate = simpleDateFormat.parse(request.getParameter("to"));
				toDateString = formatter.format(toDate);
			}
			if (request.getParameter("from") != null) {
				fromDate = simpleDateFormat.parse(request.getParameter("from"));
				fromDateString = formatter.format(fromDate);
				;
			}
		}
		catch (ParseException ex) {
			Logger.getLogger(MaintenanceController.class.getName()).log(Level.SEVERE, null, ex);
		}
		
		DbSession session = Context.getService(GmaoService.class).getSession();
		
		List<MaintenanceEvent> maintenanceEvents = gmaoService.getAll(MaintenanceEvent.class);
		
		XSSFWorkbook workbook = new XSSFWorkbook();
		XSSFSheet sheet;
		
		sheet = workbook.createSheet("Planning des opérations d'entretien");
		/*--------0--------- Table Header rows ----------------------*/
		Row row = sheet.createRow(0);
		XSSFFont font = workbook.createFont();
		font.setBold(true);
		font.setFontHeight(16);
		
		XSSFColor color = new XSSFColor(new java.awt.Color(232, 232, 232), null);
		XSSFCellStyle cellStyle = workbook.createCellStyle();
		
		cellStyle.setFillForegroundColor(color);
		cellStyle.setFillPattern(FillPatternType.SOLID_FOREGROUND);
		cellStyle.setBorderBottom(BorderStyle.THIN);
		cellStyle.setBorderTop(BorderStyle.THIN);
		cellStyle.setBorderRight(BorderStyle.THIN);
		cellStyle.setBorderLeft(BorderStyle.THIN);
		cellStyle.setFont(font);
		cellStyle.setAlignment(HorizontalAlignment.CENTER);
		
		int index = 0;
		sheet.addMergedRegion(CellRangeAddress.valueOf("A1:BA1"));
		
		createCell(sheet, row, index++, "PLANNING DES OPERATIONS D'ENTRETIEN", cellStyle);
		
		/*--------1--------- Table Header rows ----------------------*/
		row = sheet.createRow(1);
		font = workbook.createFont();
		font.setBold(true);
		font.setFontHeight(16);
		
		color = new XSSFColor(new java.awt.Color(232, 232, 232), null);
		cellStyle = workbook.createCellStyle();
		
		cellStyle.setFillForegroundColor(color);
		cellStyle.setFillPattern(FillPatternType.SOLID_FOREGROUND);
		cellStyle.setBorderBottom(BorderStyle.THIN);
		cellStyle.setBorderTop(BorderStyle.THIN);
		cellStyle.setBorderRight(BorderStyle.THIN);
		cellStyle.setBorderLeft(BorderStyle.THIN);
		cellStyle.setFont(font);
		cellStyle.setAlignment(HorizontalAlignment.CENTER);
		
		index = 0;
		
		sheet.addMergedRegion(CellRangeAddress.valueOf("A2:BA2"));
		
		createCell(sheet, row, 0, "ANNUEL", cellStyle);
		
		/*--------1--------- Table Header rows ----------------------*/
		/*--------2--------- Table Header rows ----------------------*/
		row = sheet.createRow(2);
		font = workbook.createFont();
		font.setBold(true);
		font.setFontHeight(16);
		
		color = new XSSFColor(new java.awt.Color(232, 232, 232), null);
		cellStyle = workbook.createCellStyle();
		
		cellStyle.setFillForegroundColor(color);
		cellStyle.setFillPattern(FillPatternType.SOLID_FOREGROUND);
		cellStyle.setBorderBottom(BorderStyle.THIN);
		cellStyle.setBorderTop(BorderStyle.THIN);
		cellStyle.setBorderRight(BorderStyle.THIN);
		cellStyle.setBorderLeft(BorderStyle.THIN);
		cellStyle.setFont(font);
		
		index = 0;
		createCell(sheet, row, 0, "Equipement", cellStyle);
		createCell(sheet, row, 1, "Tâche", cellStyle);
		for (int k = 1; k < 54; k++) {
			createCell(sheet, row, k + 1, k, cellStyle);
		}
		
		/*--------2--------- Table Header rows ----------------------*/
		/*--------3--------- Data rows ----------------------*/
		int rowCount = 3;
		CellStyle style = workbook.createCellStyle();
		font = workbook.createFont();
		font.setFontHeight(14);
		style.setFont(font);
		style.setBorderBottom(BorderStyle.THIN);
		style.setBorderTop(BorderStyle.THIN);
		style.setBorderRight(BorderStyle.THIN);
		style.setBorderLeft(BorderStyle.THIN);
		
		for (MaintenanceEvent e : maintenanceEvents) {
			row = sheet.createRow(rowCount++);
			createCell(sheet, row, 0, e.getEquipment().getName(), cellStyle);
			createCell(sheet, row, 1, e.getName(), cellStyle);
			
			for (int columnCount = 1; columnCount < 54; columnCount++) {
				createCell(sheet, row, columnCount + 1, "", cellStyle);
			}
			
			Long repeatAfter = e.getIntervalInDays();
			
			final Calendar current = Calendar.getInstance();
			current.setTime(e.getStartdate());
			current.add(Calendar.DATE, repeatAfter.intValue());
			
			
			while (current.getTime().before(e.getEnddate())) {
				createCell(sheet, row, current.get(Calendar.WEEK_OF_YEAR), "x", cellStyle);
				current.add(Calendar.DATE, repeatAfter.intValue());
			}
		}
		
		ServletOutputStream outputStream = response.getOutputStream();
		workbook.write(outputStream);
		workbook.close();
		outputStream.close();
	}
	
	@RequestMapping(method = RequestMethod.GET, value = "/rest/" + RestConstants.VERSION_1 + GmaoRest.GMAO_NAMESPACE
	        + "/maintenances/count")
	public void doCount(HttpServletResponse response, HttpServletRequest request) throws IOException {
		Long count = Context.getService(GmaoService.class).doCount(Maintenance.class);
		String content = "{\"count\":" + count + "}";
		response.setContentType("application/json");
		response.setContentLength(content.length());
		response.getWriter().write(content);
	}
	
	private void createCell(XSSFSheet sheet, Row row, int columnCount, Object value, CellStyle style) {
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
}
