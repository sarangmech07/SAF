package pv.selenium.hybrid.util;

//import java.util.List;

import org.openqa.selenium.By;
//import org.openqa.selenium.JavascriptExecutor;
//import org.openqa.selenium.NoSuchElementException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
//import org.testng.Assert;

import com.relevantcodes.extentreports.ExtentTest;

import pv.selenium.hybrid.keywords.AppKeywords;

public class PviCaseCount extends AppKeywords {
	static int pagerows = 0;

	public PviCaseCount(ExtentTest test) {
		super(test);
	}
	// static ExtentTest test;

	public static int webPageCountPvi(String tabelLocator, WebDriver driver) throws ElementNotFoundException {
		PviCaseCount genkey = null;
		// driver=genkey.driver;
		genkey = new PviCaseCount(test);
		WebElement e = null;
		String num = null;
		int count = 0;

		try {

			if (genkey.isElementPresent("NoRecordFound_xpath")) {
				//// h4[contains(text(),'No record Found')]
				return count = 0;
			}

			else if (!genkey.isElementPresent(tabelLocator)) {
				count = tableRowCount();
				return count;
			}

			else {
				genkey.scrollTo(tabelLocator);
				e = genkey.getElement(tabelLocator);
				// e=driver.findElement(By.xpath("(//li[@ng-repeat='page in
				// pages']/a)[last()-1]"));
				tableRowCount();
				// System.out.println("First Page row count---"+pagerows);
				num = e.getText();
				count = Integer.parseInt(num);
				count = ((count - 1) * pagerows);
				e.click();
				Thread.sleep(5000L);
				tableRowCount();
				// System.out.println("Last Page row count---"+pagerows);
				count = count + pagerows;
				//System.out.println("Final count is---- " + count);
				return count;

			}

		}

		catch (NumberFormatException exc) {
			// Assert.fail("Can't find the table");
			exc.getMessage();
			return 0;

		} catch (InterruptedException exc) {
			// Assert.fail("Can't find the table");
			exc.getMessage();
			return 0;

		}
	}

	public static int tableRowCount() throws ElementNotFoundException {

		pagerows = 0;
		for (WebElement tr : driver.findElements(By.tagName("tr"))) {
			pagerows++;
			// System.out.println("Last page element are---"+lastpagerows);
		}
		
		if (pagerows == 0) {
			
			throw new ElementNotFoundException(Constants.FAIL + "Table not found");
		}
		return --pagerows;

	}

}
