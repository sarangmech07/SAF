package pv.selenium.hybrid.mail;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;

import java.io.FileReader;
import java.io.IOException;
import java.sql.Date;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.Properties;

import javax.activation.DataHandler;
import javax.activation.DataSource;
import javax.activation.FileDataSource;

import javax.mail.BodyPart;
import javax.mail.Message;
import javax.mail.MessagingException;

import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeBodyPart;
import javax.mail.internet.MimeMessage;
import javax.mail.internet.MimeMultipart;

import pv.selenium.hybrid.util.Constants;
import pv.selenium.hybrid.util.ExecutionListener;

public class SendMail {
	public static Properties prop = new Properties();
	private static final String TRUE = "true";
	public static final String USER_DIR = "user.dir";
	public String host = null;
	public String port = null;
	public static String reportHtmlText;
	public static MimeMessage message;
	public static Session session;
	public static BodyPart mbp1;
	public static MimeMultipart mp1;
	public static BodyPart attachment_mbp1;
	public static BodyPart attachment_mbp2;
	public FileInputStream fs;
	public static String testNGReportindexfilePath;
	public static String testNGreportoOutputFile;

	
	public SendMail() {
		try {
			FileInputStream fs = new FileInputStream(Constants.PROPERTIES_FILE_PATH);
			prop.load(fs);
			host = prop.getProperty("smtp_server_address");
			port = prop.getProperty("smtp_server_port");
			session = Session.getDefaultInstance(prop);
			prop.setProperty("mail.smtp.host", host);
			prop.setProperty("mail.smtp.port", port);

		} catch (Exception e) {
			e.printStackTrace();
		}
		// MimeMessage message = new MimeMessage(session);

	}

	public static void mailreportwithAttachment(String filePath, String filename) {

		try {
			
			ExecutionListener listener = new ExecutionListener();
			testNGreportoOutputFile=Constants.REPORT_ARCHIVE_FOLDER+"TestNG-Report_"+ExecutionListener.reportFolderTimestampAppend+".zip";
			File srcFolder = new File(Constants.REPORT_PATH);
			Zip.zipDir(Constants.REPORT_PATH, filePath);
			Zip.zipDir(Constants.TESTNG_REPORT_FILE_PATH_EMAIL, testNGreportoOutputFile);
			message = new MimeMessage(session);
			// Attaching the zip file to the email
			DataSource source_index = new FileDataSource(filePath);
			DataSource source_testngindex = new FileDataSource(testNGreportoOutputFile);
			reportHtmlText = SendMail.htmlConverter(Constants.TESTNG_REPORT_FILE_PATH);
			//message = new MimeMessage(session);
			mbp1 = new MimeBodyPart();
			mp1 = new MimeMultipart();
			mbp1.setContent(reportHtmlText, "text/html");
			mp1.addBodyPart(mbp1);
			attachment_mbp1 = new MimeBodyPart();
			attachment_mbp1.setDataHandler(new DataHandler(source_index));
			attachment_mbp1.setFileName(filename);
			mp1.addBodyPart(attachment_mbp1);
			attachment_mbp2 = new MimeBodyPart();
			attachment_mbp2.setDataHandler(new DataHandler(source_testngindex));
			attachment_mbp2.setFileName("TestNG-Report_"+ExecutionListener.reportFolderTimestampAppend+".zip");
			mp1.addBodyPart(attachment_mbp2);
			// Set From: header field of the header.
			message.setFrom(new InternetAddress(prop.getProperty("from"), "Product Team"));
			message.setRecipients(Message.RecipientType.TO, InternetAddress.parse(prop.getProperty("to")));
			message.setRecipients(Message.RecipientType.CC, InternetAddress.parse(prop.getProperty("cc")));
			message.setSubject(prop.getProperty("subject_line"));
			message.setContent(mp1);
			Transport.send(message);
			prop.clear();
			
			if (srcFolder.exists()) {
				if (srcFolder.length() > 0) {
				String[] entries = srcFolder.list();
					for (String s : entries) {
						File CurrentFile = new File(srcFolder.getPath(), s);
						CurrentFile.delete();
					}
				}
				
			} 

		}

		catch (Exception e) {

			e.printStackTrace();
		}

	}

	public void mailConfig(String testcasename, int step, String keyword, String moduleName) throws IOException {

		try {
			testNGReportindexfilePath = Constants.TESTNG_REPORT_FILE_PATH;
			reportHtmlText = SendMail.htmlConverter(testNGReportindexfilePath);
			// Report failure message HTML email body
			String htmlmessage = "<html><head><title></title></head><body><h2><p align='center' style= color:red><u>Test Case Failure Alert</u></p></h2><table border='1' width='100%'><tr><th style= font-size:20px>Module Name</th><th style= font-size:20px>TestCase</th><th style= font-size:20px>Step</th><th style= font-size:20px>Keyword</th></tr><tr align='center' ><td style= color:blue><em>"
					+ moduleName + "</em></td><td style= color:blue><em>" + testcasename
					+ "</em></td><td style= color:blue><em>" + step + "</em></td><td style= color:blue><em>" + keyword
					+ "</em></td></tr></table></body></html>";
			message = new MimeMessage(session);
			mbp1 = new MimeBodyPart();
			mp1 = new MimeMultipart();
			mbp1.setContent(htmlmessage, "text/html");
			mp1.addBodyPart(mbp1);
			// Set From: header field of the header.
			message.setFrom(new InternetAddress(prop.getProperty("from"), "Product Team"));
			message.setRecipients(Message.RecipientType.TO, InternetAddress.parse(prop.getProperty("to")));
			message.setRecipients(Message.RecipientType.CC, InternetAddress.parse(prop.getProperty("cc")));
			message.setSubject(prop.getProperty("subject_line"));
			message.setContent(mp1);
			Transport.send(message);
			prop.clear();
		} catch (MessagingException e) {
			e.getMessage();
		}
	}

	

	public static String htmlConverter(String filePath) {
		String content = "";
		try {
			BufferedReader in = new BufferedReader(new FileReader(filePath));
			String str;
			while ((str = in.readLine()) != null) {
				content += str;
			}

			in.close();
			// return content;
		} catch (IOException e) {
			e.printStackTrace();
		}

		return content;

	}

}
