package framework;

import java.time.Duration;
import java.util.HashMap;
import java.util.List;

import org.openqa.selenium.Alert;
import org.openqa.selenium.By;
import org.openqa.selenium.NoSuchElementException;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.ie.InternetExplorerDriver;
import org.openqa.selenium.ie.InternetExplorerOptions;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.remote.CapabilityType;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.Select;
import org.openqa.selenium.support.ui.WebDriverWait;


public class Events {

//*************************************************************************************************************************************************************************************************	
	public static void launchApplication(String browser, String url){
		
		switch (browser.toLowerCase()) {
		case "chrome":
			
			ChromeOptions chromeOptions = new ChromeOptions();
				chromeOptions.addArguments("--ignore-certificate-errors", "--disable-extensions", "--dns-prefetch-disable", "lang=en_US.UTF-8","--disable-infobars","--new-window");
				chromeOptions.setCapability(CapabilityType.ForSeleniumServer.ENSURING_CLEAN_SESSION, true);
				HashMap<String, Object> prefs = new HashMap<String, Object>();
				prefs.put("credentials_enable_service", false);
				prefs.put("profile.password_manager_enabled", false);
				chromeOptions.setExperimentalOption("prefs", prefs);
				Data.Common.driver = new ChromeDriver(chromeOptions);
				
			break;
		case "ie":
				InternetExplorerOptions ieoptions = new InternetExplorerOptions();
				ieoptions.ignoreZoomSettings();
				ieoptions.destructivelyEnsureCleanSession();
				UtilityMethods.enableProtectedMode();
				
				Data.Common.driver = new InternetExplorerDriver(ieoptions);
				Data.Common.driver.get(url);
		default:
			break;
		}
		
		Data.Common.driver.get(url);
	}
	
//****************************************************************************************************
	public void enterValue(By by,String strValue, String stepName){
		
		try{			
			WebElement element = Data.Common.driver.findElement(by);			
			element.clear();
			element.sendKeys(strValue);			
			
		} catch(NoSuchElementException nse){
			System.out.println(stepName + " ; No Element found in the application with given Locator :" + by.toString());
		}
		
		
	}
	
//*********************************************************************************************
	
	public static void selectByVisisbleText(By by, String valueToSelect, String stepName){
		
		try{
			WebElement element = Data.Common.driver.findElement(by);
			Select listbox = new Select(element);
			int itemIndex = getItemIndexFromList(element,valueToSelect);
			
			if (itemIndex >= 0){
				listbox.selectByIndex(itemIndex);
			} else {
				System.out.println("FAIL : "+ stepName + "; the value " + valueToSelect+ " is not present in the listbox.");
				System.exit(0);
			}
			
			
		} catch(NoSuchElementException ne){
			System.out.println("FAIL : " + stepName + "; unable to select the given value as the list box with locator  " + by.toString()+ " is not found in the application.");
		}
	}

//*******************************************************************************************
	public static int getItemIndexFromList(WebElement element,String strValue){
		
		List<WebElement> allOptions = element.findElements(By.tagName("option"));
		int itemIndex = -1;
		
		for (int i = 0; i < allOptions.size(); i++){
			String optionText = allOptions.get(i).getText();
			
			if (optionText.equalsIgnoreCase(strValue)){
				itemIndex = i;
				break;
			}
		}
		
		return itemIndex;
	}
	
//******************************************************************************************
	
	public static WebElement waitForElementToDisplay(By by, String stepName, int maxTime){
		WebElement element = null;
		try{			
			WebDriverWait wait = new WebDriverWait(Data.Common.driver, maxTime);
			wait.pollingEvery(Duration.ofMillis(300));			
			element = wait.until(ExpectedConditions.visibilityOfElementLocated(by));
		} catch (Exception e){
			System.out.println("Element with locator : " + by.toString()+" is not displayed in the application even after waiting for " + maxTime + " seconds.");
		}
		
		return element;
	}
	
	
//********************************************************************************************
	
	public static void verifyAlertMessage(String strMessage){
		
		Alert a = Data.Common.driver.switchTo().alert();		
		String alertText = a.getText();
		if (alertText.equalsIgnoreCase(strMessage)){
			System.out.println("SUCCESS : The text in alert is matched with expected message : " + strMessage);
		} else {
			System.out.println("FAIL : The text in alert message not matched with expected." + '\n' + "Expected Message : " + strMessage + '\n' + "Actual Message : " + alertText);
		}
		
	}
	
// ***********************************************************************************************
	
	public static void acceptAlert(){
		Alert a = Data.Common.driver.switchTo().alert();
		
		if (a != null){
			a.accept();
		} else {
			System.out.println("FAIL : No Alert is found in the application.");
		}
	}
	
// ***********************************************************************************************
	
		public static void declineAlert(){
			Alert a = Data.Common.driver.switchTo().alert();
			
			if (a != null){
				a.dismiss();
			} else {
				System.out.println("FAIL : No Alert is found in the application.");
			}
		}
		
// *************************************************************************************************
		
		public static void moveMouseOnToElement(WebElement element, String stepName){
			Actions action = new Actions(Data.Common.driver);
			
			if (element!=null){
				action.moveToElement(element);
			} else {
				System.out.println("FAIL : "+'\n' + "STEP NAME : " + stepName +'\n' +  " Cause for Fail :  Unable to perform mouse move operation as element is null.");
			}
			
		}
//***********************************************************************************************************
		
		public static void moveMouseOnToElement(By by, String stepName){
			Actions action = new Actions(Data.Common.driver);
			
			try{
				WebElement element = Data.Common.driver.findElement(by);			
				 action.moveToElement(element);
			} catch (NoSuchElementException nse){
				System.out.println("ERROR : " + '\n' + "STEP NAME : " + stepName + '\n' + "Reason for Fail : No Element is found with given locator : " + by.toString());
			}	
		}
		
//***********************************************************************************************************

		public static void doubleClickOnElemenent(By by, String stepName){
			Actions action = new Actions(Data.Common.driver);
			
			try{
				WebElement element = Data.Common.driver.findElement(by);			
				 action.doubleClick(element);
			} catch (NoSuchElementException nse){
				System.out.println("ERROR : " + '\n' + "STEP NAME : " + stepName + '\n' + "Reason for Fail : No Element is found with given locator : " + by.toString());
			}	
		}
		
//******************************************************************************
	public static void doubleClickOnElemenent(WebElement element, String stepName){
			Actions action = new Actions(Data.Common.driver);
			
			if (element!=null){
				action.doubleClick(element);
			} else {
				System.out.println("FAIL : "+'\n' + "STEP NAME : " + stepName +'\n' +  " Reason for Fail : Given element is null.");
			}
			
		}
	
//******************************************************************************
		public static void rightClickOnElement(WebElement element, String stepName){
				Actions action = new Actions(Data.Common.driver);
				
				if (element!=null){
					action.contextClick(element);
				} else {
					System.out.println("FAIL : "+'\n' + "STEP NAME : " + stepName +'\n' +  " Reason for Fail : Given element is null.");
				}
				
			}
		
//***********************************************************************************************************

public static void rightClickOnElement(By by, String stepName){
		Actions action = new Actions(Data.Common.driver);
		
		try{
			WebElement element = Data.Common.driver.findElement(by);			
			 action.contextClick(element);
		} catch (NoSuchElementException nse){
			System.out.println("ERROR : " + '\n' + "STEP NAME : " + stepName + '\n' + "Reason for Fail : No Element is found with given locator : " + by.toString());
		}	
	}

 public static void clickButton(By by,String stepName){
	 try{
		 WebElement element = Data.Common.driver.findElement(by);
		 
	 	} catch (NoSuchElementException nse){
	 System.out.println("ERROR : " + '\n' + "STEP NAME : " + stepName + '\n' + "Reason for Fail : No Element is found with given locator : " + by.toString());
	}	
	 
	 
	 
 }
}
