package pv.selenium.medDRASearch_2208;

import java.util.Hashtable;

import org.testng.SkipException;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.Test;

import com.relevantcodes.extentreports.LogStatus;

import pv.selenium.hybrid.keywords.Keywords;
import pv.selenium.hybrid.util.Constants;
import pv.selenium.hybrid.util.DataUtil;
import pv.selenium.hybrid.util.Xls_Reader;

public class ELEM2844 extends BaseTest{
	
       @BeforeTest
       public void init(){
          xls = new Xls_Reader(Constants.ELEM_2208);
          testName = "ELEM2844";
          // Validate that the search panel is freezed when the search results are displayed
       }
       
        @Test(dataProvider="getData")
       public void copyReportMethod(Hashtable<String,String> data){
         test = rep.startTest(testName);
         test.log(LogStatus.INFO, data.toString());
         
         if(DataUtil.isSkip(xls, testName) || data.get("Runmode").equals("N")){
          test.log(LogStatus.SKIP, "Skipping the test as runmode is N");
          throw new SkipException("Skipping the test as runmode is N");
         }
         
         test.log(LogStatus.INFO, "Starting "+testName);
         
         app = new Keywords(test);
         test.log(LogStatus.INFO, "Executing keywords");
         app.executeKeywords(testName, xls,data);
         // add the screenshot
         //app.getGenericKeyWords().reportFailure("xxxx");
         test.log(LogStatus.PASS, "PASS");
         app.getGenericKeyWords().takeScreenShot();
       }

}