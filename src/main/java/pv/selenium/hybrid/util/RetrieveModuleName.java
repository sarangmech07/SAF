package pv.selenium.hybrid.util;

import pv.selenium.hybrid.keywords.Keywords;

public class RetrieveModuleName {
	
	public static String moduleName;
	public static String reteriveModuleName() {
		try {
			moduleName = Keywords.exchelSheetModulepath;
			// String myArray[]=null;
			String[] myArray = moduleName.split("\\\\");
			int m = myArray.length;
			moduleName = myArray[m - 1];
			moduleName = moduleName.replaceAll(".xlsx", "");
			// System.out.println("module Name "+moduleName);
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		return moduleName;

	}

}
