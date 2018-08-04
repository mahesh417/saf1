package framework;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.net.InetAddress;
import java.net.UnknownHostException;
import java.text.SimpleDateFormat;
import java.util.Base64;
import java.util.Date;
import java.util.Properties;

import org.apache.commons.io.FileUtils;
import org.apache.log4j.PropertyConfigurator;
import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebElement;
import org.testng.ITestContext;
import org.testng.annotations.AfterTest;
import org.testng.annotations.BeforeTest;
import org.testng.log4testng.Logger;

import com.aventstack.extentreports.ExtentReports;
import com.aventstack.extentreports.MediaEntityBuilder;
import com.aventstack.extentreports.Status;
import com.aventstack.extentreports.reporter.ExtentHtmlReporter;
import com.aventstack.extentreports.reporter.configuration.ChartLocation;
import com.aventstack.extentreports.reporter.configuration.Theme;

public class Reporter extends Configuration {
	
	@BeforeTest
	 public void initiateReport(ITestContext ctx) throws IOException{
		
		 // setlog4jPropertyFile(ctx);
		
		 UtilityMethods.makePath(System.getProperty("user.dir")+Data.Common.executionConfigData.get("screenshotPath"));
		 String timeStamp = new SimpleDateFormat("yyyy.MM.dd.HH.mm.ss").format(new Date());
		 String logFilePath = System.getProperty("user.dir")+"\\"+Data.Common.executionConfigData.get("extentReportPath") +"\\" + ctx.getSuite().getName() + " - " + timeStamp +".html";
		 System.out.println(logFilePath);
		 Data.Common.htmlReporter = new ExtentHtmlReporter(logFilePath);
		 Data.Common.extent = new ExtentReports();
		
		 Data.Common.extent.attachReporter(Data.Common.htmlReporter);
	 
		 try {
			 
			 Data.Common.extent.setSystemInfo("Host Name", InetAddress.getLocalHost().getHostName());
			 Data.Common.extent.setSystemInfo("IP Address", InetAddress.getLocalHost().getHostAddress());
			 Data.Common.extent.setSystemInfo("User Name", System.getProperty("user.name"));	
			
		} catch (UnknownHostException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	 
		 Data.Common.htmlReporter.config().setDocumentTitle("Execution results for Suit : " + ctx.getSuite().getName() + " - " + timeStamp );
		 Data.Common.htmlReporter.config().setReportName("Report for Test : " + ctx.getSuite().getName());
		 Data.Common.htmlReporter.config().setTestViewChartLocation(ChartLocation.TOP);
		 Data.Common.htmlReporter.config().setTheme(Theme.STANDARD);
	 }

//**************************************************************************************************
	
	 @AfterTest
	 public void endReport(){
		 Data.Common.extent.flush();
	   }

	
//***********************************************************************************************
	/**
	 * Takes a  screenshot when there is an assertion failure
	 * @param locator - The current ID, Xpath etc. that has not been found on the page
	 */
	public String getScreenshot(String locator) 
		{	
			System.out.println("Taking screenshot of the page with the locator " + locator);
			//Remove illegal file name characters from the locator
			String legalNamelocator = locator.toString().replaceAll("[^a-zA-Z0-9\\._]+", "");
			//Take a screenshot of the current page
			String screenShotDir = System.getProperty("user.dir")  + "\\"+Data.Common.executionConfigData.get("screenshotPath")+"\\";				
			File screenshot = ((TakesScreenshot)Data.Common.driver).getScreenshotAs(OutputType.FILE);	
			String newFilePath = screenShotDir + legalNamelocator + "_" +  new SimpleDateFormat("MM.dd_HH.mm").format(new Date())+".png";
			File newFileName = new File(newFilePath);
			
			try
			{
				FileUtils.moveFile(screenshot, newFileName);
			} 
			catch (IOException ex) 
			{
				ex.printStackTrace();
			}
			
			System.out.println(newFilePath);
			return newFilePath;
			
		}//End screenShot (String locator)
		
	//*******************************************************************************
	
	public static void highlightElement(By by,String stepName, int maxTimeToWait){
		WebElement element =	Events.waitForElementToDisplay(by,stepName,maxTimeToWait);
        JavascriptExecutor jse = (JavascriptExecutor) Data.Common.driver;
        jse.executeScript("arguments[0].style.border='3px solid red'", element);
    }
	
	//*******************************************************************************
	
		public static void highlightElement(WebElement element,String stepName, int maxTimeToWait){
	        JavascriptExecutor jse = (JavascriptExecutor) Data.Common.driver;
	        jse.executeScript("arguments[0].style.border='3px solid red'", element);
	    }
	
	
	//*******************************************************************************
	
	public String captureScreenshotToBase64(String imageName) {
		String encodedfile="";
		String ImagePath = getScreenshot(imageName);
		
		try {
			File imageFile = new File(ImagePath);
            FileInputStream fileInputStreamReader = new FileInputStream(imageFile);
            byte[] bytes = new byte[(int)imageFile.length()];
            fileInputStreamReader.read(bytes);
            //encodedfile = Base64.encode(bytes).toString();
            encodedfile = Base64.getEncoder().encodeToString(bytes);
            fileInputStreamReader.close();
        } catch (FileNotFoundException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        } catch (IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
				
		return encodedfile;
		
		
	}
	
//*************************************************************************************************
	
	public void writeLog(String status, String stepName, String actualResult) {
		Status stepStatus;
		
		switch (status.toLowerCase()) {
			case "pass":
				stepStatus= Status.PASS;
				break;
			case "fail":
				stepStatus=Status.FAIL;
				break;
			
			case "warning":
				stepStatus=Status.WARNING;
				break;	
			
			default:
				stepStatus=Status.INFO;
				break;
			
		}
		 
		Data.Common.logger.log(stepStatus, stepName  + '\n' + '\n'+  actualResult ); 
		 
	}

	//*********************************************************************************************
	
	public void writeLog(String status, String stepName, String actualResult,String screenshotName) {
		Status stepStatus;
		
		switch (status.toLowerCase()) {
			case "pass":
				stepStatus= Status.PASS;
				break;
			case "fail":
				stepStatus=Status.FAIL;
				break;
			
			case "warning":
				stepStatus=Status.WARNING;
				break;	
			
			default:
				stepStatus=Status.INFO;
				break;
			
		}
		 
		try {
			Data.Common.logger.log(stepStatus, "Step Name : " + stepName + '\n' + '\n'+ "Actual Result : " + actualResult,MediaEntityBuilder.createScreenCaptureFromBase64String(captureScreenshotToBase64(screenshotName)).build());
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} 
		 
	}
	
	
	public void setlog4jPropertyFile(ITestContext ctx) throws IOException{
		
		Data.Common.log4Logger = Logger.getLogger(ctx.getClass());
		String log4JPropertyFile = "Config/log4j.properties";
		Properties p = new Properties();

		try {
		    p.load(new FileInputStream(log4JPropertyFile));
		    PropertyConfigurator.configure(p);
		    
		} catch (IOException e) {
		    System.out.println("Failed to log the log4j.properties file in Config foder.");
		    throw new IOException("failed to load the log4j.properties.");
		}
	}
		
}
