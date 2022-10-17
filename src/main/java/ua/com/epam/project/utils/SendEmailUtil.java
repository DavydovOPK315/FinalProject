package ua.com.epam.project.utils;

import javax.mail.*;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;
import java.io.UnsupportedEncodingException;
import java.util.Properties;

/**
 * Class for sending email
 *
 * @author Denis Davydov
 * @version 2.0
 */
public final class SendEmailUtil {

    private SendEmailUtil() {
    }

    /**
     * Procedure to send email
     *
     * @param recipientEmail recipient email
     * @param subject        subject
     * @param content        content
     * @throws MessagingException           exception can be thrown
     * @throws UnsupportedEncodingException exception can be thrown
     */
    public static void sendEmail(String recipientEmail, String subject, String content) throws MessagingException, UnsupportedEncodingException {
        String fromEmail = "gameshopcontactmail@gmail.com";
        String password = "dsfgqhjwbwisxwjm";

        Properties properties = System.getProperties();
        properties.setProperty("mail.smtp.host", "smtp.gmail.com");
        properties.setProperty("mail.smtp.port", "587");
        properties.setProperty("mail.smtp.auth", "true");
        properties.setProperty("mail.smtp.starttls.enable", "true"); // enable TLS

        Session session = Session.getDefaultInstance(properties,
                new Authenticator() {
                    @Override
                    protected PasswordAuthentication getPasswordAuthentication() {
                        return new PasswordAuthentication(fromEmail, password);
                    }
                });

        MimeMessage message = new MimeMessage(session);
        message.setFrom(new InternetAddress(fromEmail, "Elective"));
        message.addRecipient(Message.RecipientType.TO, new InternetAddress(recipientEmail));
        message.setSubject(subject);
        message.setContent(content, "text/html;charset=utf-8");
        message.saveChanges();
        Transport.send(message);
    }
}