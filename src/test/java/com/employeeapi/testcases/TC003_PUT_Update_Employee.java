package com.employeeapi.testcases;

import java.util.HashMap;

import org.junit.Assert;
import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

import com.aventstack.extentreports.Status;
import com.employeeapi.base.TestBase;
import com.employeeapi.utilities.Listeners;

import io.restassured.RestAssured;

public class TC003_PUT_Update_Employee extends TestBase {

	public HashMap<String, String> reqBody = new HashMap<>();

	@BeforeClass
	public void updateEmployee() throws InterruptedException {
		logger.info("*****Started TC003_PUT_Update_Employee**********");
		httpRequest = RestAssured.given();

		reqBody.put("name", "Manikandan");
		reqBody.put("salary", "10000");
		reqBody.put("age", "32");

		httpRequest.body(reqBody.toString());

		response = httpRequest.put("/update/2");
		Thread.sleep(3000);
	}

	@Test(priority = 0)
	public void checkStatusCode() throws InterruptedException {
		int statusCode = response.getStatusCode();

		int retryCount = 0;
		while (statusCode != 200 && retryCount < 5) {
			retryCount++;
			logger.info("Retrying-->" + retryCount);
			response = httpRequest.put("/update/2");
			statusCode = response.getStatusCode();
			Thread.sleep(3000);
		}
		logger.info("Status code is-->" + statusCode);
		Listeners.getTest().log(Status.INFO, "Status code is-->" + statusCode);

		if (statusCode != 200) {
			logger.error("Status code is not 200!");
			Listeners.getTest().log(Status.FAIL, "Status code is not 200!");

			Assert.assertTrue(statusCode == 200);
		}
	}

	@Test(priority = 3)
	public void checkResponseBody() {

		logger.info("*********Checking response body*********");
		logger.info(response.getBody().asString());
		Listeners.getTest().log(Status.INFO, "The response body is -->" + response.getBody().asString());
		Assert.assertTrue(response.getBody().asString() != null);
	}
	
	@Test(priority = 4)
	public void checkResponseMessage() {
		logger.info("*********Checking response body message*********");
		logger.info("Response body message is -->"+response.jsonPath(). get("message").toString());
		Listeners.getTest().log(Status.INFO, "Response body message is -->"+response.jsonPath().get("message").toString());
	}

	@AfterClass
	public void tearDown() {
		logger.info("************Test case TC003_PUT_Update_Employee is completed************");
	}

}
