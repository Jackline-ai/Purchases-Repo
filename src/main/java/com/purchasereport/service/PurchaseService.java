package com.purchasereport.service;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import org.springframework.stereotype.Service;
import org.springframework.util.ResourceUtils;

import com.purchasereport.model.PurchaseReport;
import com.purchasereport.utils.Utilities;

import net.sf.jasperreports.engine.JRException;
import net.sf.jasperreports.engine.JasperCompileManager;
import net.sf.jasperreports.engine.JasperExportManager;
import net.sf.jasperreports.engine.JasperFillManager;
import net.sf.jasperreports.engine.JasperPrint;
import net.sf.jasperreports.engine.JasperReport;
import net.sf.jasperreports.engine.data.JRBeanCollectionDataSource;

@Service
public class  PurchaseService{
    	
      
	public String exportReport(String reportFormat,PurchaseReport purchaseReport) throws FileNotFoundException, JRException {

		String path = "/home/jackline/Desktop/Reports/";

		// Load the report template

		List<PurchaseReport> purchaseReports = new ArrayList<PurchaseReport>();
		purchaseReports.add(purchaseReport);

		File file = ResourceUtils.getFile("classpath:PurchaseReport.jrxml");
		JasperReport jasperReport = JasperCompileManager.compileReport(file.getAbsolutePath());
		JRBeanCollectionDataSource dataSource = new JRBeanCollectionDataSource(purchaseReports);
		Map<String, Object> parameters = new HashMap<>();
		
		JRBeanCollectionDataSource dataSourceHeader = new JRBeanCollectionDataSource(purchaseReports);
		parameters.put("HEADER_DATASOURCE", dataSourceHeader);
		
		JRBeanCollectionDataSource dataSourcePurchaseItems = new JRBeanCollectionDataSource(purchaseReports);
		parameters.put("PURCHASE_ITEMS_DATASOURCE", dataSourcePurchaseItems);
		
		JRBeanCollectionDataSource dataSourcePurchaseDetails = new JRBeanCollectionDataSource(purchaseReports);
		parameters.put("PURCHASE_DETAILS_DATASOURCE", dataSourcePurchaseDetails);
		
		parameters.put("createdBy", "Jackline");
		JasperPrint jasperPrint = JasperFillManager.fillReport(jasperReport, parameters, dataSource);
		if (reportFormat.equalsIgnoreCase("xml")) {
			return JasperExportManager.exportReportToXml(jasperPrint);

		}

		else if (reportFormat.equalsIgnoreCase("pdf")) {
			JasperExportManager.exportReportToPdfFile(jasperPrint, path + Utilities.getCurrentDateTime());

		}

		return "report generated in path: " + path;
	}


}


