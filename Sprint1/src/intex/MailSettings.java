package intex;


/**
 * This class contains setting information for the sending of emails.

 * This code was modified from Michael Chambers: BYU IS Core 2010.
 * Thanks, Michael!
 */

public class MailSettings {

	// Set smtpHost to the hostname for your mail server
		public static String smtpHost = "smtp.tengentllc.com"; 
//        public static String smtpHost="smtp.gmail.com";
//        public static String smtpHost= "gateway.byu.edu";
	
	// For SMTP authentication, set the next two lines to your username and password for the mail server
        public static String smtpUserName="Mystuff@tengentllc.com";
        public static String smtpPassword="Password1";
}