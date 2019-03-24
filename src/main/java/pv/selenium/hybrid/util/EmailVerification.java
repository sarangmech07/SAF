package pv.selenium.hybrid.util;

import java.io.IOException;
import java.util.Arrays;
import java.util.Properties;

import javax.mail.BodyPart;
import javax.mail.Flags;
import javax.mail.Folder;
import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.Multipart;
import javax.mail.Session;
import javax.mail.Store;
import javax.mail.internet.MimeMultipart;
import javax.mail.search.FlagTerm;

public class EmailVerification {

	static Properties props;
	static Session session;
	static Store store;
	static Folder folder;
	static Message[] messages;

	public static String connectToGmail(String credentials) {
		try {
			String getCredentials[] = credentials.split(",", 2);
			String username = getCredentials[0];
			String password = getCredentials[1];

			props = new Properties();
			// set email protocol to IMAP
			props.put("mail.store.protocol", "imaps");
			props.put("mail.store.port", "587");
			// set up the session
			session = Session.getInstance(props);
			store = session.getStore("imaps");
			// Connect to your email account
			store.connect("imap.googlemail.com", username, password);
			// Get reference to your INBOX
			folder = store.getFolder("INBOX");
			// Open the folder in READ MODE only
			folder.open(Folder.READ_ONLY);

			// System.out.println("No of Unread Messages : " +
			// folder.getUnreadMessageCount());
			// Get all the messages in INBOX into Message array
//		Message[] messages = folder.getMessages();

			// Fetch unseen messages from inbox folder
			messages = folder.search(new FlagTerm(new Flags(Flags.Flag.SEEN), false));

			// Sort messages from recent to oldest
			Arrays.sort(messages, (m1, m2) -> {
				try {
					return m2.getSentDate().compareTo(m1.getSentDate());
				} catch (MessagingException e) {
					throw new RuntimeException(e);
				}
			});

//	    for ( Message message : messages )
//	    for ( int i=0;i<1;i++ )	
//	    {
		} catch (MessagingException E) {
			E.getMessage();
			return Constants.FAIL + " - Could not connect to mail server ";
		}
		return Constants.PASS + " - Connection to mail server successful";

	}

	public static String verifyEmailSubject(String credentials, String data) {
		// split data to see if multiple values are being passed by user for selection -
		// delimiter - comma
		// String getEmailInfo[] = data.split(",",2);

		try {
			connectToGmail(credentials);
			String expectedSubject = data;
			// String expectedBody = getEmailInfo[1];

			// String sendDate = messages[0].getSentDate().toString();
			// String from = messages[0].getFrom()[0].toString();
			String subject = messages[0].getSubject();

			if (subject.contains(expectedSubject)) {
				// System.out.println("Email Subject matched");
				return Constants.PASS;

			} else {
				// System.out.println("Email Subject not matched");
				return Constants.FAIL + " - Subject Mis-match";
			}
//		    	System.out.println( 
//		          "sendDate: " + message.getSentDate()
//		         + "From: " +message.getFrom()[0].toString()
//		          + " subject: " + message.getSubject() 
//		          
//		          );
		} catch (MessagingException e) {
			return Constants.FAIL + " - Subject not found ";
		}

	}

	public static String verifyEmailBody(String credentials, String data) {
		try {
			connectToGmail(credentials);
			String expectedBody = data;
			String body = "";
			// Object obj = messages[0].getContent();
			Multipart mp = (Multipart) messages[0].getContent();

//		        for(int i=0;i<mp.getCount();i++) -This is to iterate through all unread mails
			for (int i = 0; i < 1; i++) // checking for only latest mail body
			{
				BodyPart bodyPart = mp.getBodyPart(i);
				if (bodyPart.isMimeType("text/*")) {
					body = (String) bodyPart.getContent();
//		                System.out.println("Body: " +body);
					if (body.contains(expectedBody)) {
						// System.out.println("Email body matched");
					} else {
						// System.out.println("Email body not matched");
					}
				} else if (bodyPart.isMimeType("multipart/*")) {
					MimeMultipart mimeMultipart = (MimeMultipart) bodyPart.getContent();
					body = getBodyTextFromMimeMultipart(mimeMultipart);
					if (body.contains(expectedBody)) {
						// System.out.println("Email body matched");
					} else {
						// System.out.println("Email body not matched");
					}
				}
			}

		} catch (MessagingException ME) {
			return Constants.FAIL + " - Body Mis-matched ";
		} catch (IOException ME) {
			return Constants.FAIL + " - Body Mis-matched ";
		}
		return Constants.PASS;
	}

	// calling this function in verifyEmail keyword to get the body text in case it
	// is Multipart
	private static String getBodyTextFromMimeMultipart(MimeMultipart mimeMultipart)
			throws MessagingException, IOException {
		String result = "";
		int count = mimeMultipart.getCount();
		for (int i = 0; i < count; i++) {
			BodyPart bodyPart = mimeMultipart.getBodyPart(i);
			if (bodyPart.isMimeType("text/plain")) {
				result = result + "\n" + bodyPart.getContent();
				break; // without break same text appears twice in my tests
			} else if (bodyPart.isMimeType("text/html")) {
				String html = (String) bodyPart.getContent();
				result = result + "\n" + org.jsoup.Jsoup.parse(html).text();
			} else if (bodyPart.getContent() instanceof MimeMultipart) {
				result = result + getBodyTextFromMimeMultipart((MimeMultipart) bodyPart.getContent());
			}
		}
		return result;
	}

}
