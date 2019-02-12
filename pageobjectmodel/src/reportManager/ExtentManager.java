package reportManager;

import java.text.SimpleDateFormat;
import java.util.Date;

import com.aventstack.extentreports.ExtentReporter;
import com.aventstack.extentreports.ExtentReports;
import com.aventstack.extentreports.reporter.ExtentHtmlReporter;
import com.aventstack.extentreports.reporter.configuration.ChartLocation;

import configuration.BaseClass;


public class ExtentManager {
	private static ExtentReports extent;
	  private static ExtentHtmlReporter htmlReporter;
	  
	  public ExtentManager() {}
	  
	  private static String str_todaysDateTimeStamp = new SimpleDateFormat("dd-MMM-YYYY HH-mm-ss").format(new Date());
	  
	  public static ExtentReports getInstance() {
	    if (extent == null) {
	      extent = new ExtentReports();
	      
	      extent.attachReporter(new ExtentReporter[] { getHtmlReporter() });
	      extent.setSystemInfo("Operating System", BaseClass.pro.getProperty("OS"));
	      extent.setSystemInfo("Build Name", BaseClass.pro.getProperty("BuildName"));
	      extent.setSystemInfo("Build Number", BaseClass.pro.getProperty("BuildNumber"));
	      extent.setSystemInfo("Database Name", BaseClass.pro.getProperty("DatabaseName"));
	    
	    }
	    
	    return extent;
	  }
	  

	  private static ExtentHtmlReporter getHtmlReporter()
	  {
	    String newReportPath = BaseClass.createNewDirectory(BaseClass.parentResultDirectory + "Reports_");
	    String HTMLReportPath = newReportPath + "Report_" + str_todaysDateTimeStamp + ".html";
	    
	    htmlReporter = new ExtentHtmlReporter(HTMLReportPath);
	    

	    htmlReporter.config().setTestViewChartLocation(ChartLocation.TOP);
	    htmlReporter.config().setChartVisibilityOnOpen(true);
	    htmlReporter.config().setTheme(com.aventstack.extentreports.reporter.configuration.Theme.STANDARD);
	    htmlReporter.config().setDocumentTitle(BaseClass.pro.getProperty("ReportTitle"));
	    htmlReporter.config().setEncoding("utf-8");
	    htmlReporter.config().setReportName(BaseClass.pro.getProperty("ReportTitle"));
	    

	    return htmlReporter;
	  }

}
