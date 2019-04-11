package pv.selenium.hybrid.keywords;

import java.awt.AWTException;
import java.awt.Robot;
import java.awt.event.KeyEvent;
import java.io.File;
import java.util.HashMap;
import java.util.Hashtable;
import java.util.List;
import java.util.Map;

import org.openqa.selenium.Alert;
import org.openqa.selenium.By;
import org.openqa.selenium.Keys;
import org.openqa.selenium.NoAlertPresentException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.ui.Select;

import com.relevantcodes.extentreports.ExtentTest;
import com.relevantcodes.extentreports.LogStatus;

import pv.selenium.hybrid.util.Constants;
import pv.selenium.hybrid.util.DataBaseConnection;
import pv.selenium.hybrid.util.DatabaseException;
import pv.selenium.hybrid.util.ElementNotFoundException;
import pv.selenium.hybrid.util.FileRowReader;
import pv.selenium.hybrid.util.PviCaseCount;

public class AppKeywords extends GenericKeywords {
	// dataBaseConnection dbobj;

	public AppKeywords(ExtentTest test) {
		super(test);

	}

	public String verifyLoginDetails(Hashtable<String, String> testData) {
		// name
		// String expectedName=testData.get("Name");
		// Username
		// String expectedID=testData.get("Username");
		// Actual

		return Constants.PASS;

	}

	public String login(String username, String password) {

		getElement("loginLink_xpath").click();
		getElement("userName_xpath").sendKeys(username);
		getElement("password_xpath").sendKeys(password);
		getElement("loginButton_xpath").click();
		return Constants.PASS;

	}

	// Keyword is required to perform action on a dynamic webtable - Manage Queries,
	// Manage CaseSeries, Manage Reports Grid
	public String actionOnQuery(String locatorKey, String data) {
		int counter = 0;
		String queryInput[] = data.split(",");
		String userQuery = queryInput[1]; // store the query name on which action should be performed
		String userAction = queryInput[0]; // store action - execute/copy/edit
		String currentQueryName = null;
		try {
			test.log(LogStatus.INFO, "Find Query in " + prop.getProperty(locatorKey));
			Thread.sleep(3000);
		} catch (InterruptedException e1) {
			e1.getMessage();
		}

		WebElement e = getElement(locatorKey);
		// store all queries available in Manage Queries grid
		List<WebElement> allqueries = e.findElements(By.tagName("tr"));
		testend: for (WebElement row : allqueries) // traverse each row of web table
		{

			if (counter == 0) // 1st row which contains header
			{

			}
			// else if (counter % 2 != 0) // every odd number row contains Query/Case Series
			// name
			else {
				List<WebElement> queryrw = row.findElements(By.tagName("td")); // find all columns for each row
				List<WebElement> action = row.findElements(By.tagName("img"));// find actions = execute/export/copy/edit

				Integer countercol = 0;

				// Traverse each column for every Query
				for (WebElement querynum : queryrw) {

					if (currentQueryName != null && currentQueryName.trim().equals(userQuery.trim())
							&& countercol.equals(queryrw.size() - 1)) {

						// System.out.println("Query Found, trying to perform action");
						// System.out.println("total Image tags="+ action.size());
						for (WebElement we : action) {
							String imgsrc = we.getAttribute("src");
							// System.out.println("Image Tag="+ imgsrc);
							// if user action equals particular image tag
							if (imgsrc.contains(userAction)) {
								// System.out.println("Clicked on user action");
								we.click();
								break testend;
							}
						}
						countercol++;
					} else if (("manageReportGrid_clsname".equals(locatorKey) && countercol == 1)
							|| (locatorKey != "manageReportGrid_clsname" && countercol == 0)) {
						currentQueryName = querynum.getText(); // available query name on Manage Queries Grid
						// System.out.println("PVQ Object Name="+currentQueryName);
						if ("Select".equals(userAction) && currentQueryName.trim().equals(userQuery.trim())) {
							WebElement tableuserquery = querynum.findElement(By.tagName("div"));
							tableuserquery.click();
							// query_num.click();
							break testend;
						}
						countercol++;
					} else {
						countercol++;
					}
				}
			}
			counter++;
		}
		return Constants.PASS;
	}

	// Save the downloads in Default download location and move it to TestOutput
	// location of Framework
	public String saveVerifyOutputFile(String locatorKey, String data) {
		// split the data by delimiter to identify name and output file location
		String csoutput[] = data.split(",");
		String outputfilename = csoutput[1]; // file name with which the export will be stored in the testoutput folder
												// of framework
		String expectedcsname = csoutput[0]; // contains user attribute - cs/query/report name
		String outputfilepath = Constants.FILE_DOWNLOAD_PATH + outputfilename;
		// test.log(LogStatus.INFO,"Saving the File ="+expectedcsname + " at location=
		// "+ outputfilepath);
		// output_file_path

		// System.out.println("Path="+prop.getProperty(locatorKey));

		try {
			Robot rw = new Robot();
			// Press from Keyboard - command - Alt+S to save the export
			rw.keyPress(KeyEvent.VK_ALT);
			rw.keyPress(KeyEvent.VK_S);
			rw.keyRelease(KeyEvent.VK_ALT);
			rw.keyRelease(KeyEvent.VK_S);
			Thread.sleep(5000);
			test.log(LogStatus.INFO, "Saving the File =" + expectedcsname + " at location= " + outputfilepath);
			// 14May- Verify file is saved successfully after save operation at default
			// download location (should be "D" drive)
			File fname = new File(prop.getProperty(locatorKey));

			// File fname = new File("D:\\Export_Case_Series_CSV.xlsx");
			if (fname.exists()) {

				// renaming the file and moving it to a new location - desired user location
				if (fname.renameTo(new File(outputfilepath.trim()))) {
					// if file gets copied successfully then delete file at original location
					fname.delete();
				}
			}
		} catch (AWTException | InterruptedException e) {
			e.getMessage();
		}

		return Constants.PASS;
	}

	// SQL Query shall return only 1 column having case count and column should be
	// named as "count (CASE_MASTER.CASE_ID)".
	// Also, Please make sure not to append semi colon ";" at the end of Query.
	public String queryCountVerify(String locatorKey, String data) {
		String pvqCountString = null;
		String safetyQueryCount = null;

		try {
			WebElement e = getElement(locatorKey);
			pvqCountString = e.getText().trim();
			// System.out.println("Count from PVQ---> "+pvqCountString);
			safetyQueryCount = DataBaseConnection.executeSQL(data);
			System.out.println("Output from Safety Query---> " + safetyQueryCount);
			if (safetyQueryCount.equals(pvqCountString))
				return Constants.PASS;
			else
				return Constants.FAIL + " -- Query Count not Matching between Safety and PVQ Mart";
		} catch (DatabaseException e) {

			return e.getMessage();
		}
	}

// keyword to do selection for lists which has elements with checkbox/radio buttons like on Groups/Users screen of business config
//	public String selectfrmComboListItems(String locatorKey, String data)
	public String selectfrmPromptListItems(String locatorKey, String data) {

		// split data to see if multiple values are being passed by user for selection -
		// delimiter - comma
		String user_selection[] = data.split(",");
		// split locator to get the tagname and locator key in OR - delimiter - Pipe
		String user_key[] = locatorKey.split("\\|");

		// After split, the 1st string contains the tagname which contains the elements
		// in a list/prompt
		String tag = user_key[0];

		// After split, the 2nd string contains the locator key of list which contains
		// all elements available in OR
		String locatorKey1 = user_key[1].trim();

		// contains the element using its xpath/id/name
		WebElement e = getElement(locatorKey1);
		test.log(LogStatus.INFO, "Selecting options from " + prop.getProperty(locatorKey1));

		// contains all the elements of the prompt identified with its tagname
		List<WebElement> prompt_list = e.findElements(By.tagName(tag));

		// run the loop for all the values passed in data
		for (int j = 0; j < user_selection.length; j++) {
			// System.out.println("user selection="+user_selection[j]);
			for (WebElement e1 : prompt_list) {
				// if the user value matches with prompt item list value then click it
				if (e1.getText().contains(user_selection[j].trim())) {
					// click on the value passed in Data
					e1.click();
					break;
				}
			}
		}
		return Constants.PASS;
	}

	// Created on 14May and required to be used for actions on Adhoc Reporting
	public String doubleClick(String locatorKey) {

		WebElement e = getElement(locatorKey);
		try {
			test.log(LogStatus.INFO, "DoubleClicking on " + locatorKey);
			Actions action = new Actions(driver);
			action.doubleClick(e).perform();
			Thread.sleep(5000);
		} catch (InterruptedException e1) {
			return Constants.FAIL + " Alert Not Found";
		}
		return Constants.PASS;
	}

	// Disable browser notifications

	public String inputInAlert(String data) {

		// data can accept 2 strings - 1st contain the action on alert and 2nd contain
		// any input to be passed in alert (if required)
		try {
			test.log(LogStatus.INFO, "Switching on Alert ");
			String alertinput[] = data.split(",");
			// data can accept 2 strings - 1st contain the action on alert
			String alertaction = alertinput[0];
			// System.out.println("Alert Action="+alertaction);
			// data can accept 2 strings - 2nd contain the any input to be passed in alert
			// (if required) like report name in case of copy
			String alerttxt = alertinput[1];
			// System.out.println("Alert Text="+alerttxt);
			Alert alt = driver.switchTo().alert();
			alt.sendKeys(alerttxt);
			if ("Accept".equals(alertaction))
				alt.accept();
			// accepting the alert again since for manage report copy, dialog for copy still
			// appears
			driver.switchTo().alert().accept();

			return Constants.PASS;
		} catch (NoAlertPresentException e) {
			return Constants.FAIL + "  Alert not found";
		}
	}

	/**
	 * We have put two conditions in this keyword to verify PVI application data
	 * (from UI) with either database or with export file. That's why we need to
	 * mention either "File" or "DataBase" in the testcase data file to tell the
	 * keyword for data verification form corresponding location (data file or SQL
	 * query).
	 * 
	 * 'locatorykey' shall be the address of the table footer element containing
	 * total number of pagination i.e second last element of table footer in PVI
	 * case as last element is '<<' . As, element representing total page count will
	 * be dynamic in nature(depend on user) so we need to give generic
	 * address(locatorkey) in such a way that 'locatorkey' always point to the
	 * second last element of the table pagination footer irrespective to the total
	 * number of pagination in the table.So, Always use 'last()-1' as will always
	 * point to the second last element of the table footer as last element is '<<'.
	 * So in PVI reference, locatorkey always will be '(//li[@ng-repeat='page in
	 * pages']/a)[last()-1]' which will always point to the last pagination number
	 * of the table
	 * 
	 * Data of testcase i.e. from test data sheet will be split in to two parts-
	 * data1 and data2 where 'data1' will have two discrete values in testcase data
	 * sheet:- i) DataBase --> Will be appended before SQL query(data2) with ","
	 * separated. e.g. DataBase,Select * form Table (Here "data1" will be 'DataBase'
	 * and 'data2' will be the SQL query separated by ",") ii) File --> Will be
	 * having the path of the file from where we need to count the records. e.g.
	 * File,D:\\csvfile.csv (Here, "data1" will be 'File' and 'data2' will be the
	 * path of the file)
	 */
	public String pviCountVerify(String locatorKey, String data) {
		int dataBaseCountPvi = 0;
		String queryCountPvi = null;
		int totalCountPviPage = 0;
		int rowCount = 0;
		String verifyMode[];
		String data1 = "";
		String data2 = "";

		try {
			if (data != null && data.contains(",")) {
				verifyMode = data.split(",", 2);
				data1 = verifyMode[0];
				data2 = verifyMode[1];
			} else {
				return Constants.FAIL + "-- Either 'DataBase,' is not appended before SQL query or dataset is missing";
			}
			// locator key shall be the xpath of table without list of elements'li'
			totalCountPviPage = PviCaseCount.webPageCountPvi(locatorKey, driver);
			if ("DataBase".equalsIgnoreCase(data1)) {
				System.out.println("Count from PVI---> " + totalCountPviPage);
				queryCountPvi = DataBaseConnection.executeSQL(data2);
				dataBaseCountPvi = Integer.parseInt(queryCountPvi);
				// System.out.println("Output from DataBase Query---> "+dataBaseCountPvi);
				if (totalCountPviPage == dataBaseCountPvi)
					return Constants.PASS;
				else
					return Constants.FAIL + " -- Count between Database and Application is not matching";
			}

			else if ("File".equalsIgnoreCase(data1)) {
				// data2 shall be the path to the file where row count needs to be calculated
				rowCount = FileRowReader.getFileRowCount(data2);
				// System.out.println("Row count form CSV file-----"+rowCount);
				if (totalCountPviPage == rowCount)
					return Constants.PASS;

				else
					return Constants.FAIL + " -- Count between File and Application is not matching";
			}

		} catch (DatabaseException e1) {
			// test.log(LogStatus.INFO,");
			return e1.getMessage();
		}

		catch (ElementNotFoundException e1) {
			// test.log(LogStatus.INFO,");
			return e1.getMessage();
		} catch (Exception e1) {
			// test.log(LogStatus.INFO,");
			return Constants.FAIL
					+ " -- Not able to connect to Database. Either check your Database credentials or Verify if VPN is connected.";
		}

		return Constants.FAIL + " -- Keyword data not properly given. Please verify keyword inputs";

	}

	public String dateselectdrpdownPVI(String locatorKey, String data) {
		test.log(LogStatus.INFO, "Selecting dropdown value from " + prop.getProperty(locatorKey));

		if (!data.isEmpty() && data != null) {
			WebElement e = getElement(locatorKey);
			try {
				Select drpdown = new Select(e);
				drpdown.selectByVisibleText(data);
			} catch (Exception ex) {
				return Constants.FAIL;
			}
		}
		return Constants.PASS;
	}

	public String pviIEdownload() {
		try {
			Robot robotObj = new Robot();
			Thread.sleep(5000);
			robotObj.keyPress(KeyEvent.VK_ALT);
			robotObj.keyPress(KeyEvent.VK_S);
			robotObj.keyRelease(KeyEvent.VK_S);
			robotObj.keyRelease(KeyEvent.VK_ALT);

			Thread.sleep(10000);

		} catch (AWTException ex) {
			ex.printStackTrace();
		} catch (InterruptedException ex) {
			ex.printStackTrace();
		}
		return Constants.PASS;
	}

	public String isSearchKeywordPresent(String locatorKey, String data) {
		String xpath = prop.getProperty(locatorKey);
		List<WebElement> containerList = driver.findElements(By.xpath(xpath));
		for (WebElement container : containerList) {
			List<WebElement> links = container.findElements(By.xpath("./descendant::a"));
			for (WebElement link : links) {
				if (!link.getText().startsWith(data) && !link.getText().contains("Show"))
					return Constants.FAIL;
			}
		}
		return Constants.PASS;
	}
	public String isSearchKeywordContained(String locatorKey, String data) {
		String xpath = prop.getProperty(locatorKey);
		xpath = "//div[contains(@class,'cMeddraResultPanel')]/div";
		List<WebElement> containerList = driver.findElements(By.xpath(xpath));
		for (WebElement container : containerList) {
			List<WebElement> links = container.findElements(By.xpath("./descendant::a"));
			for (WebElement link : links) {
				if (!link.getText().contains(data) && !link.getText().contains("Show"))
					return Constants.FAIL;
			}
		}
		return Constants.PASS;
	}

}
