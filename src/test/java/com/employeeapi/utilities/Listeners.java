package com.employeeapi.utilities;

import java.util.HashMap;
import java.util.Map;

import org.testng.ITestContext;
import org.testng.ITestResult;
import org.testng.TestListenerAdapter;

import com.aventstack.extentreports.ExtentReports;
import com.aventstack.extentreports.ExtentTest;
import com.aventstack.extentreports.Status;
import com.aventstack.extentreports.reporter.ExtentSparkReporter;
import com.aventstack.extentreports.reporter.configuration.Theme;

public class Listeners extends TestListenerAdapter{
	
	public static ExtentReports extent;
	public static ExtentSparkReporter htmlReporter;
	public static ExtentTest test;
	public static Map<Integer, ExtentTest> extentTestMap = new HashMap<Integer, ExtentTest>();
	
	@Override
	public void onStart(ITestContext testContext) {
		htmlReporter = new ExtentSparkReporter(System.getProperty("user.dir")+"/test-output/ExtentReport.html");
		
		htmlReporter.config().setDocumentTitle("Automation report");
		htmlReporter.config().setReportName("REST API Automation report");
		
		htmlReporter.config().setTheme(Theme.DARK);
		
		extent = new ExtentReports();
		
		extent.attachReporter(htmlReporter);
		extent.setSystemInfo("OS","Windows");
		extent.setSystemInfo("Environment","Test-QA");
		extent.setSystemInfo("User","Manikandan");
	}
	
	@Override
	public void onTestStart(ITestResult result) {
		test = extent.createTest(result.getName());
		extentTestMap.put((int)(long)(Thread.currentThread().getId()), test);
		getTest().log(Status.INFO,"Test class STARTED is " + result.getTestClass().getName());
		getTest().log(Status.INFO,"Test case STARTED is " + result.getName());
	}
	
	@Override
	public void onTestSuccess(ITestResult result) {
		getTest().log(Status.PASS,"Test case PASSED is " + result.getName());
	}
	
	@Override
	public void onTestFailure(ITestResult result) {
		getTest().log(Status.FAIL,"Test case FAILED is " + result.getName());
	}
	
	@Override
	public void onTestSkipped(ITestResult result) {
		getTest().log(Status.SKIP,"Test case SKIPPED is " + result.getName());
	}
	
	@Override
	public void onFinish(ITestContext testContext) {
		extent.flush();
	}
	
	public static ExtentTest getTest() {
		return (ExtentTest) extentTestMap.get((int) (long) (Thread.currentThread().getId())); 
	}
	

}
