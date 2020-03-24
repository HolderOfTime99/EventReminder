package api;

import javax.mail.*;
import java.util.Properties;
import javax.mail.internet.*;

public class GmailSender {

    private String username;
    private String password;
    public static final String HOST_SERVER_ADDRESS = "smtp.gmail.com";
    public static final String HOST_SERVER_SSL_PORT = "465";


    public GmailSender(String username, String password) {
        this.username = username;
        this.password = password;
    }

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

    private Properties setProperties() {
        Properties properties = new Properties();
        properties.put("mail.smtp.host", HOST_SERVER_ADDRESS);
        properties.put("mail.smtp.socketFactory.port", HOST_SERVER_SSL_PORT);
        properties.put("mail.smtp.socketFactory.class", "javax.net.ssl.SSLSocketFactory");
        properties.put("mail.smtp.auth", "true");
        properties.put("mail.smtp.port", HOST_SERVER_SSL_PORT);
        return properties;
    }

    public static void main(String[] args) throws Exception {
        GmailSender sender = new GmailSender("username", "password");
        sender.send("username", "subject", "content");
    }

}
