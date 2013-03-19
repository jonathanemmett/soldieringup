package org.soldieringup;

import java.util.Properties;

import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;

/**
 * This class contains the requirements needed for sending mail through Java mail apis.
 *
 * Right now, we are using a gmail account for testing purposes. When we go live, we
 * need to make that that the base email is the email that the server provides for
 * the hosting.
 *
 * @author Jake LaCombe
 */
public class EmailMessage
{
	private static EmailMessage classInstance = null;
	private static final String CURRENT_SOLDIERUP_EMAIL = "soldup2013test@gmail.com";
	private static final String CURRENT_SOLDIERUP_PASSWORD = "Provided in Email :)";
	private Properties properties;
	private Session session;

	// Note: This email host is currently only for testing purposes
	public String host = "smtp.gmail.com";

	/**
	 * Gets the singleton instance of the class
	 * @return Singleton instance of the class
	 */
	public static EmailMessage getInstance()
	{
		if( classInstance == null )
		{
			classInstance = new EmailMessage();
		}

		return classInstance;
	}

	/**
	 * Constructor for an email message
	 * @param aSenderEmail Email of the sender
	 * @param aSenderPassword Password of the sender
	 */
	private EmailMessage()
	{
	      // Get system properties
	      properties = System.getProperties();

	      // Setup mail server connection
	      properties.setProperty("mail.smtp.host", host);
	      properties.put( "mail.smtp.starttls.enable", "true" );
	      properties.put( "mail.smtp.host", host );
	      properties.put( "mail.smtp.user", CURRENT_SOLDIERUP_EMAIL );
	      properties.put( "mail.smtp.password", CURRENT_SOLDIERUP_PASSWORD );
	      properties.put( "mail.smtp.port", "587" );
	      properties.put( "mail.smtp.auth", "true" );

	      // Get the session object needed for emailing
	      session = Session.getDefaultInstance(properties);
	}

	/**
	 * Sends an email from a given recipient to a given receiver with a subject and messge
	 * @param aReceiverEmail Email of the receiver
	 * @param aSubject Email subject
	 * @param aMessage Email message
	 */
	public void sendMessage(
		String aReceiverEmail,
		String aSubject,
		String aMessage)
	{
		try
		{
			// Create a default MimeMessage object.
	        MimeMessage message = new MimeMessage( session );

	        // Set From: header field of the header.
	        message.setFrom( new InternetAddress( CURRENT_SOLDIERUP_EMAIL ) );

	        // Set To: header field of the header.
	        message.addRecipient(
	        	Message.RecipientType.TO,
	            new InternetAddress( aReceiverEmail )
	        );

	        // Set Subject: header field
	        message.setSubject( aSubject );

	        // Now set the actual message
	        message.setText( aMessage );

	        // Send message
	        Transport transport = session.getTransport( "smtp" );
	        transport.connect( host, CURRENT_SOLDIERUP_EMAIL, CURRENT_SOLDIERUP_PASSWORD );
	        transport.sendMessage( message, message.getAllRecipients() );
	        transport.close();
	   }
	   catch (MessagingException mex)
	   {
	        mex.printStackTrace();
	   }
	}
}
