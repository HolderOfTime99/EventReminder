package api;

import javax.mail.*;
import java.io.FileNotFoundException;
import java.util.Properties;
import javax.mail.internet.*;

/**
 * This class conducts SMTP functionality from a given Gmail account.
 * Automated emails can be sent from this account using this class.
 */
public class GmailSender {

    private String username;
    private String password;
    public static final String HOST_SERVER_ADDRESS = "smtp.gmail.com";
    public static final String HOST_SERVER_SSL_PORT = "465";


    /**
     * Constructs and returns a GmailSender that is hooked up to the given
     * GMAIL account.
     *
     * @param username the username of the given GMAIL account
     * @param password the password of the given GMAIL account
     */
    public GmailSender(String username, String password) {
        this.username = username;
        this.password = password;
    }

    /**
     * Sends an email from the account this is hooked up to.
     *
     * @param to the email address being sent to
     * @param subject the content for the "Subject" line in the email.
     * @param content the content of the message
     * @throws Exception throws mailing related exceptions for malformed messaging
     * and addressing
     */
    public void send(String to, String subject, String content) throws Exception {
        Properties props = setProperties();
        Authenticator authenticator = new Authenticator() {
            protected PasswordAuthentication getPasswordAuthentication() {
                return new PasswordAuthentication(username, password);
            }
        };
        Session session = Session.getDefaultInstance(props, authenticator);
        MimeMessage message = new MimeMessage(session);
        message.addRecipient(Message.RecipientType.TO, new InternetAddress(to));
        message.setSubject(subject);
        message.setText(content);
        Transport.send(message);
    }

    /**
     * Sets the properties for the message sending
     * @return
     */
    private Properties setProperties() {
        Properties properties = new Properties();
        properties.put("mail.smtp.host", HOST_SERVER_ADDRESS);
        properties.put("mail.smtp.socketFactory.port", HOST_SERVER_SSL_PORT);
        properties.put("mail.smtp.socketFactory.class", "javax.net.ssl.SSLSocketFactory");
        properties.put("mail.smtp.auth", "true");
        properties.put("mail.smtp.port", HOST_SERVER_SSL_PORT);
        return properties;
    }

}
