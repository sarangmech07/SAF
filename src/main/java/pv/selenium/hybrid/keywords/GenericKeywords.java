package pv.selenium.hybrid.keywords;

import java.awt.AWTException;
import java.awt.Robot;
import java.awt.event.KeyEvent;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Properties;
import java.util.Set;
import java.util.concurrent.TimeUnit;

import org.openqa.selenium.Alert;
import org.openqa.selenium.By;
import org.openqa.selenium.InvalidElementStateException;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.NoAlertPresentException;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.UnhandledAlertException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeDriverService;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.firefox.FirefoxOptions;
import org.openqa.selenium.firefox.FirefoxProfile;
import org.openqa.selenium.ie.InternetExplorerDriver;
import org.openqa.selenium.io.FileHandler;
import org.openqa.selenium.remote.CapabilityType;
import org.openqa.selenium.remote.DesiredCapabilities;
import org.openqa.selenium.remote.RemoteWebDriver;
import org.openqa.selenium.support.ui.ExpectedCondition;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.Select;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.Assert;

import com.relevantcodes.extentreports.ExtentTest;
import com.relevantcodes.extentreports.LogStatus;

import pv.selenium.hybrid.mail.SendMail;
import pv.selenium.hybrid.util.Constants;
import pv.selenium.hybrid.util.DateSelectCalender;
import pv.selenium.hybrid.util.EmailVerification;
import pv.selenium.hybrid.util.RandomStringGenerator;
import pv.selenium.hybrid.util.Xls_Reader;


public class GenericKeywords {
	public static WebDriver driver;
	public static WebDriverWait wait ;							   
	public static Properties prop;
	public static ExtentTest test;
	
	Xls_Reader xls;
	SendMail mail=new SendMail();
	
	public GenericKeywords(ExtentTest test) {
		this.test = test;
		prop = new Properties();
		try {
			FileInputStream fs = new FileInputStream(System.getProperty("user.dir") + "//src//pv//resources//project.properties");

			prop.load(fs);
		} catch (FileNotFoundException e) {
			e.getMessage();
		} catch (IOException e) {
			e.getMessage();
		}
	}
	
	@SuppressWarnings("deprecation")
	
	public String openBrowser(String browserType){
		test.log(LogStatus.INFO, "Opening Browser");
		if(prop.getProperty("grid").equals("Y")){
			DesiredCapabilities cap=null;
			if(browserType.equals("Mozilla")){
				cap = DesiredCapabilities.firefox();
				cap.setBrowserName("firefox");
				cap.setJavascriptEnabled(true);
				cap.setPlatform(org.openqa.selenium.Platform.WINDOWS);
				
			}else if(browserType.equals("Chrome")){
				 cap = DesiredCapabilities.chrome();
				 cap.setBrowserName("chrome");
				 cap.setPlatform(org.openqa.selenium.Platform.WINDOWS);
			}
			else if(browserType.equals("IE")){
				cap = DesiredCapabilities.internetExplorer();
				//DesiredCapabilities ieCapabilities = DesiredCapabilities.internetExplorer(); 
				cap.setBrowserName("internet explorer");
				//cap.setVersion("11");
				cap.setPlatform(org.openqa.selenium.Platform.WINDOWS);
			    //System.setProperty("webdriver.ie.driver", "D:\\Jars\\IEDriverServer.exe");
				// System.setProperty("webdriver.ie.driver", "D:\\PVQ Automation Framework\\Framework\\IEDriver\\IEDriverServer.exe");	
		        System.setProperty("webdriver.ie.driver", System.getProperty("user.dir")+"/Browser-Drivers/IEDriverServer.exe");
				 
			}
			try {
				driver = new RemoteWebDriver(new URL("http://localhost:4444/wd/hub"), cap);
			} catch (MalformedURLException e) {
				e.getMessage();
			}
		}else{		
				if(browserType.equals("Mozilla")){			    	
			    	System.setProperty("webdriver.gecko.driver", System.getProperty("user.dir")+"/Browser-Drivers/geckodriver.exe");
			    	
			    	 FirefoxOptions options = new FirefoxOptions();
			         try {
						options.setProfile(FirefoxDriverProfile());
					} catch (Exception e) {
						e.getMessage();
					}
					driver = new FirefoxDriver(options);
				}else if(browserType.equals("Chrome")){
					ChromeDriverService chSvc = new ChromeDriverService.Builder()
					          .usingDriverExecutable(new File( System.getProperty("user.dir")+"/Browser-Drivers/chromedriver.exe")).usingAnyFreePort().build(); 
					//ChromeOptions options = new ChromeOptions();
					Map<String, Object> prefs = new HashMap<String, Object>();
					prefs.put("profile.default_content_setting_values.notifications", 2);
					ChromeOptions options = new ChromeOptions();
					options.setExperimentalOption("prefs", prefs);
					//options.addArguments("--disable-notifications");
					options.setExperimentalOption("useAutomationExtension", false);
					//options.addArguments("user-data-dir=C:\\Users\\msharma\\AppData\\Local\\Google\\Chrome\\User Data\\Profile 1");
					driver =  new ChromeDriver(chSvc,options);
					driver.manage().timeouts().pageLoadTimeout(130, TimeUnit.SECONDS);
					driver.manage().timeouts().implicitlyWait(20, TimeUnit.SECONDS);
					
	 
				}else if(browserType.equals("IE")){
					DesiredCapabilities cap=null;
					cap = DesiredCapabilities.internetExplorer(); 
					cap.setBrowserName("internet explorer");
					cap.setVersion("11");
					   System.setProperty("webdriver.ie.driver",System.getProperty("user.dir")+"/Browser-Drivers/IEDriverServer.exe");		  
						 
					//System.setProperty("webdriver.ie.driver", "D:\\PVQ Automation Framework\\Framework\\IEDriver\\IEDriverServer.exe");		  
				    //System.setProperty("webdriver.ie.driver", "D:\\Jars\\IEDriverServer.exe");
					   
				    cap.setCapability(InternetExplorerDriver.INTRODUCE_FLAKINESS_BY_IGNORING_SECURITY_DOMAINS, true);
				    cap.setCapability(InternetExplorerDriver.ENABLE_PERSISTENT_HOVERING, false); 
				    cap.setCapability(InternetExplorerDriver.IE_ENSURE_CLEAN_SESSION, true); 
				    cap.setCapability(InternetExplorerDriver.NATIVE_EVENTS, false); 
				    cap.setCapability(InternetExplorerDriver.REQUIRE_WINDOW_FOCUS, true); 
				    cap.setCapability(CapabilityType.ACCEPT_SSL_CERTS, true);
				    cap.setJavascriptEnabled(true);
				    driver = new InternetExplorerDriver(cap);
				    driver.manage().timeouts().implicitlyWait(20, TimeUnit.SECONDS);
					
				}
		}
		
		//driver.manage().timeouts().implicitlyWait(10, TimeUnit.SECONDS);
		driver.manage().window().maximize();
		return Constants.PASS;
	}
	
	public static FirefoxProfile FirefoxDriverProfile() throws Exception {
		FirefoxProfile profile = new FirefoxProfile();
		profile.setPreference("browser.download.folderList", 2);
		profile.setPreference("browser.download.manager.showWhenStarting", false);
		profile.setPreference("browser.download.dir", "D:\\");
		profile.setPreference("browser.helperApps.neverAsk.openFile","text/csv,application/x-msexcel,application/excel,application/pdf,application/x-excel,application/vnd.ms-excel,image/png,image/jpeg,text/html,text/plain,application/msword,application/xml");
		profile.setPreference("browser.helperApps.neverAsk.saveToDisk", "text/csv,application/x-msexcel,application/excel,application/pdf,application/x-excel,application/vnd.ms-excel,image/png,image/jpeg,text/html,text/plain,application/msword,application/xml");
		profile.setPreference("browser.helperApps.alwaysAsk.force", false);
		profile.setPreference("browser.download.manager.showWhenStarting", false);
		profile.setPreference("browser.download.useDownloadDir", true);
		profile.setPreference("browser.download.manager.alertOnEXEOpen", false);
		profile.setPreference("browser.download.manager.focusWhenStarting", false);
		profile.setPreference("browser.download.manager.useWindow", false);
		profile.setPreference("browser.download.manager.showAlertOnComplete", false);
		profile.setPreference("browser.download.manager.closeWhenDone", false);
		profile.setPreference("pdfjs.disabled", true);
		return profile;
	}
	
	public String navigate(String urlKey) {
		try {
			test.log(LogStatus.INFO, "Navigating to " + prop.getProperty(urlKey));
			driver.get(prop.getProperty(urlKey));
			return Constants.PASS;
		} catch (Exception e) {
			return Constants.FAIL + " -- Webpage not found.";
		}

	}
	
	public String click(String locatorKey) {
		try {
			if(locatorKey.equals("Submit_xpath")) {
				test.log(LogStatus.INFO, "Clicking on " + prop.getProperty(locatorKey));
			}
			WebElement e = getElement(locatorKey);
			e.click();
			return Constants.PASS;
		} catch (InvalidElementStateException e) {
			return Constants.FAIL + " -- Not able to click on element as element is not in clickable state.";
		}
	}

	
	
	public String jsclick(String locatorKey) {
		try {
			test.log(LogStatus.INFO, "Using Javascript Clicking on " + prop.getProperty(locatorKey));
			WebElement e = getElement(locatorKey);
			JavascriptExecutor js = (JavascriptExecutor) driver;
			js.executeScript("arguments[0].click();", e);
			return Constants.PASS;
		} catch (Exception e) {
			return Constants.FAIL + " -- No javascript found or the arguments to the script may be empty.";
		}

	}
		
	// MG Keyword for clicking on a Module to perform any operation like click on Import Case Series
	public String selectModule(String data) {
		test.log(LogStatus.INFO, "Selecting the Module " + data);

		try {
			WebElement e = driver.findElement(By.id("navigationPane"));
			Thread.sleep(2000);

			List<WebElement> pvqmodules = e.findElements(By.tagName("span"));

			for (WebElement e1 : pvqmodules) {

				if (e1.getText().equals(data)) {

					e.click();
					break;
				} else {
					return Constants.FAIL + "-- Module not visible on Webpage.";
				}
			}
			return Constants.PASS;
		} catch (InterruptedException eee) {
			return Constants.FAIL + "-- Not element found with name 'span'.";
		}
	}
	
 
	// MG new keyword to select data from drop downs like Pinning Status or Query Category
	public String selectdrpdown(String locatorKey, String data) {
		try {
			test.log(LogStatus.INFO, "Selecting dropdown value from " + prop.getProperty(locatorKey));
			WebElement e = getElement(locatorKey);

			Select drpdown = new Select(e);
			drpdown.selectByVisibleText(data);
			return Constants.PASS;
		} catch (Exception e) {
			return Constants.FAIL + "-- No Dropdown found to select the element";
		}
	}
 
	// Keyword to select multiple values from a list box
	public String selectfrmlist(String locatorKey, String data) {
		test.log(LogStatus.INFO, "Selecting options from " + prop.getProperty(locatorKey));
		WebElement e = getElement(locatorKey);
		Select listbox = new Select(e);
		// Store all options available in list box
		List<WebElement> list_option = listbox.getOptions();
		String all_grps[] = new String[list_option.size()];

		// Storing all options available in the list
		for (int i = 0; i < list_option.size(); i++) {
			all_grps[i] = list_option.get(i).getText();
			System.out.println("available groups=" + all_grps[i]);
		}

		// select from list box - multiple values as well
		Robot rb;
		try {
			rb = new Robot();
			// read user input to identify the group names (if multiple)
			String usergrp[] = data.split(",");

			for (int k = 0; k < usergrp.length; k++) {
				System.out.println("user groups=" + usergrp[k]);
				for (int j = 0; j < all_grps.length; j++) {
					if (usergrp[k].trim().equals(all_grps[j])) {
						listbox.selectByVisibleText(usergrp[k].trim());
						rb.keyPress(KeyEvent.VK_CONTROL);
					}
				}
			}
			rb.keyRelease(KeyEvent.VK_CONTROL);

		} catch (AWTException e1) {
			return Constants.FAIL + " -- Not list found to select on webpage";
		}
			        
		return Constants.PASS;		
		
	}
	
	
	public String input(String locatorKey, String data) {
		try {
			test.log(LogStatus.INFO, "Typing in " + prop.getProperty(locatorKey));
			WebElement e = getElement(locatorKey);
			e.sendKeys(data);
			return Constants.PASS;
		} catch (InvalidElementStateException e) {
			return Constants.FAIL + "-- Not able to give input to the element as element is not active for user input.";
		}

	}
	
	public String inputDynamicText(String locatorKey, String data) {
		test.log(LogStatus.INFO, "Typing in " + prop.getProperty(locatorKey));

		try {
			String randomString = RandomStringGenerator.generateRandomString();
			int i = 0;
			// input types in data ---> Date,data or String,data or VarChar,data
			WebElement e = getElement(locatorKey);
			ArrayList<String> arrli = new ArrayList<String>(i);

			String inputtype[] = data.split(",");
			for (int n = 0; i <= inputtype.length; i++)
				arrli.add(inputtype[n]);

			Date now = new Date();
			DateFormat df = new SimpleDateFormat("ddMMYYHHmmss");

			// data shall be having following data- 'Date',data. Will produce a string
			// having date appended in it
			if (inputtype[0].equalsIgnoreCase("Date")) {
				String name = inputtype[1] + df.format(now);
				e.sendKeys(name);
			}
			// data column shall be having following data- String,data. Will produce a
			// string having random string appended in it

			else if (inputtype[0].equalsIgnoreCase("String")) {
				String name = inputtype[1] + randomString;
				e.sendKeys(name);
			}
			// data column shall be having following data- Email,data,@xyz.com. Will produce
			// a string(email) having username with appended random string in it
			else if (inputtype[0].equalsIgnoreCase("Email")) {
				// data shall be --> "Email,emailaddress,@xyz.com"
				String name = inputtype[1] + randomString + inputtype[2];
				e.sendKeys(name);
			}

			return Constants.PASS;
		} catch (InvalidElementStateException e) {
			return Constants.FAIL + "-- Either element not found or element not visible on webpage.";
		}
	}

	
	public String clearInput(String locatorKey) {
		try {
			test.log(LogStatus.INFO, "Typing in " + prop.getProperty(locatorKey));

			WebElement e = getElement(locatorKey);
			e.clear();
			return Constants.PASS;
		} catch (InvalidElementStateException e) {
			return Constants.FAIL + "-- Not able to clear input as element is not active for user to edit.";
		}

	}
	
	// To handle the alerts in an application
	public String alert(String data) {
		test.log(LogStatus.INFO, "Switching on Alert ");
		try {
			Alert sysalert = driver.switchTo().alert();
			// Accept the alert, click on 'OK' button in an alert
			if (data.equals("Accept")) {
				sysalert.accept();
				return Constants.PASS;
			} else if (data.equals("Reject") || data.equals("Cancel")) {
				sysalert.dismiss();
				return Constants.PASS;
			}
		} catch (Exception e) {
			return Constants.FAIL + " Alert not found";
		}
		return Constants.FAIL + " Either Alert not found or Check the testcase Data input for keyword";
	}
	
	// The Keyword will be used If any input is required to be passed in an alert
	

	// Required to switch to a particular frame.
	public String switchframe(String locatorKey) {
		try {
			// If no locator key is passed, goto default content of the page
			if (locatorKey.isEmpty()) {
				driver.switchTo().defaultContent();

			}
			// if locator key is passed, switch to that particular frame
			else {
				test.log(LogStatus.INFO, "Switching to frame " + prop.getProperty(locatorKey));
				WebElement e = getElement(locatorKey);
				driver.switchTo().frame(e);
			}

			return Constants.PASS;
		} catch (Exception e) {
			return Constants.FAIL + " No window frame found on webpage";
		}
	}
	
	
	public String switchWindow(String data) {
		try {
			String ParentWindow = null;
			String ChildWindow = null;
			String Child = "SecondaryWindow";
			ParentWindow = driver.getWindowHandle();
			Set<String> s1 = driver.getWindowHandles();
			Iterator<String> i1 = s1.iterator();
			while (i1.hasNext()) {
				ChildWindow = i1.next();
				if (!ParentWindow.equalsIgnoreCase(ChildWindow) & data.equalsIgnoreCase(Child)) {
					// Switching to Child window
					driver.switchTo().window(ChildWindow);
					return Constants.PASS;
				} else if (!ParentWindow.equalsIgnoreCase(ChildWindow) & data.equalsIgnoreCase("close")) {
					// Closing the Child window
					// Currently opened window will behave as a Parent window so passing parent
					// window id for closing the child window
					driver.switchTo().window(ParentWindow).close();
					// Now switching back to parent(main) window
					driver.switchTo().window(ChildWindow);
					return Constants.PASS;
				}

			}
			return Constants.FAIL;
		} catch (Exception e) {
			return Constants.FAIL + " No Window Found on webpage";
		}

	}
	
	public String closeBrowser() {
		test.log(LogStatus.INFO, "Closing browser");
		driver.quit();
		return Constants.PASS;

	}
	
	/***************************Validation keywords*********************************/
	public String verifyText(String locatorKey, String expectedText) {
		WebElement e = getElement(locatorKey);
		String actualText = e.getText();
		if (actualText.contains(expectedText))
		{
			System.out.println("Text matched");
			return Constants.PASS;
		}
		else if (expectedText.isEmpty()) {
			actualText.isEmpty();
			return Constants.PASS;
		} else
			return Constants.FAIL;
	}

	public String verifyElementPresent(String locatorKey) {
		boolean result = isElementPresent(locatorKey);
		if (result)
			return Constants.PASS;
		else
			return Constants.FAIL + " - Could not find Element " + locatorKey;
	}

	public String verifyElementNotPresent(String locatorKey) {
		boolean result = isElementPresent(locatorKey);
		if (!result)
			return Constants.PASS;
		else
			return Constants.FAIL + " - Could find Element " + locatorKey;
	}

	public String scrollTo(String locatorKey) {
		int y = getElement(locatorKey).getLocation().y;
		JavascriptExecutor js = (JavascriptExecutor) driver;
		js.executeScript("window.scrollTo(0," + (y + 250) + ")");
		return Constants.PASS;
	}
 
	public String wait(String timeout) {

		try {
			if(timeout.equals(""))
				timeout = "5000";
			Thread.sleep(Integer.parseInt(timeout));
		} catch (InterruptedException e) {
			e.getMessage();
		}
		return Constants.PASS;
	}
	
	/*Keyword to switch to windows(Secondary Window and Back to Main window)-
	 Switch to Secondary Window --> Put data as "SecondaryWindow" in data column for the keyword
	 Switch back to Main Window --> Put data as "PrimaryWindow" in data column for the keyword
	 Note:- No need to mention any object in the keyword but only need to pass 'data'.
	 */
    public String verifySwitchWindow(String data) {
        String comparePrimary="PrimaryWindow";
        String compareSecondary="SecondaryWindow";
          String MainWindow=driver.getWindowHandle();
            // To handle all new opened window.                     
         Set<String> s1=driver.getWindowHandles();          
         Iterator<String> i1=s1.iterator();           
       while(i1.hasNext()){       
             String ChildWindow=i1.next();
              if(!MainWindow.equalsIgnoreCase(ChildWindow) & data.equalsIgnoreCase(compareSecondary)){            
              // Switching to Child window
                     driver.switchTo().window(ChildWindow); 
                     return Constants.PASS;
                     }
              else if (data.equalsIgnoreCase(comparePrimary)) {
              // Switching to Parent window i.e Main Window.
                      driver.switchTo().window(MainWindow);    
                      return Constants.PASS;
                     }
              }
       return Constants.FAIL;
  } 

   /* Verify for Radio Button- If element is Selected or, De-selected 
    1) To verify if Radio button is selected- Put data as "select" in data column for the keyword
    2)  To verify if Radio button is selected- Put data as "deselect" in data column for the keyword
    3) LocaterKey shall be the object of radio button
   */
    public String verifySelectDeselect(String locatorKey, String data) {
            
            WebElement item = getElement(locatorKey);
           //System.out.println(item);
            boolean result= item.isSelected();
            if (data.equalsIgnoreCase("select"))
                  {
                  if(result==true)
                            return Constants.PASS;
                  else
                            return Constants.FAIL+" - Element "+ locatorKey +" is not selected";
                  }
            else if (data.equalsIgnoreCase("deselect"))
            {
                  if(result==false)
                    return Constants.PASS;
                  else
                    return Constants.FAIL+" - Element "+ locatorKey +" is selected";
            }
            else
                  return Constants.FAIL+" - Incorrect keyword (Please mention select / deselect)";
                                       
    }

 // Verify the selected date from date picker control
	public String verifyDate(String locatorKey, String expDate) {
		try {
			WebElement e = getElement(locatorKey);
			System.out.println(e);
			String actualDate = e.getAttribute("value");
			if (actualDate.contains(expDate))
				return Constants.PASS;
			else
				return Constants.FAIL;
		} catch (Exception e) {
			return Constants.FAIL + " -- not able to finf element with attribute name 'value'.";
		}
	}

    
	public String selectDate(String locatorKey, String data) {
		try {
			WebElement element1 = getElement(locatorKey);
			if (DateSelectCalender.dateSelection(element1, locatorKey, data, driver).equalsIgnoreCase(Constants.PASS)) {
				return Constants.PASS;
			} else
				return Constants.FAIL;
		} catch (Exception e) {
		}
		return Constants.FAIL + " -- Not able to find the Date field on webpage";
	}			 
	
	public String click_Js(String locatorKey) {
		JavascriptExecutor executor = (JavascriptExecutor) driver;
		executor.executeScript("arguments[0].click();", getElement(locatorKey));
		return Constants.PASS;
	}
    
	public String valueHolder(String locatorKey, String data) {
		String webText = null;
		String sheetName = "Data";
		int testStartRowNum = 1;
		Boolean bol = false;
		// int rowNum=0;
		WebElement element = getElement(locatorKey);
		webText = element.getText().trim();

		try {

			while (!Keywords.keywordXls.getCellData(sheetName, 0, testStartRowNum).equals(Keywords.testCaseName)) {
				testStartRowNum++;
			}

			int dataStartRowNum = testStartRowNum + 2;
			// rowNum=excelRowColumnCount.rowColumnCount(sheetName,
			// Keywords.keywordDataColumn, Keywords.keywordXls);
			bol = Keywords.keywordXls.setCellData(sheetName, Keywords.keywordDataColumn, dataStartRowNum, webText);
			if (bol == true)
				return Constants.PASS;
			else
				return Constants.FAIL + "-- Sheet or Column Name " + data + " not Found in excel sheet  " + sheetName;

		} catch (IOException e) {
			return Constants.FAIL + "-- Sheet or Column Name " + data + " not Found in excel sheet" + sheetName;

		}
	}
       
       /*This keyword shall be used to copy test case column data in to other test case column with the same name. Both the "Column Name" shall be same 
       i.e. column name from where data is getting copied and column name where data is getting pasted. "LocatorKey" shall be the name of test case where data is getting pasted and its value
        shall be given in 'OR' like any other keyword.
      */
	public String valueCopy(String locatorKey, String data) {
		String cellText = null;

		String sheetName = "Data";
		int testStartRowNum = 1;
		Boolean bol = false;
		// String inputtype[] = data.split(",",2);
		String copyToTestcaseInput = prop.getProperty(locatorKey); // TestCaseFrom
		String colName = data; // Column Name

		try {
			while (!Keywords.keywordXls.getCellData(sheetName, 0, testStartRowNum).equals(Keywords.testCaseName)) {
				testStartRowNum++;
			}

			int dataStartRowNum = testStartRowNum + 2;
			// rowNum=excelRowColumnCount.rowColumnCount(sheetName,
			// Keywords.keywordDataColumn, Keywords.keywordXls);
			cellText = Keywords.keywordXls.getCellDataRandom(sheetName, Keywords.keywordDataColumn, dataStartRowNum);
			if (cellText.equalsIgnoreCase("") || cellText.equalsIgnoreCase(null)) {
				return Constants.FAIL + " Sheet or Column Name " + colName + " not Found in excel sheet for testcase"
						+ Keywords.testCaseName;
			}

			testStartRowNum = 1;
			while (!Keywords.keywordXls.getCellData(sheetName, 0, testStartRowNum).equals(copyToTestcaseInput)) {
				testStartRowNum++;
			}

			dataStartRowNum = 0;
			dataStartRowNum = testStartRowNum + 2;

			bol = Keywords.keywordXls.setCellData(sheetName, Keywords.keywordDataColumn, dataStartRowNum, cellText);
			if (bol == true)
				return Constants.PASS;
			else
				return Constants.FAIL + "-- Sheet or Column Name " + colName + " not Found in excel sheet for testcase"
						+ copyToTestcaseInput;

		} catch (IOException e) {
			return Constants.FAIL + "-- Sheet or Column Name " + colName + " not Found in excel sheet" + sheetName;

		}

	}
       
       
	public String verifyEmailSubject(String locatorKey, String data) {
		try {
			if (EmailVerification.verifyEmailSubject(prop.getProperty(locatorKey), data)
					.equalsIgnoreCase(Constants.PASS)) {
				return Constants.PASS;
			}
		}
			catch (Exception e) {
				
		}
		return Constants.FAIL + " -- Data not present in email";
	}
       
	public String verifyEmailBody(String locatorKey, String data) {
		try {
			if (EmailVerification.verifyEmailBody(prop.getProperty(locatorKey), data)
					.equalsIgnoreCase(Constants.PASS)) {
				return Constants.PASS;
			} else
				return Constants.FAIL;
		} catch (Exception e) {
		}
		return Constants.FAIL + " -- Data not present in email";
	}
       
       

	
	/************************Utility Functions********************************/
	public WebElement getElement(String locatorKey) {
		WebElement e = null;
		try {
			if (locatorKey.endsWith("_id")) {
				e = driver.findElement(By.id(prop.getProperty(locatorKey)));
				elementHighlight(e);
			} else if (locatorKey.endsWith("_xpath")) {
				e = driver.findElement(By.xpath(prop.getProperty(locatorKey)));
				if(!locatorKey.equals("filterButton_xpath"))
					elementHighlight(e);
			} else if (locatorKey.endsWith("_name")) {
				e = driver.findElement(By.name(prop.getProperty(locatorKey)));
				elementHighlight(e);
			} else if (locatorKey.endsWith("_clsname")) {
				e = driver.findElement(By.className(prop.getProperty(locatorKey)));
				elementHighlight(e);
			} else {
				e = driver.findElement(By.xpath(prop.getProperty(locatorKey)));
				elementHighlight(e);
			}

			reportPass("Element Found - " + locatorKey);

		} catch (Exception ex) {
			// ex.getMessage();
			reportFailure(Constants.FAILURE_MSG + locatorKey);
			Assert.fail(Constants.FAILURE_MSG + locatorKey);

		}

		return e;

	}
	
	
 
  
	public boolean isElementPresent(String locatorKey) {
		List<WebElement> e = null;

		try {
			if (locatorKey.endsWith("_id"))
				e = driver.findElements(By.id(prop.getProperty(locatorKey)));
			else if (locatorKey.endsWith("_xpath"))
				e = driver.findElements(By.xpath(prop.getProperty(locatorKey)));

			else if (locatorKey.endsWith("_name"))
				e = driver.findElements(By.name(prop.getProperty(locatorKey)));

			if (e.size() == 0)
				return false;
			else
				return true;
		}

		catch (Exception ee) {
			return false;
		}

	}
	
	
	public By getLocation(String locatorKey) {
		By by = null;
		try {
			if (locatorKey.endsWith("_id")) {
				by = By.id(prop.getProperty(locatorKey));
			} else if (locatorKey.endsWith("_xpath")) {
				by = By.xpath(prop.getProperty(locatorKey));				
			} else if (locatorKey.endsWith("_name")) {
				by = By.name(prop.getProperty(locatorKey));
			} else if (locatorKey.endsWith("_clsname")) {
				by = By.className(prop.getProperty(locatorKey));
			} else {
				by = By.xpath(prop.getProperty(locatorKey));
			}
		} catch (Exception ex) {
			// ex.getMessage();
			reportFailure(Constants.FAILURE_MSG + locatorKey);
			Assert.fail(Constants.FAILURE_MSG + locatorKey);

		}

		return by;

	}
 
	public String verifyElementVisibleClickable(String locatorKey) {
		ExpectedCondition<Boolean> expectation = new ExpectedCondition<Boolean>() {
			public Boolean apply(WebDriver driver) {
				return ((JavascriptExecutor) driver).executeScript("return document.readyState").toString()
						.equals("complete");
			}
		};
		try {
			Thread.sleep(5000);
			WebDriverWait wait = new WebDriverWait(driver, 80);
			//System.out.println("Initial Starting time of Page Load -- " + currentTime());
			wait.until(expectation);
			//System.out.println("Page Loaded -- " + currentTime());
			//System.out.println("Checking for element visibility  --" + currentTime());
			// wait.until(ExpectedConditions.)
			wait.until(ExpectedConditions.visibilityOfElementLocated(getLocation(locatorKey)));
			//System.out.println("Checking if element is clickable  --" + currentTime());
			wait.until(ExpectedConditions.elementToBeClickable(getLocation(locatorKey)));
			Thread.sleep(5000);

			return Constants.PASS;
		} catch (Exception ex) {
			reportFailure("Timeout waiting for Page Load Request to complete. --- " + locatorKey);
			Assert.fail("Timeout waiting for Page Load Request to complete.");
			return Constants.FAIL + " -- Timeout due to Webpage not able to load on time";
		}
	}
	
	public String verifyElementVisibleNotClickable(String locatorKey) {
		ExpectedCondition<Boolean> expectation = new ExpectedCondition<Boolean>() {
			public Boolean apply(WebDriver driver) {
				return ((JavascriptExecutor) driver).executeScript("return document.readyState").toString()
						.equals("complete");
			}
		};
		try {
			Thread.sleep(5000);
			WebDriverWait wait = new WebDriverWait(driver, 80);
			//System.out.println("Initial Starting time of Page Load -- " + currentTime());
			wait.until(expectation);
			//System.out.println("Page Loaded -- " + currentTime());
			//System.out.println("Checking for element visibility  --" + currentTime());
			// wait.until(ExpectedConditions.)
			wait.until(ExpectedConditions.visibilityOfElementLocated(getLocation(locatorKey)));
			System.out.println("Checking if element is clickable  --" + currentTime());
			wait.until(ExpectedConditions.elementToBeClickable(getLocation(locatorKey)));
			Thread.sleep(5000);
			reportFailure("Timeout waiting for Page Load Request to complete. --- " + locatorKey);
			Assert.fail("Timeout waiting for Page Load Request to complete.");

			return Constants.FAIL;
		} catch (Exception ex) {
			return Constants.PASS;
		}
	}
	public String currentTime() {
		DateTimeFormatter dtf = DateTimeFormatter.ofPattern("yyyy/MM/dd HH:mm:ss");
		LocalDateTime now = LocalDateTime.now();
		return dtf.format(now);
		// System.out.println(dtf.format(now));
	}
	
	
	public String dateselectdrpdownPVI(String locatorKey, String data) {
		test.log(LogStatus.INFO, "Selecting dropdown value from " + prop.getProperty(locatorKey));

		if (!data.isEmpty() && data != null) {
			WebElement e = getElement(locatorKey);
			try {
				Select drpdown = new Select(e);
				drpdown.selectByVisibleText(data);
			} catch (Exception ex) {
				return Constants.FAIL + " -- Not able to select element from dropdown.";
			}
		}
		return Constants.PASS;
	}

    

		/******************************Reporting functions******************************/
		
	public void reportFailure(String failureMessage) {

		takeScreenShot();
		test.log(LogStatus.FAIL, failureMessage);
		mailNotification();
		
	}

	public void reportPass(String successMessage) {
		if (prop.getProperty("screenShotAtEveryStep").equals("Y")) {
			takeScreenShot();
			test.log(LogStatus.PASS, successMessage);
		}

	}
	
	public void mailNotification() {
		try {
			mail.mailConfig(Keywords.testCaseName, Keywords.testStepNum, Keywords.keywordName,Keywords.moduleName);
		} catch (IOException e) {
			e.getMessage();
		}
	}
	
	public void takeScreenShot() {
		// decide name - time stamp
		Date d = new Date();
		String screenshotFile = d.toString().replace(":", "_").replace(" ", "_") + ".png";
		String path = Constants.SCREENSHOT_PATH + screenshotFile;
		// take screenshot
		try {

			File srcFile = ((TakesScreenshot) driver).getScreenshotAs(OutputType.FILE);

			FileHandler.copy(srcFile, new File(path));

		} catch (IOException | UnhandledAlertException f) {
			try {
				Alert alert = driver.switchTo().alert();
				String alertText = alert.getText();
				alert.accept();
			} catch (NoAlertPresentException e) {
				e.getMessage();
			}
		}

		// embedding the screenshot in to report
		test.log(LogStatus.INFO, test.addScreenCapture(path));
	}

	public void elementHighlight(WebElement element) {
		if(prop.getProperty("highlightWebElements").equals("Y")) {
			for (int i = 0; i < 20; i++) {
			JavascriptExecutor js = (JavascriptExecutor) driver;
			js.executeScript(
					"arguments[0].setAttribute('style', 'display:inline;background: yellow; border: 2px solid red;');",
					element);

			}
		}
	}	

	public String radio(String locatorKey, String data) {

		String user_selection[] = data.split("\\s*(=>|,|\\s)\\s*");
		String user_key[] = locatorKey.split("_");
		locatorKey = user_key[0] + "_" + user_selection[0] + "_" + user_key[1];
		WebElement e = getElement(locatorKey);
		e.click();

		return Constants.PASS;
	}
	
	public String getJsValueAndCompare(String locatorKey,String data) {
		try {
			test.log(LogStatus.INFO, "Using Javascript Clicking on " + prop.getProperty(locatorKey));
			WebElement e = getElement(locatorKey);
			JavascriptExecutor js = (JavascriptExecutor) driver;
			String value = (String) js.executeScript("return arguments[0].value;", e);
			if(value.equals(data))
			return Constants.PASS;
		} catch (Exception e) {
			return Constants.FAIL + " -- No javascript found or the arguments to the script may be empty.";
		}
		return  Constants.FAIL;
	}

}
