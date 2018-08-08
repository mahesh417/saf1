package tests;

import org.testng.annotations.Optional;
import org.testng.annotations.Parameters;
import org.testng.annotations.Test;
import framework.Reporter;
import framework.Common;
import framework.Data;
import framework.Events;

public class Cases extends Reporter{

	
	@Parameters("browser")
	@Test
	public void verify_permissions_for_not_verified_user(@Optional String browser){
				
		Data.Common.logger = Data.Common.extent.createTest("verify_permissions_for_not_verified_user");
	
		writeLog("pass", "sample Step", "saple result");
		writeLog("pass", "step 2", "sadkjhask");
		
		
	}

	
	@Parameters("browser")
	@Test
	public void verify_permissions_for_verified_user( @Optional String browser){
				
		Data.Common.logger = Data.Common.extent.createTest("verify_permissions_for_verified_user");
		browser = (browser==null)?"chrome":browser;
		
		Events.launchApplication(browser, Data.Common.envConfigData.get("url"));
		Common.Login(Data.Common.envConfigData.get("username"), Data.Common.envConfigData.get("password"));
	
		writeLog("pass", "step in verified user", "step in not verified user.");
		writeLog("fail", "dasdsadas", "dsadasdsadadsadsadas");
		
		
	}
	
}
