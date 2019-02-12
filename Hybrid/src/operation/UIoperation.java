package operation;

import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.Keys;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.Assert;
import org.testng.Reporter;

import com.relevantcodes.extentreports.LogStatus;

import testcases.BaseClass1;

public class UIoperation {

	WebDriver driver;
	WebDriver driver1;
	WebDriverWait wait;
	JavascriptExecutor jse;
	public static String emailid;
	public static String Emailstr;
	public static String otp;
	BaseClass1 baseclassobj;

	public UIoperation(WebDriver driver) {
		this.jse = (JavascriptExecutor) this.driver;
		this.baseclassobj = new BaseClass1();
		this.driver = driver;
		this.wait = new WebDriverWait(driver, 40L);
	}

	public void perform(String ObjValue, String operation, String objectName, String objectType, String value)
			throws Exception {

		System.out.println("");
		String e1;
		switch ((e1 = operation.toUpperCase()).hashCode()) {
		case -2010775627:
			if (e1.equals("HANDLE_POPUP")) {
				WebElement ele5551 = this.driver.findElement(By.id("ifsc-alert"));
				String name5551 = ele5551.getClass().getName();
				if (name5551 == "close") {
					Actions ele12 = new Actions(this.driver);
					ele12.click(ele5551).perform();
				} else {
					System.out.println("No such element found");
				}
			}
			break;
		case -1852075189:
			if (e1.equals("WAIT_12_SECONDS")) {
				Thread.sleep(5000L);
				BaseClass1.test.log(LogStatus.INFO, operation + " operation successful :" + objectName);
			}
			break;
		case -1725128435:
			if (e1.equals("SELECTDROPDOWN")) {
				BaseClass1.test.log(LogStatus.INFO, "Starting " + operation + " operation :" + objectName);
				this.wait.until(
						ExpectedConditions.elementToBeClickable(this.getObject(ObjValue, objectName, objectType)));
				this.driver.findElement(this.getObject(ObjValue, objectName, objectType))
						.sendKeys(new CharSequence[] { value });
				this.driver.findElement(this.getObject(ObjValue, objectName, objectType))
						.sendKeys(new CharSequence[] { Keys.ARROW_DOWN });
				this.driver.findElement(this.getObject(ObjValue, objectName, objectType))
						.sendKeys(new CharSequence[] { Keys.ENTER });
				BaseClass1.test.log(LogStatus.INFO, operation + " operation successful :" + objectName);
			}
			break;
		case -1709542161:
			if (e1.equals("CTRL_KEY_UP")) {
				Actions actions6 = new Actions(this.driver);
				actions6.keyUp(Keys.LEFT_CONTROL).build().perform();
			}
			break;
		case -1591047057:
			if (e1.equals("SETTEXT")) {
				BaseClass1.test.log(LogStatus.INFO, "Starting " + operation + " operation :" + objectName);
				this.wait.until(
						ExpectedConditions.elementToBeClickable(this.getObject(ObjValue, objectName, objectType)));
				this.driver.findElement(this.getObject(ObjValue, objectName, objectType))
						.sendKeys(new CharSequence[] { value });
				BaseClass1.test.log(LogStatus.INFO, operation + " operation successful :" + objectName);
			}
			break;
		case -1140682743:
			if (e1.equals("WAIT_3_SECONDS")) {
				Thread.sleep(3000L);
				BaseClass1.test.log(LogStatus.INFO, operation + " operation successful :" + objectName);
			}
			break;
		case -1090550451:
			if (e1.equals("SCROLL_UP")) {
				Actions action22 = new Actions(this.driver);
				action22.sendKeys(new CharSequence[] { Keys.PAGE_UP });
				BaseClass1.test.log(LogStatus.INFO, operation + " operation successful :" + objectName);
			}
			break;
		case -541520336:
			if (e1.equals("CLOSEBROWSER")) {
				Thread.sleep(2500L);
				this.driver.quit();
				BaseClass1.test.log(LogStatus.INFO, operation + " operation successful :" + objectName);
			}
			break;
		case -501501933:
			if (e1.equals("SWITCHTOIFRAME")) {
				Thread.sleep(2000L);
				WebElement iframeSwitch1 = this.driver.findElement(By.id("plaid-link-iframe"));
				this.driver.switchTo().frame(iframeSwitch1);
				System.out.println("switcehd to iFrame");
			}
			break;
		case -460624373:
			if (e1.equals("WAIT_5_SECONDS")) {
				Thread.sleep(5000L);
				BaseClass1.test.log(LogStatus.INFO, operation + " operation successful :" + objectName);
			}
			break;
		case -193976606:
			if (e1.equals("ASSERTION")) {
				BaseClass1.test.log(LogStatus.INFO, "Starting " + operation + " operation :" + objectName);
				this.wait.until(
						ExpectedConditions.elementToBeClickable(this.getObject(ObjValue, objectName, objectType)));
				WebElement a = this.driver.findElement(this.getObject(ObjValue, objectName, objectType));
				String str = a.getText();

				try {
					Assert.assertEquals("Strings are not matching", value, str);
					System.out.println("Assertion Test passed");
					BaseClass1.test.log(LogStatus.INFO, operation + " operation successful :" + objectName);
				} catch (AssertionError arg27) {
					Reporter.log("Error: " + arg27.getLocalizedMessage());
					Assert.fail();
				}
			}
			break;
		case -47467820:
			if (e1.equals("SCROLL_DOWN")) {
				Actions action = new Actions(this.driver);
				action.sendKeys(new CharSequence[] { Keys.PAGE_DOWN });
				BaseClass1.test.log(LogStatus.INFO, operation + " operation successful :" + objectName);
			}
			break;
		case 32886021:
			if (e1.equals("SENDER_ACCOUNT_NUMBER")) {
				BaseClass1.test.log(LogStatus.INFO, "Starting " + operation + " operation :" + objectName);
				this.wait.until(
						ExpectedConditions.elementToBeClickable(this.getObject(ObjValue, objectName, objectType)));
				this.driver.findElement(this.getObject(ObjValue, objectName, objectType))
						.sendKeys(new CharSequence[] { value });
				// this.baseclassobj.writeDataToSheet(Integer.valueOf(value));
				BaseClass1.test.log(LogStatus.INFO, operation + " operation successful :" + objectName);
			}
			break;
		case 64208429:
			if (e1.equals("CLEAR")) {
				BaseClass1.test.log(LogStatus.INFO, "Starting " + operation + " operation :" + objectName);
				this.wait.until(
						ExpectedConditions.elementToBeClickable(this.getObject(ObjValue, objectName, objectType)));
				this.driver.findElement(this.getObject(ObjValue, objectName, objectType)).clear();
				BaseClass1.test.log(LogStatus.INFO, operation + " operation successful :" + objectName);
			}
			break;
		case 64212328:
			if (e1.equals("CLICK")) {
				BaseClass1.test.log(LogStatus.INFO, "Starting " + operation + " operation :" + objectName);
				this.wait.until(
						ExpectedConditions.elementToBeClickable(this.getObject(ObjValue, objectName, objectType)));
				WebElement ele1 = this.driver.findElement(this.getObject(ObjValue, objectName, objectType));
				Actions action11 = new Actions(this.driver);
				action11.click(ele1).perform();
				BaseClass1.test.log(LogStatus.INFO, operation + " operation successful :" + objectName);
				System.out.println("Clicked by PRADIP");
			}
			break;
		case 66129592:
			if (e1.equals("ENTER")) {
				Actions action3 = new Actions(this.driver);
				action3.sendKeys(new CharSequence[] { Keys.ENTER });
				BaseClass1.test.log(LogStatus.INFO, operation + " operation successful :" + objectName);
			}
			break;
		case 219433997:
			if (e1.equals("WAIT_7_SECONDS")) {
				Thread.sleep(5000L);
				BaseClass1.test.log(LogStatus.INFO, operation + " operation successful :" + objectName);
			}
			break;
		case 312590202:
			if (e1.equals("DOUBLE_CLICK")) {
				BaseClass1.test.log(LogStatus.INFO, "Starting " + operation + " operation :" + objectName);
				this.wait.until(
						ExpectedConditions.elementToBeClickable(this.getObject(ObjValue, objectName, objectType)));
				WebElement ele11 = this.driver.findElement(this.getObject(ObjValue, objectName, objectType));
				Actions action21 = new Actions(this.driver);
				action21.doubleClick(ele11).perform();
				BaseClass1.test.log(LogStatus.INFO, operation + " operation successful :" + objectName);
				System.out.println("Double Clicked by PRADIP");
			}
			break;
		case 666771720:
			if (e1.equals("WAIT_2_SECONDS")) {
				Thread.sleep(2000L);
				BaseClass1.test.log(LogStatus.INFO, operation + " operation successful :" + objectName);
			}
			break;
		case 780266212:
			if (e1.equals("WAIT_60_SECONDS")) {
				Thread.sleep(5000L);
				BaseClass1.test.log(LogStatus.INFO, operation + " operation successful :" + objectName);
			}
			break;
		case 929968396:
			if (e1.equals("GOTOURL")) {
				this.driver.get(ObjValue);
				BaseClass1.test.log(LogStatus.INFO,
						operation + " operation successful :" + objectName + " - " + ObjValue);
			}
			break;
		case 1315496014:
			if (e1.equals("WAIT_15_SECONDS")) {
				Thread.sleep(5000L);
				BaseClass1.test.log(LogStatus.INFO, operation + " operation successful :" + objectName);
			}
			break;
		case 1346830090:
			if (e1.equals("WAIT_4_SECONDS")) {
				Thread.sleep(4000L);
				BaseClass1.test.log(LogStatus.INFO, operation + " operation successful :" + objectName);
			}
			break;
		case 1369806727:
			if (e1.equals("WAIT_30_SECONDS")) {
				Thread.sleep(5000L);
				BaseClass1.test.log(LogStatus.INFO, operation + " operation successful :" + objectName);
			}
			break;
		case 1451054738:
			if (e1.equals("SWITCHTODEFAULT")) {
				this.driver.switchTo().defaultContent();
				System.out.println("switcehd to default");
			}
			break;
		case 1511046686:
			if (e1.equals("D2B_SWITCHTOIFRAME")) {
				Thread.sleep(2000L);
				WebElement iframeSwitch1 = this.driver.findElement(By.id("plaid-link-iframe-1"));
				this.driver.switchTo().frame(iframeSwitch1);
				System.out.println("switcehd to iFrame");
			}
			break;
		case 1566320232:
			if (e1.equals("WAIT_20_SECONDS")) {
				Thread.sleep(5000L);
				BaseClass1.test.log(LogStatus.INFO, operation + " operation successful :" + objectName);
			}
			break;
		case 1762833737:
			if (e1.equals("WAIT_10_SECONDS")) {
				Thread.sleep(5000L);
				BaseClass1.test.log(LogStatus.INFO, operation + " operation successful :" + objectName);
			}
			break;
		case 1974198908:
			if (e1.equals("SELECTDD")) {
				BaseClass1.test.log(LogStatus.INFO, "Starting " + operation + " operation :" + objectName);
				this.wait.until(
						ExpectedConditions.elementToBeClickable(this.getObject(ObjValue, objectName, objectType)));
				this.driver.findElement(this.getObject(ObjValue, objectName, objectType))
						.sendKeys(new CharSequence[] { value });
				this.driver.findElement(this.getObject(ObjValue, objectName, objectType))
						.sendKeys(new CharSequence[] { Keys.ENTER });
			}
			break;
		case 1998246177:
			if (e1.equals("CTRL+A")) {
				Actions builder = new Actions(this.driver);
				builder.sendKeys(new CharSequence[] { Keys.chord(new CharSequence[] { Keys.CONTROL, "a" }) }).perform();
			}
			break;
		case 1998246195:
			if (e1.equals("CTRL+S")) {
				BaseClass1.test.log(LogStatus.INFO, "Starting " + operation + " operation :" + objectName);
				Actions builder1 = new Actions(this.driver);
				builder1.sendKeys(new CharSequence[] { Keys.chord(new CharSequence[] { Keys.CONTROL, "s" }) })
						.perform();
				BaseClass1.test.log(LogStatus.INFO,
						operation + " operation successful :" + objectName + " - " + ObjValue);
			}
			break;
		case 2026888460:
			if (e1.equals("WAIT_6_SECONDS")) {
				Thread.sleep(5000L);
				BaseClass1.test.log(LogStatus.INFO, operation + " operation successful :" + objectName);
			}
			break;
		case 2054124673:
			if (e1.equals("ESCAPE")) {
				Actions action21 = new Actions(this.driver);
				action21.sendKeys(new CharSequence[] { Keys.ESCAPE }).perform();
				BaseClass1.test.log(LogStatus.INFO, operation + " operation successful :" + objectName);
			}
			break;
		case 2101953014:
			if (e1.equals("CTRL_KEY_DOWN")) {
				Actions actions5 = new Actions(this.driver);
				actions5.keyDown(Keys.LEFT_CONTROL).build().perform();
			}
		}

	}

	private void If(boolean b) {
	}

	private void POPUP_HANDLER() {
	}

	private By getObject(String objValue, String objectName, String objectType) throws Exception {
		if (objectType.equalsIgnoreCase("XPATH")) {
			return By.xpath(objValue);
		} else if (objectType.equalsIgnoreCase("ID")) {
			return By.id(objValue);
		} else if (objectType.equalsIgnoreCase("CLASSNAME")) {
			return By.className(objValue);
		} else if (objectType.equalsIgnoreCase("NAME")) {
			return By.name(objValue);
		} else if (objectType.equalsIgnoreCase("CSS")) {
			return By.cssSelector(objValue);
		} else if (objectType.equalsIgnoreCase("LINK")) {
			return By.linkText(objValue);
		} else if (objectType.equalsIgnoreCase("PARTIALLINK")) {
			return By.partialLinkText(objValue);
		} else {
			throw new Exception("Wrong object type");
		}
	}

}
