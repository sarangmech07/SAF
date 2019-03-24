package pv.selenium.hybrid.util;

import java.util.Arrays;
import java.util.List;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.*;

public class DateSelectCalender {

	public static String dateSelection(WebElement datePicker, String locatorKey, String expDate, WebDriver driver) {

		List<WebElement> noOfColumns;
		boolean dateNotFound = true;
		// WebElement datePicker;
		WebElement monthYear;

		// Expected Day, Month and Year
		String[] date = expDate.split("-");
		String[] selectedDate;
		String expDay = date[0];
		String expMonth = date[1];
		String expYear = date[2];
		// System.out.println(expDay);
		// System.out.println(expMonth);
		// System.out.println(expYear);

		List<String> monthList = Arrays.asList("January", "February", "March", "April", "May", "June", "July", "August",
				"September", "October", "November", "December");

		// Current Calendar Month and Year
		String calMonth;
		String calYear;
		while (dateNotFound) {

			// Retrieve current selected month and year name from date picker popup.
			monthYear = driver.findElement(By.xpath("//*[@class='btn btn-default btn-sm uib-title']"));
			selectedDate = monthYear.getText().split(" ");
			calMonth = selectedDate[0];
			calYear = selectedDate[1];

			// System.out.println(calMonth);
			// System.out.println(calYear);
			// System.out.println(expMonth);
			// System.out.println(expYear);

			// If current selected month and year are same as expected month and year then
			// go Inside this condition.
			if (expMonth.equalsIgnoreCase(calMonth) && expYear.equalsIgnoreCase(calYear)) {
				// Select the day of the month and set dateNotFound flag to false.
				// datePicker = getElement(locatorKey);
				noOfColumns = datePicker.findElements(By.tagName("td"));
				// System.out.println(noOfColumns);

				// Loop will rotate till expected date not found.
				for (WebElement cell : noOfColumns) {
					// System.out.println(cell.getText());
					// Select the date from date picker when condition match.
					if (cell.getText().equals(expDay)) {
						cell.findElement(By
								.xpath("//span[contains(@class,'ng-binding') and contains(text(), '" + expDay + "')]"))
								.click();
						break;
					}
				}
				try {
					Thread.sleep(4000);
				} catch (InterruptedException e) {
					e.getMessage();
				}

				dateNotFound = false;

			}

			// If current selected year is not same as expected year then go Inside this
			// condition.

			else if (Integer.parseInt(expYear) != Integer.parseInt(calYear)) {
				if (Integer.parseInt(expYear) < Integer.parseInt(calYear)) {
					driver.findElement(By.xpath("//button[@class='btn btn-default btn-sm pull-left uib-left']"))
							.click();
				} else {
					driver.findElement(By.xpath("//button[@class='btn btn-default btn-sm pull-right uib-right']"))
							.click();
				}
			}

			// If current selected months is not same as expected year then go Inside this

			else if (monthList.indexOf(expMonth) != monthList.indexOf(calMonth)) {
				if (monthList.indexOf(expMonth) < monthList.indexOf(calMonth)) {
					driver.findElement(By.xpath("//button[@class='btn btn-default btn-sm pull-left uib-left']"))
							.click();
				} else {
					driver.findElement(By.xpath("//button[@class='btn btn-default btn-sm pull-right uib-right']"))
							.click();
				}
			}

		}

		if (!dateNotFound)
			return Constants.PASS;
		else
			return Constants.FAIL + " - Could not find Element " + locatorKey;
	}

}
