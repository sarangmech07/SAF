package pv.selenium.hybrid.util;

import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.io.LineNumberReader;

public class FileRowReader {
	
	public static int getFileRowCount(String filPath) {

	try{
		File file =new File(filPath);
	if(file.exists()){
		    FileReader fr = new FileReader(file);
		    LineNumberReader lnr = new LineNumberReader(fr);
		    int linenumber = 0;
	            while (lnr.readLine()!= null){
	        	linenumber++;
	            }
	 //Subtracting the table header count
	            linenumber--;
	            //System.out.println("Total number of lines : " + linenumber);
	            lnr.close();
	            return linenumber;
		}
			}
	catch(IOException e){
		e.getMessage();
		}
	return 0;
}

}

