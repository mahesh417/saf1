package tests;

import org.apache.log4j.PropertyConfigurator;
import org.testng.annotations.Optional;
import org.testng.annotations.Parameters;
import org.testng.annotations.Test;
import org.testng.log4testng.Logger;

import framework.Events;
import framework.Reporter;
import framework.UtilityMethods;
import framework.Data;

public class Cases extends Reporter{
	@Parameters("browser")
	@Test
	public void CreateNewCase(@Optional String browser){
		String log4jConfPath = "Config\\log4j.properties";
		PropertyConfigurator.configure(log4jConfPath);
		
		Data.Common.log4Logger = Logger.getLogger(Cases.class);
		Data.Common.log4Logger.debug("A sample debug message.");
		
		Data.Common.logger = Data.Common.extent.createTest("Creating A New Case");
		UtilityMethods.closeBrowsers();
		
		System.out.println(Data.Common.envConfigData.get("url"));
		Events.launchApplication("chrome", Data.Common.envConfigData.get("url"));

		
		
		Data.Common.logger.fail("An error generated while navigating to Cases Page.");
		
	}

	
	
}
