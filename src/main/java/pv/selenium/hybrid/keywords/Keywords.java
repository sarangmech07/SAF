package pv.selenium.hybrid.keywords;

import java.util.Date;
import java.util.Hashtable;
import org.testng.Assert;
import pv.selenium.hybrid.util.Constants;
import pv.selenium.hybrid.util.RetrieveModuleName;
import pv.selenium.hybrid.util.Xls_Reader;
import com.relevantcodes.extentreports.ExtentTest;
import com.relevantcodes.extentreports.LogStatus;

public class Keywords {

	ExtentTest test;
	AppKeywords app;
	static public String testCaseName;
	static public String keywordName;
	static public String elementName;
	static public int testStepNum;
	static public String keywordDataColumn;
	static public Xls_Reader keywordXls;
	static public String exchelSheetModulepath;
	static public String moduleName;
	static public Date dateNow;
	static public String executionStartTime;

	public Keywords(ExtentTest test) {
		this.test = test;
		
	}

	public void executeKeywords(String testUnderExecution, Xls_Reader xls, Hashtable<String, String> testData) {

		app = new AppKeywords(test);

		int rows = xls.getRowCount(Constants.KEYWORDS_SHEET);

		for (int rNum = 2; rNum <= rows; rNum++) {
			String tcid = xls.getCellData(Constants.KEYWORDS_SHEET, Constants.TCID_COL, rNum);
			exchelSheetModulepath = xls.path;
			moduleName = RetrieveModuleName.reteriveModuleName();
			testCaseName = tcid;
			testStepNum = rNum;
			keywordXls = xls;

			if (tcid.equals(testUnderExecution)) {
				String keyword = xls.getCellData(Constants.KEYWORDS_SHEET, Constants.KEYWORD_COL, rNum);
				keywordName = keyword;
				String object = xls.getCellData(Constants.KEYWORDS_SHEET, Constants.OBJECT_COL, rNum);
				elementName = object;
				String key = xls.getCellData(Constants.KEYWORDS_SHEET, Constants.DATA_COL, rNum);
				keywordDataColumn = key;
				String data = testData.get(key);
				test.log(LogStatus.INFO, "Test Name:  " + tcid + " || Step Row Number :  " + rNum + " ||  Keyword:  "
						+ keyword + " || Element:  " + object + " ||  User Input:  " + data);
				System.out.println( "Test Name:  " + tcid + " || Step Row Number :  " + rNum
						+ " ||  Keyword:  " + keyword + " || Element:  " + object + " ||  User Input:  " + data +" TimeStamp:" + new Date());
				String result = "";
				if ("openBrowser".equalsIgnoreCase(keyword))
					result = app.openBrowser(data);
				else if ("navigate".equalsIgnoreCase(keyword))
					result = app.navigate(object);
				else if ("click".equalsIgnoreCase(keyword))
					result = app.click(object);
				else if ("input".equalsIgnoreCase(keyword))
					result = app.input(object, data);
				else if ("inputDynamicText".equalsIgnoreCase(keyword))
					result = app.inputDynamicText(object, data);
				else if ("clearInput".equalsIgnoreCase(keyword))
					result = app.clearInput(object);
				else if ("closeBrowser".equalsIgnoreCase(keyword))
					result = app.closeBrowser();
				else if ("verifyLoginDetails".equalsIgnoreCase(keyword))
					result = app.verifyLoginDetails(testData);
				else if ("verifyElementPresent".equalsIgnoreCase(keyword))
					result = app.verifyElementPresent(object);
				else if ("verifyElementNotPresent".equalsIgnoreCase(keyword))
					result = app.verifyElementNotPresent(object);
				else if ("verifyText".equalsIgnoreCase(keyword))
					result = app.verifyText(object, data);
				else if ("wait".equalsIgnoreCase(keyword))
					result = app.wait(key);
				else if ("scrollTo".equalsIgnoreCase(keyword))
					result = app.scrollTo(object);
				else if ("switchframe".equalsIgnoreCase(keyword))
					result = app.switchframe(object);
				else if ("switchWindow".equalsIgnoreCase(keyword))
					result = app.switchWindow(data);
				else if ("selectModule".equalsIgnoreCase(keyword))
					result = app.selectModule(data);
				else if ("selectdrpdown".equalsIgnoreCase(keyword))
					result = app.selectdrpdown(object, data);
				else if ("selectfrmlist".equalsIgnoreCase(keyword))
					result = app.selectfrmlist(object, data);
				else if ("actionOnQuery".equalsIgnoreCase(keyword))
					result = app.actionOnQuery(object, data);
				else if ("saveVerifyOutputFile".equalsIgnoreCase(keyword))
					result = app.saveVerifyOutputFile(object, data);
				else if ("alert".equalsIgnoreCase(keyword))
					result = app.alert(data);
				else if ("queryCountVerify".equalsIgnoreCase(keyword))
					result = app.queryCountVerify(object, data);
				else if ("inputInAlert".equalsIgnoreCase(keyword))
					result = app.inputInAlert(data);
				else if ("selectfrmPromptListItems".equalsIgnoreCase(keyword))
					result = app.selectfrmPromptListItems(object, data);
				else if ("verifySelectDeselect".equalsIgnoreCase(keyword))
					result = app.verifySelectDeselect(object, data);
				else if ("verifyDate".equalsIgnoreCase(keyword))
					result = app.verifyDate(object, data);
				else if ("selectDate".equalsIgnoreCase(keyword))
					result = app.selectDate(object, data);
				else if ("pviCountVerify".equalsIgnoreCase(keyword))
					result = app.pviCountVerify(object, data);
				else if ("radio".equalsIgnoreCase(keyword))
					result = app.radio(object, data);
				else if ("jsclick".equalsIgnoreCase(keyword))
					result = app.jsclick(object);
				else if ("click_Js".equalsIgnoreCase(keyword))
					result = app.click_Js(object);
				else if ("dateselectdrpdownPVI".equalsIgnoreCase(keyword))
					result = app.dateselectdrpdownPVI(object, data);
				else if ("verifyElementVisibleClickable".equalsIgnoreCase(keyword))
					result = app.verifyElementVisibleClickable(object);
				else if ("verifyElementVisibleNotClickable".equalsIgnoreCase(keyword))
					result = app.verifyElementVisibleNotClickable(object);
				else if ("valueHolder".equalsIgnoreCase(keyword))
					result = app.valueHolder(object, data);
				else if ("valueCopy".equalsIgnoreCase(keyword))
					result = app.valueCopy(object, data);
				else if ("verifyEmailSubject".equalsIgnoreCase(keyword))
					result = app.verifyEmailSubject(object, data);
				else if ("verifyEmailBody".equalsIgnoreCase(keyword))
					result = app.verifyEmailBody(object, data);
				else if ("pviIEdownload".equalsIgnoreCase(keyword))
					result = app.pviIEdownload();
				else if ("isSearchKeywordPresent".equalsIgnoreCase(keyword))
					result = app.isSearchKeywordPresent(object,data);
				else if ("isSearchKeywordContained".equalsIgnoreCase(keyword))
					result = app.isSearchKeywordContained(object,data);
				else if ("isElementPresent".equalsIgnoreCase(keyword))
					result = app.isElementPresent(object) == true ? "PASS" : "FAIL";
				else if("getJsValueAndCompare".equalsIgnoreCase(keyword))
					result = app.getJsValueAndCompare(object,data);

				// central place reporting failure
				if (!result.equals(Constants.PASS)) {
					app.reportFailure(result);
					Assert.fail(result);

				} else {
					app.reportPass(result);
				}
			}
		}

	}

	public AppKeywords getGenericKeyWords() {
		return app;
	}

}
