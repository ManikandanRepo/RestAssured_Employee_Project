package com.employeeapi.base;


import org.apache.logging.log4j.Logger;
import org.apache.logging.log4j.LogManager;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.BeforeSuite;

import io.restassured.RestAssured;
import io.restassured.response.Response;
import io.restassured.specification.RequestSpecification;

public class TestBase {
	
	public static RequestSpecification httpRequest;
	public static Response response;
	
	public String empID = "2";
	
	public static Logger logger = LogManager.getLogger("REST API Automation logs");
	
	@BeforeSuite
	public void setup() {
	
		RestAssured.baseURI = "https://dummy.restapiexample.com/api/v1";
		
	}

}
