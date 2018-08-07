package framework;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.io.Writer;

import org.openqa.selenium.NoSuchWindowException;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.testng.annotations.Optional;
import org.testng.annotations.Parameters;
import org.testng.annotations.Test;

public class Common {
	@Parameters("browser")
	@Test()
	public void launchApplication(@Optional("chrome") String browser){
		
		
		switch (browser.toLowerCase()) {
		case "chrome":
			Data.Common.driver = new ChromeDriver();
			
			break;
		case "firefox":
			Data.Common.driver = new FirefoxDriver();
			break;
		default:
			break;
		}
		
		
		
	}
	
	
	public static void switchToMainWindow(){
		
		try{
			Data.Common.driver.switchTo().window(Data.Common.mainWindowHandle);
			
		} catch (NoSuchWindowException nsw){
			System.out.println("Main browser has been closed. Unable to switch to main window.");
		}
	}
	
	
	@Test
	 public void GenerateReport()
	    {
	        try {
	            //define a HTML String Builder
	            StringBuilder htmlStringBuilder=new StringBuilder();
	            //append html header and title
	            htmlStringBuilder.append("<html><head><title>Selenium Test </title></head>");
	            //append body
	            htmlStringBuilder.append("<body>");
	            //append table
	            htmlStringBuilder.append("<table border=\"1\" bordercolor=\"#000000\">");
	            //append row
	            htmlStringBuilder.append("<tr><td><b>TestId</b></td><td><b>TestName</b></td><td><b>TestResult</b></td></tr>");
	            //append row
	            htmlStringBuilder.append("<tr><td>001</td><td>Login</td><td>Passed</td></tr>");
	            //append row
	            htmlStringBuilder.append("<tr><td>002</td><td>Logout</td><td>Passed</td></tr>");
	            //close html file
	            htmlStringBuilder.append("</table></body></html>");
	            //write html string content to a file
	            WriteToFile(htmlStringBuilder.toString(),"testfile.html");
	        } catch (IOException e) {
	            e.printStackTrace();
	        }
	    }
	    public static void WriteToFile(String fileContent, String fileName) throws IOException {
	        String projectPath = System.getProperty("user.dir");
	        String tempFile = projectPath + File.separator+fileName;
	        File file = new File(tempFile);
	        // if file does exists, then delete and create a new file
	        if (file.exists()) {
	            try {
	                File newFileName = new File(projectPath + File.separator+ "backup_"+fileName);
	                file.renameTo(newFileName);
	                file.createNewFile();
	            } catch (IOException e) {
	                e.printStackTrace();
	            }
	        }
	        //write to file with OutputStreamWriter
	        OutputStream outputStream = new FileOutputStream(file.getAbsoluteFile());
	        Writer writer=new OutputStreamWriter(outputStream);
	        writer.write(fileContent);
	        writer.close();

	    }
	    
		

}
