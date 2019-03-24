package pv.selenium.hybrid.util;

import java.text.SimpleDateFormat;
import java.util.Date;

public class DateFormatter {
	
	public static String getCurrentTimeStamp() {
	    SimpleDateFormat sdfDate = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");//dd/MM/yyyy
	    Date now = new Date();
	    String strDate = sdfDate.format(now);
	    return strDate;
	}

}
