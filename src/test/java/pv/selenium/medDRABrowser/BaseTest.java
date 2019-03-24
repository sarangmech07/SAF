package pv.selenium.medDRABrowser;

import org.testng.annotations.AfterMethod;
import org.testng.annotations.DataProvider;

import com.relevantcodes.extentreports.ExtentReports;
import com.relevantcodes.extentreports.ExtentTest;

import pv.selenium.hybrid.keywords.Keywords;
import pv.selenium.hybrid.util.DataUtil;
import pv.selenium.hybrid.util.ExtentManager;
import pv.selenium.hybrid.util.Xls_Reader;

public class BaseTest {
	public ExtentReports rep = ExtentManager.getInstance();
	public ExtentTest test;
	public Keywords app;
	public Xls_Reader xls ;
	public String testName;
	
	
	
	/*
	 * @AfterMethod public void quit(){ if(rep!=null){ rep.endTest(test);
	 * rep.flush(); }
	 * 
	 * if(app!=null) app.getGenericKeyWords().closeBrowser(); }
	 */
	
	@DataProvider
	public Object[][] getData(){
		return DataUtil.getData(xls, testName);
	}
}
