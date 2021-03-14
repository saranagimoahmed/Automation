package Testing.Automation;

import java.lang.reflect.Method;
import java.util.concurrent.TimeUnit;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.firefox.FirefoxDriver;

import org.testng.ITestResult;
import org.testng.annotations.AfterClass;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import com.relevantcodes.extentreports.ExtentReports;
import com.relevantcodes.extentreports.ExtentTest;
import com.relevantcodes.extentreports.LogStatus;

import io.github.bonigarcia.wdm.WebDriverManager;

public class TestPage {
	private WebDriver driver;
	ExtentReports report;
	ExtentTest test;

	@BeforeClass
	public void setup() {
		// use FF Driver
		WebDriverManager.firefoxdriver().setup();
		driver = new FirefoxDriver();
		driver.manage().timeouts().implicitlyWait(10, TimeUnit.SECONDS);
		report = new ExtentReports(System.getProperty("user.dir") + "//TestReport.html"); //Report for status 
	}

	@BeforeMethod
	public void beforeEachTest(Method method) {
		test = report.startTest(method.getName());
		test.log(LogStatus.INFO, "Test Case Started");
	}

	@Test
	public void SelectProduct() throws Exception {
		// Create object of HomePage Class
		Page home = new Page(driver);
		home.Gotoshop();
		home.SelectProduct();
		home.SelectColor();
		home.SelectQuantity();
		home.ADDTOCART();

	}

	@AfterMethod // REPORT Function 
	public void afterEachTest(ITestResult iTestResult) {
		if (iTestResult.getStatus() == ITestResult.SUCCESS) {
			test.log(LogStatus.PASS, "Test Case Passed");
		} else if (iTestResult.getStatus() == ITestResult.SKIP) {
			test.log(LogStatus.SKIP, "Test Case Skipped");
		} else if (iTestResult.getStatus() == ITestResult.FAILURE) {
			;

			test.log(LogStatus.FAIL, "Test Case Failed");
		}
	}

	@AfterClass

	public void close() {
		driver.close();
		report.endTest(test);
		report.flush();
	}

}
