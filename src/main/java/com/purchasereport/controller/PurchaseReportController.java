package com.purchasereport.controller;

import java.io.FileNotFoundException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.purchasereport.model.PurchaseReport;
import com.purchasereport.model.ApiResponse;
import com.purchasereport.service.PurchaseService;

import net.sf.jasperreports.engine.JRException;
@RestController
@RequestMapping("/api")
public class PurchaseReportController {
	@Autowired
	PurchaseService purchaseService;
	
	@PostMapping("/purchase-report")
	public ResponseEntity<ApiResponse> saveEtimsData(@RequestBody PurchaseReport purchaseReport) {
		ApiResponse response = new ApiResponse("Invoice printed successfully", 200);
		
		try {
			purchaseService.exportReport("pdf", purchaseReport);
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (JRException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return ResponseEntity.ok(response);
	}
}
