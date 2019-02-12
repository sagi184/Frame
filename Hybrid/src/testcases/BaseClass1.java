package testcases;

import java.io.File;

import org.apache.commons.io.FileUtils;
import org.apache.poi.ss.usermodel.CellValue;
import org.apache.poi.ss.usermodel.FormulaEvaluator;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.testng.Assert;
import org.testng.ITestResult;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.AfterTest;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

import com.relevantcodes.extentreports.ExtentReports;
import com.relevantcodes.extentreports.ExtentTest;
import com.relevantcodes.extentreports.LogStatus;

import operation.UIoperation;
import readExcel.ReadExcel;

public class BaseClass1 {
	public static com.relevantcodes.extentreports.ExtentReports extent;
	public static ExtentTest test;
	WebDriver driver = null;
	public String[] Strarray;
	int sheetIndex = 0;
	int index = 0;
	public static String stName;
	public static String objName;

	@BeforeTest
	public void startreport() {
		extent = new ExtentReports(System.getProperty("user.dir") + "\\Reports\\advancereport.html",
				Boolean.valueOf(true));
		extent.addSystemInfo("HostName", "Sagar");
	}

	@Test(dataProvider = "DP1", priority = 1)
	public void readTestCasesToExecute(String Testname, String Enabled) throws Exception {
		this.getDataFromDataprovider1();
		if (Testname != null && Enabled.contains("Y")) {
			this.readDataFromSheet(Testname);
		}

	}

	@AfterTest
	public void endReport() {
		extent.flush();
		// SendEmail.SendMailWithAttachment();
		extent.close();
	}

	@AfterMethod
	public void saveScreenshot(ITestResult result) throws Exception {
		if (2 == result.getStatus()) {
			captureScreenshot(this.driver, stName);
		}

	}

	private void readDataFromSheet(String sheetName) throws Exception {
		stName = sheetName;

		try {
			ReadExcel e = new ReadExcel();
			Sheet Sheet1 = e.readExcel(System.getProperty("user.dir") + "\\", "TestCaseSteps.xlsx", sheetName);
			++this.sheetIndex;
			int rowCount = Sheet1.getLastRowNum() - Sheet1.getFirstRowNum();

			for (int i = 0; i < rowCount; ++i) {
				Row row = Sheet1.getRow(i + 1);
				String testcaseName = row.getCell(0).getStringCellValue();
				String keyword = row.getCell(1).getStringCellValue();
				String objectName = row.getCell(2).getStringCellValue();
				String objectType = row.getCell(3).getStringCellValue();
				String ObjValue = row.getCell(4).getStringCellValue();
				String value = row.getCell(5).getStringCellValue();
				int cellType = row.getCell(5).getCellType();
				switch (cellType) {
				case 0:
					value = String.valueOf(row.getCell(5).getNumericCellValue());
					break;
				case 1:
					value = String.valueOf(row.getCell(5).getStringCellValue());
					break;
				case 2:
					FormulaEvaluator evaluator = e.wb.getCreationHelper().createFormulaEvaluator();
					CellValue cellValue = evaluator.evaluate(row.getCell(5));
					switch (cellValue.getCellType()) {
					case 0:
						value = String.valueOf(Long.toString((long) cellValue.getNumberValue()));
						break;
					case 1:
						value = cellValue.getStringValue();
					}
				case 3:
					break;
				default:
					value = row.getCell(5).getStringCellValue();
					break;
				}

				this.transaction(testcaseName, keyword, objectName, objectType, value, ObjValue);
			}

			System.out.println("No Test Step for execution");
			test.log(LogStatus.INFO, "No Test Step for execution");
			extent.endTest(test);
			this.driver.quit();
		} catch (Exception arg15) {
			test.log(LogStatus.ERROR, "Exception while performing step: " + objName + " - " + arg15.getMessage());
			System.out.println("Exception while performing step: " + arg15.getMessage());
			Assert.fail();
		}

	}

	public void transaction(String testcaseName, String keyword, String objectName, String objectType, String value,
			String ObjValue) throws Exception {
		try {
			objName = objectName;
			if (testcaseName != null && testcaseName.length() != 0) {
				System.setProperty("webdriver.chrome.driver",
						System.getProperty("user.dir") + "\\" + "chromedriver.exe");
				test = extent.startTest(testcaseName);
				this.driver = new ChromeDriver();
				this.driver.manage().window().maximize();
			}

			UIoperation e = new UIoperation(this.driver);
			e.perform(ObjValue, keyword, objectName, objectType, value);
		} catch (AssertionError arg7) {
			System.out.println("Hi Sagar Exception in test case execution: " + arg7.getMessage());
			Assert.fail();
		}

	}

	@DataProvider(name = "DP1")
	public Object[][] getDataFromDataprovider1() throws Exception {
		Object[][] object1 = null;
		ReadExcel file = new ReadExcel();
		Sheet Sheet1 = file.readExcel(System.getProperty("user.dir") + "\\", "TestCaseSteps.xlsx", "ExecuteState");
		int rowCount = Sheet1.getLastRowNum() - Sheet1.getFirstRowNum();
		this.Strarray = new String[rowCount];
		object1 = new Object[rowCount][2];

		for (int i = 0; i < rowCount; ++i) {
			Row row = Sheet1.getRow(i + 1);

			for (int j = 0; j < row.getLastCellNum(); ++j) {
				object1[i][j] = row.getCell(j).getStringCellValue();
			}
		}

		System.out.println("");
		return object1;
	}

	public static void captureScreenshot(WebDriver driver, String screenshotname) {
		try {
			TakesScreenshot e = (TakesScreenshot) driver;
			File source = e.getScreenshotAs(OutputType.FILE);
			String imageName = screenshotname + ".png";
			String scrnstpath = System.getProperty("user.dir") + "/" + "Screenshots" + "/" + imageName;
			FileUtils.copyFile(source, new File(scrnstpath));
			System.out.println("Screenshot taken");
			test.log(LogStatus.INFO, "Screenshot captured");
			test.log(LogStatus.FAIL, "Test case is Failed");
			test.log(LogStatus.FAIL, "Screenshot below: " + test.addScreenCapture(scrnstpath));
			driver.quit();
			extent.endTest(test);
		} catch (Exception arg5) {
			System.out.println("Exception while taking screenshot " + arg5.getMessage());
		}

	}
}
