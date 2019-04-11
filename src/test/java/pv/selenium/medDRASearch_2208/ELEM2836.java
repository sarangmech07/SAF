package pv.selenium.medDRASearch_2208;

import java.io.IOException;
import java.util.Hashtable;

import org.testng.SkipException;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.Test;

import com.relevantcodes.extentreports.LogStatus;

import pv.selenium.hybrid.keywords.Keywords;
import pv.selenium.hybrid.util.Constants;
import pv.selenium.hybrid.util.DataUtil;
import pv.selenium.hybrid.util.Xls_Reader;

public class ELEM2836 extends BaseTest{
	@BeforeTest
    public void init(){
       xls = new Xls_Reader(Constants.ELEM_2208);
       testName = "ELEM2836";
       /*Verify that when the user clicks on 
        * the MedDRA launcher component with search term, 
        * the MedDRA browser Modal should be launched with the 
        * same search term auto populated on the search panel
        */
       try
       {
           Runtime.getRuntime().exec("taskkill /F /IM chromedriver.exe");
       } catch (IOException e)
       {
           e.printStackTrace();
       }
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
