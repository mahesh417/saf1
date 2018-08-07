package framework;

import java.util.HashMap;

import org.openqa.selenium.WebDriver;
import org.testng.log4testng.Logger;
import org.w3c.dom.Document;

import com.aventstack.extentreports.ExtentReports;
import com.aventstack.extentreports.ExtentTest;
import com.aventstack.extentreports.reporter.ExtentHtmlReporter;

public class Data {
	
	
	public static class Common{
		
		public static WebDriver driver;
		public static ExtentHtmlReporter htmlReporter;
	  	public static ExtentReports extent;
	  	public static ExtentTest logger;
		public static HashMap<String, String> envConfigData;
		public static HashMap<String, String> executionConfigData;
		public static Logger log4Logger;
		public static Document objectRepository;
		public static String mainWindowHandle;
		
		
	}

}
