package pv.selenium.hybrid.util;
import java.util.Date;
import org.testng.IExecutionListener;
import pv.selenium.hybrid.mail.SendMail;
 
public class ExecutionListener implements IExecutionListener {
	SendMail mail=new SendMail();
    public static Date executionStartTime;
    public static Date executionEndTime;
    public static String zipAtachmentPath; 
    public static String reportFolderTimestampAppend;
    public static String fileName;
    
    public void onExecutionStart() {
    	executionStartTime = new Date();
    	reportFolderTimestampAppend = executionStartTime.toString().replace(":", "_").replace(" ", "_");
    	fileName=Constants.ZIP_REPORT_FOLDER_NAME+reportFolderTimestampAppend+".zip";
       	zipAtachmentPath= Constants.REPORT_ARCHIVE_FOLDER+fileName;
       
    }
 
    public void onExecutionFinish() {
    	executionEndTime = new Date();
    	SendMail.mailreportwithAttachment(zipAtachmentPath,fileName);
    }
}
