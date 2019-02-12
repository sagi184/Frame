package configuration;

import atu.testrecorder.ATUTestRecorder;
import atu.testrecorder.exceptions.ATUTestRecorderException;
import excelConfiguration.ExcelReader;
import excelConfiguration.ExcelWriter;
import reportManager.ExtentManager;

import com.aventstack.extentreports.ExtentReports;
import com.aventstack.extentreports.ExtentTest;
import com.aventstack.extentreports.MediaEntityBuilder;
import com.aventstack.extentreports.Status;

import java.awt.AWTException;
import java.awt.Robot;
import java.awt.Toolkit;
import java.awt.datatransfer.Clipboard;
import java.awt.datatransfer.DataFlavor;
import java.awt.datatransfer.Transferable;
import java.awt.datatransfer.UnsupportedFlavorException;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.PrintStream;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.Statement;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.InputMismatchException;
import java.util.List;
import java.util.Properties;
import java.util.concurrent.TimeUnit;
import org.apache.commons.io.FileUtils;
import org.apache.log4j.Logger;
import org.apache.log4j.PropertyConfigurator;
import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.NoSuchElementException;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebDriver.Navigation;
import org.openqa.selenium.WebDriver.Options;
import org.openqa.selenium.WebDriver.Timeouts;
import org.openqa.selenium.WebDriver.Window;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.edge.EdgeDriver;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.firefox.FirefoxProfile;
import org.openqa.selenium.ie.InternetExplorerDriver;
import org.openqa.selenium.interactions.Action;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.remote.DesiredCapabilities;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.Select;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.Assert;
import org.testng.ITestResult;
import org.testng.annotations.AfterClass;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.AfterSuite;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.BeforeSuite;







public class BaseClass
{
  private static WebDriver driver;
  private static WebDriverWait wait;
  public static ExtentTest etest;
  private static Select select;
  private static WebElement webEle;
  protected static Logger log = Logger.getLogger("devpinoyLogger");
  

  private static ExtentReports extentReports;
  

  private static FileInputStream fis;
  
  private static File file;
  
  public static Properties pro;
  
  public static String parentResultDirectory;
  
  private static ATUTestRecorder recorder;
  
  private static final String str_todaysDateStamp = new SimpleDateFormat("dd-MMM-YYYY").format(new Date());
  private static final String str_todaysDateTimeStamp = new SimpleDateFormat("dd-MMM-YYYY HH-mm-ss").format(new Date());
  

  public BaseClass() {}
  
  static final void ConfigRead(String propertiesFileName)
    throws Exception
  {
    file = new File(System.getProperty("user.dir") +"\\src\\properties\\"+ propertiesFileName + ".properties");
    
    System.setProperty("current.date", str_todaysDateStamp);
    System.setProperty("current.date.time", str_todaysDateTimeStamp);
    

    fis = new FileInputStream(file);
 
    pro = new Properties();
    

    pro.load(fis);
    
    if (propertiesFileName.equalsIgnoreCase("log4j")) {
      PropertyConfigurator.configure(pro);
    }
  }
  
  @BeforeSuite
  protected static void setup()
  {
    try
    {
      ConfigRead("log4j");
      System.out.println("log4j.properties file loaded Successfully.");
      log.debug("log4j.properties file loaded Successfully.");
    } catch (Exception e) {
      System.err.println("Error while reading log4j.properties file.");
      e.printStackTrace();
      log.error("ERROR : Error while reading log4j.properties file.", e);
    }
    


    try
    {
      ConfigRead("config");
      System.out.println("config.properties file loaded Successfully.");
      log.debug("config.properties file loaded Successfully.");
    }
    catch (Exception e) {
      System.err.println("Error while reading config.properties file.");
      e.printStackTrace();
      log.error("ERROR : Error while reading config.properties file.", e);
    }
  
   /* try
    {
      String VideoOutputPath = pro.getProperty("ScriptVideos") + "TestVdo_" + str_todaysDateTimeStamp;
      

      recorder = new ATUTestRecorder(VideoOutputPath, Boolean.valueOf(false));
      

      recorder.start();
      log.debug("Video Recorder Started Successfully.");
    }
    catch (Exception e) {
      e.printStackTrace();
      log.error("ERROR :  Failed to start video recorder.");
    }
    */


    parentResultDirectory = createNewDirectory(pro.getProperty("ResultPath") + "Results_");
    

    setupReport();
    


    BrowserSetup(pro.getProperty("testBrowser"));
  }

  public static void readExcelSheet(String sheetName)
  {
    try
    {
      ExcelReader.setExcelFile(pro.getProperty("TestDataExcelFilePath"), sheetName);
      log.debug("Reading excel file from : " + pro.getProperty("TestDataExcelFilePath") + ", containing : " + 
        sheetName + " Sheet");
      etest.info("Reading excel file from : " + pro.getProperty("TestDataExcelFilePath") + ", containing : " + 
        sheetName + " Sheet");
    }
    catch (Exception e)
    {
      e.printStackTrace();
      log.error("Error while reading excel file");
    }
  }

  public static String createNewDirectory(String path)
  {
    try
    {
      file = new File(path + str_todaysDateStamp);
      
      if (!file.exists())
      {
        file.mkdir();
      }
      


      return file.getAbsolutePath() + "\\";
    }
    catch (Exception e) {
      System.out.println("failed to create New Directoty.");
      e.printStackTrace(); }
    return null;
  }
 
  static final void setupReport()
  {
    try
    {
      extentReports = ExtentManager.getInstance();
      System.out.println("Extent Report Instance created ");
      log.debug("Extent Report Instance created ");
    } catch (Exception e) {
      System.err.println("Failed to instantiate extent report");
      e.printStackTrace();
      log.error("ERROR :  Failed to instantiate extent report", e);
    }
  }
 
  public static void startReport(String testName, String testDescription, String testCategory, String authorName)
  {
    etest = 
      extentReports.createTest(testName, testDescription).assignCategory(new String[] { testCategory }).assignAuthor(new String[] { authorName });
    
    System.err.println("");
    System.err.println("");
    System.err.println("*****************************************************************");
    System.err.println("Test started is : " + testName);
    System.err.println("Test description : " + testDescription);
    System.err.println("*****************************************************************");
    
    log.info("");
    log.info("");
    log.info("*****************************************************************");
    log.info("Test started is : " + testName);
    log.info("Test description : " + testDescription);
    log.info("*****************************************************************");
    


    etest.info("Test is running in : " + pro.getProperty("testBrowser") + " browser.");
  }
  

  static final void BrowserSetup(String browser)
  {
    String str;
   

    switch ((str = browser.toLowerCase()).hashCode()) {
    case -1361128838:  if (str.equals("chrome")) {
    	try {
            System.setProperty("webdriver.chrome.driver", pro.getProperty("ChromeDriverPath"));
            
            driver = new ChromeDriver();
            System.out.println("Chrome browser launched successfully");
            log.debug("Chrome browser launched successfully");
          }
          catch (IllegalStateException e) {
            System.err.println("ERROR :  Error in chrome driver");
            e.printStackTrace();
            log.error("ERROR :  Error in chrome driver", e);
          }break;
    }
     case -849452327: 
    	 {if (str.equals("firefox"))  try
         {
             System.setProperty("webdriver.gecko.driver", pro.getProperty("GecoDriverPath"));
             FirefoxProfile ffprofile = new FirefoxProfile();
             ffprofile.setAcceptUntrustedCertificates(true);
             ffprofile.setAssumeUntrustedCertificateIssuer(false);
             driver = new FirefoxDriver();
             System.out.println("Firefox browser launched successfully");
             log.debug("Firefox browser launched successfully");
           } catch (IllegalStateException e) {
             System.err.println("ERROR :  Error in Firefox driver");
             e.printStackTrace();
             log.error("ERROR :  Error in Firefox driver", e);
           }
    	  break; 
    	 }
    	 case 3356:  if (str.equals("ie")) {
    		 try
    	        {
    	          System.setProperty("webdriver.ie.driver", pro.getProperty("IEDriverPath"));
    	          DesiredCapabilities capabilities = DesiredCapabilities.internetExplorer();
    	          capabilities.setCapability("ignoreProtectedModeSettings", 
    	            true);
    	          

    	          driver = new InternetExplorerDriver();
    	          System.out.println("IE browser launched successfully");
    	          log.debug("IE browser launched successfully");
    	        } catch (IllegalStateException e) {
    	          System.err.println("ERROR :  Error in IE driver");
    	          e.printStackTrace();
    	          log.error("ERROR :  Error in IE driver", e);
    	        }
    	 } break;
    	 case 3108285:  if (!str.equals("edge")) {
    	 }   
      else
      {
        try {
          System.setProperty("webdriver.edge.driver", pro.getProperty("EdgeDriverPath"));
          driver = new EdgeDriver();
          log.debug("Edge browser launched successfully");
        } catch (IllegalStateException e) {
          System.err.println("ERROR :  Error in Edge driver");
          e.printStackTrace();
          log.error("ERROR :  Error in Edge driver", e);
        }
      }
      break;
    }
   /* label421:
    System.err.println("ERROR : " + browser + ":- is a invalid User Input. Please specify valid browser name in config file.");
    log.error("ERROR :  Failed to launch unknown browser: " + browser);
    throw new InputMismatchException(browser + " - Invalid User Input. Please specify valid browser name in config file.");*/
    

    driver.manage().window().maximize();
    System.out.println("Browser window maximized");
    log.debug("Browser window maximized");
    
    driver.manage().deleteAllCookies();
    log.debug("Cookies deleted from browser");
  }
  
  public static void launchAppURL(String url)
  {
    try
    {
      driver.navigate().to(url);
      System.out.println("Navigated to : " + url + " successfully ");
      log.debug("Navigated to : " + url + " successfully ");
      etest.log(Status.PASS, "Navigated to : " + url + " successfully ");
    }
    catch (Exception e) {
      System.err.println("ERROR :  Failed to navigate to : " + url);
      log.error("ERROR :  Failed to navigate to : " + url, e);
      CaptureInLogWithStatus(Status.FAIL, "Failed to navigate to" + url, Thread.currentThread().getStackTrace()[1].getMethodName());
      e.printStackTrace();
    }
  }
 
  private static WebElement isElementPresnt(By object, int waitTime)
  {
    webEle = null;
    for (int i = 0; i < waitTime; i++) {
      try
      {
        if (driver.findElement(object).isDisplayed()) {
          webEle = driver.findElement(object);
        }
      }
      catch (Exception e)
      {
        try {
          Thread.sleep(500L);
        } catch (InterruptedException e1) {
          System.err.println("Waiting for element to appear on DOM");
        }
      }
    }
    
    return webEle;
  }
 
  private static void locatorType(String element, String locator)
  {
    String str;
    


    switch ((str = locator.toLowerCase()).hashCode()) {
    case -1548231387:  if (str.equals("tagname")) {isElementPresnt(By.tagName(element), 120);
    } break;
    
    case -8935421:  if (str.equals("classname")) {isElementPresnt(By.className(element), 120);} break;
    case 3355:  if (str.equals("id")) {isElementPresnt(By.id(element), 120);} break; 
    case 98819:  if (str.equals("css")) {isElementPresnt(By.cssSelector(element), 120);} break; 
    case 3373707:  if (str.equals("name")) {isElementPresnt(By.name(element), 120);} break; 
    case 114256029:  if (str.equals("xpath")) {isElementPresnt(By.xpath(element), 120);} break;
    case 292026600:  if (str.equals("partialLinkText")) {isElementPresnt(By.partialLinkText(element), 120);} break;
    case 1195141159:  if (!str.equals("linktext")) {
        
    	isElementPresnt(By.linkText(element), 120);  
      }
    break ;
      }
    /*label289: if (webEle == null) {
      log.error("ERROR :  Element is not present on DOM after 60 sec :" + element + "and locator is : " + locator);
      CaptureInLogWithStatus(Status.FAIL, "Element is not present on DOM after 60 sec :" + element + "and locator is : " + locator, "Element not present");
      throw new NullPointerException(
        "Element is not present on DOM after 60 sec :" + element + "and locator is : " + locator);*/
    }


  private static String getAttributeValue()
  {
    String AttributeValue = null;
    if ((webEle.getText() != null) && (webEle.getText().length() > 0)) {
      AttributeValue = webEle.getText();
    } else if ((webEle.getAttribute("Text") != null) && (webEle.getAttribute("Text").length() > 0)) {
      AttributeValue = webEle.getAttribute("Text");
    }
    else if ((webEle.getAttribute("Value") != null) && (webEle.getAttribute("Value").length() > 0)) {
      AttributeValue = webEle.getAttribute("Value");
    }
    else if ((webEle.getAttribute("placeholder") != null) && (webEle.getAttribute("placeholder").length() > 0)) {
      AttributeValue = webEle.getAttribute("placeholder");
    }
    else if ((webEle.getAttribute("name") != null) && (webEle.getAttribute("name").length() > 0)) {
      AttributeValue = webEle.getAttribute("name");
    }
    else if ((webEle.getAttribute("label") != null) && (webEle.getAttribute("label").length() > 0)) {
      AttributeValue = webEle.getAttribute("label");
    }
    else if ((webEle.getAttribute("id") != null) && (webEle.getAttribute("id").length() > 0)) {
      AttributeValue = webEle.getAttribute("id");
    }
    else if ((webEle.getAttribute("href") != null) && (webEle.getAttribute("href").length() > 0)) {
      AttributeValue = webEle.getAttribute("href");
    }
    else {
      AttributeValue = "Null (This webelement does not have any matching attributes in DOM)";
    }
    
    return AttributeValue;
  }
  










  private static void highlightcontrol(WebElement elementToHighlight)
  {
    try
    {
      JavascriptExecutor js = (JavascriptExecutor)driver;
      js.executeScript("arguments[0].setAttribute('style','border: solid 2px Fuchsia');", new Object[] { elementToHighlight });
    } catch (Exception e) {
      e.printStackTrace();
      log.error("ERROR :  Failed to highlight webelement : " + elementToHighlight);
    }
  }
  







  public static void waitForElementToClick(By object)
  {
    try
    {
      wait = new WebDriverWait(driver, 60L);
      wait.until(ExpectedConditions.elementToBeClickable(object));
    } catch (Exception e) {
      System.err.println("ERROR :  failed to waitForElementToClick  : " + object);
      e.printStackTrace();
      log.error("ERROR :  failed to waitForElementToClick  : " + object, e);
      CaptureInLogWithStatus(Status.FAIL, "failed to waitForElementToClick  : " + object, Thread.currentThread().getStackTrace()[1].getMethodName());
    }
  }
  











  public static void waitForPresenceOfElement(By object)
  {
    try
    {
      wait = new WebDriverWait(driver, 60L);
      wait.until(ExpectedConditions.presenceOfElementLocated(object));
    }
    catch (Exception e) {
      System.err.println("ERROR :  Failed to waitForPresenceOfElement : " + object);
      e.printStackTrace();
      log.error("ERROR :  Failed to waitForPresenceOfElement : " + object, e);
      CaptureInLogWithStatus(Status.FAIL, "Failed to waitForPresenceOfElement : " + object, Thread.currentThread().getStackTrace()[1].getMethodName());
    }
  }
  










  public static void waitForVisibilityOfElement(By object)
  {
    try
    {
      wait = new WebDriverWait(driver, 300L);
      wait.until(ExpectedConditions.visibilityOfElementLocated(object));
    }
    catch (Exception e) {
      System.err.println("ERROR :  Failed to waitForVisibilityOfElement: " + object);
      e.printStackTrace();
      log.error("ERROR :  Failed to waitForVisibilityOfElement: " + object, e);
      CaptureInLogWithStatus(Status.FAIL, "Failed to waitForVisibilityOfElement: " + object, Thread.currentThread().getStackTrace()[1].getMethodName());
    }
  }
  











  public static void waitForInvisibilityOfElement(By object)
  {
    try
    {
      wait = new WebDriverWait(driver, 600L);
      wait.until(ExpectedConditions.invisibilityOfElementLocated(object));
    }
    catch (Exception e) {
      System.err.println("ERROR :  Failed to waitForInvisibilityOfElement : " + object);
      e.printStackTrace();
      log.error("ERROR :  Failed to waitForInvisibilityOfElement : " + object, e);
      CaptureInLogWithStatus(Status.FAIL, "Failed to waitForInvisibilityOfElement : " + object, Thread.currentThread().getStackTrace()[1].getMethodName());
    }
  }
  




























  public static void waitForUrlContains(String anyWordPresentInUrl, int waitTimeInSeconds)
  {
    try
    {
      wait = new WebDriverWait(driver, waitTimeInSeconds);
      wait.until(ExpectedConditions.urlContains(anyWordPresentInUrl));
      String URL = driver.getCurrentUrl();
      assertTrue(URL.contains(anyWordPresentInUrl), "Url does not contain : " + anyWordPresentInUrl);
      System.out.println("URL contains : " + anyWordPresentInUrl);
    }
    catch (Exception e) {
      System.err.println("ERROR :  foiled to waitForUrlContains : " + anyWordPresentInUrl);
      e.printStackTrace();
      log.error("ERROR :  foiled to waitForUrlContains : " + anyWordPresentInUrl, e);
      CaptureInLogWithStatus(Status.FAIL, "URL does not contain :" + anyWordPresentInUrl, Thread.currentThread().getStackTrace()[1].getMethodName());
    }
  }
  



















  public static boolean isElementDisplayed(By object)
  {
    try
    {
      boolean displayStatus = driver.findElement(object).isDisplayed();
      log.debug("isElementDisplayed " + object + " : " + displayStatus);
      
      return displayStatus;
    } catch (Exception e) {
      System.err.println("isElementDisplayed " + object + " : False ");
      log.debug("isElementDisplayed " + object + " : False "); }
    return false;
  }
  















  public static boolean isElementEnabled(By object)
  {
    try
    {
      boolean enableStatus = driver.findElement(object).isEnabled();
      log.debug("isElementEnabled " + object + " : " + enableStatus);
      
      return enableStatus;
    } catch (Exception e) {
      System.err.println("isElementEnabled " + object + " : False ");
      log.debug("isElementEnabled " + object + " : False "); }
    return false;
  }
  















  public static boolean isElementSelected(By object)
  {
    try
    {
      boolean selectedStatus = driver.findElement(object).isSelected();
      log.debug("isElementSelected " + object + " : " + selectedStatus);
      return selectedStatus;
    } catch (Exception e) {
      System.err.println("isElementSelected " + object + " : False ");
      log.debug("isElementSelected " + object + " : False "); }
    return false;
  }
  


















  public static String getTitle()
  {
    try
    {
      String title = driver.getTitle();
      log.debug("Title is : " + title);
      System.out.println("Title is : " + title);
      return title;
    } catch (Exception e) {
      System.err.println("ERROR :  Failed to get title");
      e.printStackTrace();
      
      log.error("ERROR :  Failed to get title", e); }
    return null;
  }
  













  public static String getUrl()
  {
    try
    {
      String url = driver.getCurrentUrl();
      System.out.println("Url of webpage is : " + url);
      log.debug("Url of webpage is : " + url);
      return url;
    } catch (Exception e) {
      System.err.println("ERROR :  Failed to get Url ");
      e.printStackTrace();
      
      log.error("ERROR :  Failed to get Url ", e); }
    return null;
  }
  















  public static void Click(String element, String locator)
  {
    locatorType(element, locator);
    
    highlightcontrol(webEle);
    String ElementAttribute = getAttributeValue();
    
    try
    {
      webEle.click();
      log.debug("clicked on :" + ElementAttribute);
      System.out.println("Clicked on " + ElementAttribute);
      
      Thread.sleep(600L);
    }
    catch (Exception e) {
      System.err.println("ERROR : Unable to click on " + ElementAttribute);
      e.printStackTrace();
      log.error("ERROR : Unable to click on " + ElementAttribute, e);
      CaptureInLogWithStatus(Status.FAIL, "Unable to click on " + ElementAttribute, Thread.currentThread().getStackTrace()[1].getMethodName());
    }
  }
  













  public static void sendKeys(String element, String locator, String value)
  {
    locatorType(element, locator);
    String ElementAttribute = getAttributeValue();
    try {
      highlightcontrol(webEle);
      webEle.sendKeys(new CharSequence[] { value });
      System.out.println("Text entered in " + ElementAttribute + " text box is : " + value);
      
      log.debug("Text entered in " + ElementAttribute + " text box is : " + value);
    }
    catch (Exception e) {
      System.err.println("ERROR :  Unable to enter text in " + ElementAttribute + " text box : " + value);
      e.printStackTrace();
      log.error("ERROR :  Unable to enter text in " + ElementAttribute + " text box : " + value, e);
      CaptureInLogWithStatus(Status.FAIL, "Unable to enter text in " + ElementAttribute + " text box : " + value, Thread.currentThread().getStackTrace()[1].getMethodName());
    }
  }
  












  public static void clear(String element, String locator)
  {
    locatorType(element, locator);
    String ElementAttribute = getAttributeValue();
    try
    {
      webEle.clear();
      
      System.out.println("cleared the " + ElementAttribute + " field");
      log.debug("cleared the " + ElementAttribute + " field");
    } catch (Exception e) {
      System.err.println("ERROR :  Unable to clear the " + ElementAttribute + " field");
      e.printStackTrace();
      log.error("ERROR :  Unable to clear the " + ElementAttribute + " field", e);
      CaptureInLogWithStatus(Status.FAIL, "Unable to clear the " + ElementAttribute + " field", Thread.currentThread().getStackTrace()[1].getMethodName());
    }
  }
  










  public static String viewText(String element, String locator)
  {
    String text = null;
    locatorType(element, locator);
    String ElementAttribute = getAttributeValue();
    highlightcontrol(webEle);
    try
    {
      text = webEle.getText();
      
      log.debug("GetText Successful & text is : " + text);
      return text;
    } catch (Exception e) {
      System.err.println("ERROR :  Unable to GetText of " + ElementAttribute);
      e.printStackTrace();
      log.error("ERROR :  Unable to GetText of " + ElementAttribute, e);
      CaptureInLogWithStatus(Status.FAIL, "Unable to GetText of " + ElementAttribute, Thread.currentThread().getStackTrace()[1].getMethodName()); }
    return null;
  }
  













  public static void mouseHover(String element, String locator)
  {
    locatorType(element, locator);
    String ElementAttribute = getAttributeValue();
    try
    {
      Actions action = new Actions(driver);
      action.moveToElement(webEle).perform();
      
      System.out.println("Mouse hover on " + ElementAttribute);
      log.debug("Mouse hover on " + ElementAttribute);
    } catch (Exception e) {
      System.err.println("ERROR :  Unable to mouse hover on " + ElementAttribute);
      e.printStackTrace();
      log.error("ERROR :  Unable to mouse hover on " + ElementAttribute, e);
      CaptureInLogWithStatus(Status.FAIL, "Unable to mouse hover on " + ElementAttribute, Thread.currentThread().getStackTrace()[1].getMethodName());
    }
  }
  












  public static void mouseHoverAndClick(String element, String locator)
  {
    locatorType(element, locator);
    String ElementAttribute = getAttributeValue();
    try
    {
      highlightcontrol(webEle);
      Actions action = new Actions(driver);
      action.moveToElement(webEle).click().build().perform();
      
      System.out.println("Mouse hover & clicked on " + ElementAttribute);
      log.debug("Mouse hover & clicked on " + ElementAttribute);
    } catch (Exception e) {
      System.err.println("ERROR :  Unable to mouse hover & click on " + ElementAttribute);
      e.printStackTrace();
      log.error("ERROR :  Unable to mouse hover & click on " + ElementAttribute, e);
      CaptureInLogWithStatus(Status.FAIL, "Unable to mouse hover & click on " + ElementAttribute, Thread.currentThread().getStackTrace()[1].getMethodName());
    }
  }
  




  public static void waitForSeconds(int timeToWaitInSeconds)
  {
    driver.manage().timeouts().implicitlyWait(timeToWaitInSeconds, TimeUnit.SECONDS);
  }
  


  public static void pageLoadTimeout(int timeToWaitInSeconds)
  {
    driver.manage().timeouts().pageLoadTimeout(timeToWaitInSeconds, TimeUnit.SECONDS);
  }
  









  public static void dropdownSelectUsingVisibleText(String element, String locator, String text)
  {
    locatorType(element, locator);
    highlightcontrol(webEle);
    try
    {
      select = new Select(webEle);
      select.selectByVisibleText(text);
      System.out.println("Value selected from dropdown is : " + text);
      
      log.debug("Value selected from dropdown is : " + text);
    } catch (Exception e) {
      System.err.println("ERROR :  Failed to select " + text + " from dropdown");
      
      log.error("ERROR :  Failed to select " + text + " from dropdown", e);
      CaptureInLogWithStatus(Status.FAIL, "Failed to select " + text + " from dropdown", Thread.currentThread().getStackTrace()[1].getMethodName());
      throw new NoSuchElementException("Cannot locate element with text : " + text);
    }
  }
  






  public static void dropdownSelectUsingIndex(String element, String locator, int index)
  {
    locatorType(element, locator);
    highlightcontrol(webEle);
    try
    {
      select = new Select(webEle);
      select.selectByIndex(index);
      System.out.println("Selected index from dropdown is : " + index);
      
      log.debug("Selected index from dropdown is : " + index);
    } catch (Exception e) {
      System.err.println("ERROR :  Failed to select index no : " + index + " from dropdown");
      
      log.error("ERROR :  Failed to select index no : " + index + " from dropdown", e);
      CaptureInLogWithStatus(Status.FAIL, "Failed to select index no : " + index + " from dropdown", Thread.currentThread().getStackTrace()[1].getMethodName());
      throw new NoSuchElementException("Cannot locate element with index : " + index);
    }
  }
  







  public static String dropdownGetSelectedOption(String element, String locator)
  {
    locatorType(element, locator);
    String option1 = null;
    try {
      select = new Select(webEle);
      webEle = select.getFirstSelectedOption();
      option1 = webEle.getText();
      System.out.println("Selected option in dropdown is :-" + option1);
      
      log.debug("Selected option in dropdown is :-" + option1);
      return option1;
    } catch (Exception e) {
      System.err.println("ERROR :  Failed to get Selected option from dropdown");
      
      log.error("ERROR :  Failed to get Selected option from dropdown", e);
      CaptureInLogWithStatus(Status.FAIL, "Failed to get Selected option from dropdown", Thread.currentThread().getStackTrace()[1].getMethodName());
      throw new NoSuchElementException("Failed to get Selected option from dropdown");
    }
  }
  










  public static List<WebElement> dropdownGetAllOptions(String element, String locator)
  {
    locatorType(element, locator);
    highlightcontrol(webEle);
    try
    {
      select = new Select(webEle);
      List<WebElement> ls = select.getOptions();
      
      System.out.println("Stored all options from dropdown in List");
      log.debug("Stored all options from dropdown in List");
      return ls;
    } catch (Exception e) {
      System.err.println("Unable to store all options from dropdown in List");
      
      log.error("ERROR :  Unable to store all options from dropdown in List", e);
      CaptureInLogWithStatus(Status.FAIL, "Unable to store all options from dropdown in List", Thread.currentThread().getStackTrace()[1].getMethodName());
      throw new NoSuchElementException("Unable to store all options from dropdown in List");
    }
  }
  














  private static String captureScreenshot(String screenshotName)
    throws Exception
  {
    String endImageWithTimeStamp = "_" + str_todaysDateTimeStamp + ".jpeg";
    
    TakesScreenshot ts = (TakesScreenshot)driver;
    File source = (File)ts.getScreenshotAs(OutputType.FILE);
    String returnImage = screenshotName + endImageWithTimeStamp;
    


    String newScreenshotDirPath = createNewDirectory(parentResultDirectory + "Reports_");
    
    String destination = newScreenshotDirPath + screenshotName + endImageWithTimeStamp;
    File finalDestination = new File(destination);
    FileUtils.copyFile(source, finalDestination);
    return returnImage;
  }

  public static String getTodaysDate()
  {
    return str_todaysDateStamp;
  }
 
  public static void stringAssertEquals(String Actual, String Expected, String Message)
  {
    Assert.assertEquals(Actual, Expected, Message);
  }
  
  public static void intAssertEquals(int Actual, int Expected, String Message) {
    Assert.assertEquals(Actual, Expected, Message);
  }
  
  public static void stringAssertNotEquals(String Actual, String Expected)
  {
    Assert.assertNotEquals(Actual, Expected);
  }
  
  public static void intAssertNotEquals(int Actual, int Expected) {
    Assert.assertNotEquals(Integer.valueOf(Actual), Integer.valueOf(Expected));
  }
  
  public static void assertTrue(boolean condition, String Message) {
    Assert.assertTrue(condition, Message);
  }
  
  public static void assertFalse(boolean condition, String Message) {
    Assert.assertFalse(condition, Message);
  }
  

  public static String getClipboardContents(String element, String locator)
    throws UnsupportedFlavorException, IOException
  {
    locatorType(element, locator);
    mouseHoverAndClick(element, locator);
    try
    {
      Robot r = new Robot();
      r.keyPress(17);
      r.keyPress(65);
      Thread.sleep(500L);
      r.keyRelease(17);
      r.keyRelease(65);
      Thread.sleep(500L);
      
      r.keyPress(17);
      r.keyPress(67);
      Thread.sleep(500L);
      r.keyRelease(17);
      r.keyRelease(67);
    }
    catch (AWTException|InterruptedException e)
    {
      e.printStackTrace();
    }
    mouseHoverAndClick(element, locator);
    Clipboard clipboard = Toolkit.getDefaultToolkit().getSystemClipboard();
    Transferable contents = clipboard.getContents(null);
    String x = (String)contents.getTransferData(DataFlavor.stringFlavor);
    return x;
  }
 
  public static ResultSet getDatabaseData(String query)
  {
    ResultSet rs = null;
    try {
      Class.forName(pro.getProperty("DB_driver"));
      String userName = pro.getProperty("DB_userName");
      String password = pro.getProperty("DB_password");
      String url = pro.getProperty("DB_url");
      Connection con = DriverManager.getConnection(url, userName, password);
      System.out.println("connected to database");
      Statement statement = con.createStatement();
      rs = statement.executeQuery(query);
    }
    catch (Exception e) {
      System.err.println("ERROR : Failed to connect to DB ");
      log.error("ERROR : Failed to connect to DB ");
      etest.log(Status.ERROR, "Error : Failed to connect to DB");
    }
    return rs;
  }
 
  @AfterMethod
  public static void getCustomResult(ITestResult result)
    throws Exception
  {
    if (result.getStatus() == 2)
    {
      log.error("ERROR :  Test Case Failed is : " + result.getName());
      log.error("ERROR :  Test Case Failed because of :  " + result.getThrowable());
      

      String screenshotPath = captureScreenshot(result.getName());
      etest.fail("Test Case Failed is : " + result.getName(), MediaEntityBuilder.createScreenCaptureFromPath(screenshotPath).build());
      etest.log(Status.FAIL, "Test Case Failed because of :  " + result.getThrowable());



    }
    else if (result.getStatus() == 3) {
      log.debug("Test Case Skipped is : " + result.getName());
      etest.log(Status.SKIP, "Test Case Skipped is : " + result.getName());
    } else if (result.getStatus() == 1) {
      log.debug("Test Case Pass is : " + result.getName());
      etest.log(Status.PASS, "Test Case Pass is : " + result.getName());
    }
    



    extentReports.flush();
  }
 
  public static void CaptureInLogWithStatus(Status Status, String message, String NameofScreenShot)
  {
    String screenshotPath = null;
    try {
      screenshotPath = captureScreenshot(NameofScreenShot);
    }
    catch (Exception e) {
      e.printStackTrace();
    }
    try
    {
      etest.log(Status, message, MediaEntityBuilder.createScreenCaptureFromPath(screenshotPath).build());
    }
    catch (IOException e)
    {
      e.printStackTrace();
    }
  }

  @BeforeClass
  public static void StartExcel()
  {
    ExcelWriter.createWorkbook();
    log.debug("New workbook created");
  }
  
  @AfterClass
  public void EndExcel()
  {
    try
    {
      ExcelWriter.closeWorkbook(getClass().getSimpleName());
      log.debug("Excel workbook closed with name : " + getClass().getSimpleName());
    }
    catch (Exception e)
    {
      e.printStackTrace();
      log.error("ERROR :  Failed to close excel workbook", e);
    }
  }

  @AfterSuite
  public static void teardown()
  {
    try
    {
      recorder.stop();
      log.debug("ATU recorder Stop");
    }
    catch (ATUTestRecorderException e) {
      e.printStackTrace();
      log.error("ERROR :  Failed to stop ATU recorder", e);
    } finally {
      if (fis != null) {
        try {
          fis.close();
        } catch (IOException e) {
          log.error(e);
        }
      }
      
      System.out.println("For Framework related any help please contact : Tushar Mohite <tushar.mohite@nihilent.com>");
    }
  }
}
