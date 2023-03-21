package com.employeeapi.testcases;

import org.testng.Assert;
import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

import com.aventstack.extentreports.Status;
import com.employeeapi.base.TestBase;

import io.restassured.RestAssured;
import com.employeeapi.utilities.Listeners;

public class TC001_Get_All_Employees extends TestBase{
	
	
	@BeforeClass
	public void getAllemployees() throws InterruptedException {
		
		logger.info("*****Started TC001_Get_All_Employees**********");
		
		httpRequest = RestAssured.given();
		response = httpRequest.get("employees");
		Thread.sleep(3000);
	}
	
	@Test(priority = 0)
	public void checkStatusCode() throws InterruptedException {
		logger.info("*********Checking status code*********");
		int statusCode = response.getStatusCode();
		int retryCount = 0;
		while (statusCode!=200 && retryCount<5) {
			retryCount++;
			logger.info("Retrying-->" + retryCount);
			response = httpRequest.get("employees");
			statusCode = response.getStatusCode();
			Thread.sleep(3000);
		}
		logger.info("The status code is -->"+ statusCode);
		Listeners.getTest().log(Status.INFO, "The status code is -->"+ statusCode);
		Assert.assertEquals(statusCode,200);
		
	}
	
	
	@Test(priority = 1)
	public void checkResponseTime() {
		
		logger.info("************Checking response time**********");
		
		double responseTime = response.getTime();
		
		logger.info("The response time is -->"+ responseTime);
		Listeners.getTest().log(Status.INFO, "The response time is -->"+ responseTime);
		if (responseTime>5000) {
			logger.warn("The response time is more than 5 seconds");
			Assert.assertTrue(responseTime<5000);
		}
	}
	
	@Test(priority = 2)
	public void checkStatusLine() {
		logger.info("**********Checking the status line*********");
		String statusLine = response.getStatusLine();
		logger.info("Status line is-->"+statusLine);
		Listeners.getTest().log(Status.INFO, "Status line is-->"+statusLine);
		Assert.assertEquals(statusLine, "HTTP/1.1 200 OK");
	}
	
	@Test(priority = 3)
	public void checkResponseBody() {
		
		logger.info("*********Checking response body*********");
		
		Assert.assertTrue(response.getBody().asString()!=null);
	}
	
	@Test(priority = 4)
	public void checkContentType() {
		logger.info("*******Checking content type from response header");
		String contentType = response.header("Content-Type");
		logger.info("Content type is-->" + contentType);
		Listeners.getTest().log(Status.INFO, "Content type is-->" + contentType);
		Assert.assertEquals(contentType,"application/json");
	}
	
	@Test(priority = 5, enabled = false)
	public void checkContentLength() {
		logger.info("********Check the content length*********");
		//System.out.println(response.getHeaders());
		String contentLength= response.header("Content-Length");
		Listeners.getTest().log(Status.INFO,"Content length is-->" + contentLength);
		logger.info("Content length is-->" + contentLength);
		if(contentLength.isBlank() || contentLength.isEmpty() || contentLength.equals("null")) {
			logger.error("Content Length is not retrieved from header");
		}
		else {
			Assert.assertTrue(Integer.parseInt(contentLength)>100);
		}
	}
	
	@AfterClass
	public void tearDown() {
		logger.info("******* Test case execution for TC001_Get_All_Employees is completed*************");
	}
	
	
	

}
