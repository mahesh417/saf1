package framework;

import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;

public class Validators {
	
	
	public void verifyPartialTextOfElement(By by, String expectedText, String stepName){
		
		WebElement element = Events.waitForElementToDisplay(by, stepName, 20);
		try{
			if (element.getText().contains(expectedText)){
				System.out.println("PASS : the given text : " + expectedText + " found in the element " + by.toString());
			} else {
				System.out.println("FAIL : the given text : " + expectedText + " NOT found in the element " + by.toString()+ '\n' + " Actual text found in the element is : " + element.getText());
			}	
		} catch (NullPointerException nse){
			nse.printStackTrace();
		}

		
	}
	
//************************************************************************************************************
	
	public void verifyPartialTextOfElement(WebElement element, String expectedText, String stepName){
		
		
		try{
			if (element.getText().contains(expectedText)){
				System.out.println("PASS : the given text : " + expectedText + " found in the element " );
			} else {
				System.out.println("FAIL : the given text : " + expectedText + " NOT found."+'\n'+" Actual text found in the element is : " + element.getText());
			}	
		} catch (NullPointerException nse){
			nse.printStackTrace();
		}

		
	}

	
	
//************************************************************************************************************
		
		public void verifyPageTitle(String expTitle,String stepName){
			String actTitle = Data.Common.driver.getTitle();
			
			if (actTitle.equalsIgnoreCase(expTitle)){
				System.out.println("PASS : " + stepName + " ; Page title is as expected : " + expTitle);
			} else {
				System.out.println("FAIL : " + stepName + " ; Page title mismatched. Expected : " + expTitle + "; Actual :" + actTitle);
			}
			
		}
		
//************************************************************************************************************
		
		public void verifyPageTitleContains(String expTitle,String stepName){
			String actTitle = Data.Common.driver.getTitle();
			
			if (actTitle.toLowerCase().contains(expTitle.toLowerCase())){
				System.out.println("PASS : " + stepName + " ; Page title contains the exepected value : " + expTitle);
			} else {
				System.out.println("FAIL : " + stepName + " ; Page title does not contain the expected value.. Expected value to be present : " + expTitle + "; Actual :" + actTitle);
			}
			
		}
		
//****************************************************************************************************************
		
		public static boolean verifyObjectEnabled(WebElement element, String stepName){
			boolean isObjectEnabled = false;
			try{				
				isObjectEnabled = element.isEnabled();	
			} catch (Exception e){
				System.out.println("FAIL : Step Name :  " + stepName );
				e.printStackTrace();
				
			}
			
			return isObjectEnabled;
		}

//*****************************************************************************************************************
		
	public static boolean verifyCheckSelected(WebElement element, String stepName){
		
		boolean isSelected = false;			
		try{
			isSelected = element.isSelected();
		} catch(Exception e){
			System.out.println("FAIL : Step Name :  " + stepName );
			e.printStackTrace();
			
		}
		return isSelected;					
	}
	


//*****************************************************************************************************************
	
		public static class Cases{
			
			public static void verifyCreateCaseExists(String userType){
				
				WebElement element = Events.waitForElementToDisplay(UtilityMethods.getBy_from_Repository("createCaseLink"), "Check if 'Create New Case' element is displayed for the user type : " + userType, 20);
				
				switch (userType.toLowerCase()) {
					case "verified":
						 if (element != null){
							 System.out.println("PASS : 'Create New Case' link is displayed for the user type : " + userType);
						 } else {
							 System.out.println("FAIL : 'Create New Case' link is NOT displayed for the user type : " + userType);
						 }
						break;
	
					default:
						 if (element == null){
							 System.out.println("PASS : 'Create New Case' link is NOT displayed for the user type : " + userType);
						 } else {
							 System.out.println("FAIL : 'Create New Case' link is displayed for the user type : " + userType);
						 }
						break;
				}
			}
			
			public static void verifyLateCaseIsOnTop(String caseSubject){
				
			}
		}
			
}
