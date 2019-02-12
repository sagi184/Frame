package pomcall;

import org.testng.Assert;
import org.testng.annotations.Test;

import com.aventstack.extentreports.Status;

import configuration.BaseClass;
import configuration.Locator;
import excelConfiguration.ExcelReader;
import pOMCOR.PomcOR;

public class GotoPodetails extends BaseClass { 
	
	@Test
	public void OpenRecordAndGotoPOsDetailScreen() {
		try{
			startReport("TC-48673 Open POs detail screen and click on back button", "Goto PO detail screen and click on back button ", "Regression", "Rucha Deshpande");
			
			readExcelSheet(pro.getProperty("TestSheet_POMC"));
			
			launchAppURL(pro.getProperty("RWS_POMC_URL"));
			
			etest.pass("Launch PO Mass Change application");
			
			String str_PONum_excel= ExcelReader.getCellData(5, 2);
						
			etest.pass("Enter PO number in text box");
			
			sendKeys(PomcOR.txtbox_POnumber_id, Locator.ID, str_PONum_excel);
			
			etest.pass("Click on search button for results");
			
			Click(PomcOR.button_search_xpath, Locator.XPath);
			
			etest.pass("Click on back button to navigate to the search screen");
			
			Thread.sleep(4000);	
							
			Click(PomcOR.button_back_xpath, Locator.XPath);				
		
			String str_poStatus_excel = ExcelReader.getCellData(1,3);
			
			etest.pass("Select value for PO status field");
			Thread.sleep(2000);
			dropdownSelectUsingVisibleText(PomcOR.dropdown_PoOrderStatus_xpath, Locator.XPath, str_poStatus_excel);
								
					
			etest.pass("click on linktext of PO number to navigate to the PO detail screen");
			
			Thread.sleep(2000);
			
			Click(str_PONum_excel, Locator.Linktext);
			
			Thread.sleep(4000);	
			
			}catch (AssertionError e) {
				e.printStackTrace();
				etest.log(Status.ERROR, e.getMessage());
				Assert.fail();
				
			} catch (Exception e) {
				e.printStackTrace();
				etest.log(Status.ERROR, e.getMessage());
				Assert.fail();
			}
			
			
		}
}
