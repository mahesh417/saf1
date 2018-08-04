package framework;

import org.testng.Assert;
import org.testng.annotations.BeforeSuite;
import org.testng.annotations.Optional;
import org.testng.annotations.Parameters;

public class Configuration {
	
	@Parameters("environment")
	@BeforeSuite
	public void getEnvDetails(@Optional String environment){
		
		environment = (environment==null)?"qa":environment;
		System.out.println(environment);
		
		switch (environment.toLowerCase()) {
		case "qa":
			Data.Common.envConfigData = UtilityMethods.readPropertiesToMap("Config\\env_qa.properties");
			break;
			
		case "dev":
			Data.Common.envConfigData = UtilityMethods.readPropertiesToMap("Config\\env_dev.properties");
			break;
			
		default:
			Assert.fail("The environment : " + environment + " is invalid. Environment must be either qa or dev.");
			break;
		}
		
		Data.Common.executionConfigData=UtilityMethods.readPropertiesToMap("Config\\execution_config.properties");
		
	}

}
