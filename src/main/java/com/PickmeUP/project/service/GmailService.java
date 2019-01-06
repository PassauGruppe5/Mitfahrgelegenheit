package com.PickmeUP.project.service;

import com.PickmeUP.project.model.User;

import java.util.Properties;

import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.PasswordAuthentication;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;

public class GmailService {
    public static void sendWelcomeMail(User user) {
        String to = user.getEmail();
        String from = "pickmeup.uni.passau@gmail.com";
        final String username = "pickmeup.uni.passau@gmail.com";
        final String password = "lukpkucfltvvpxkf";

        // Assuming you are sending email through relay.jangosmtp.net
        String host = "smtp.gmail.com";

        Properties props = new Properties();
        props.put("mail.smtp.auth", "true");
        props.put("mail.smtp.starttls.enable", "true");
        props.put("mail.smtp.host", host);
        props.put("mail.smtp.port", "587");

        // Get the Session object.
        Session session = Session.getInstance(props,
                new javax.mail.Authenticator() {
                    protected PasswordAuthentication getPasswordAuthentication() {
                        return new PasswordAuthentication(username, password);
                    }
                });

        try {
            // Create a default MimeMessage object.
            Message message = new MimeMessage(session);

            // Set From: header field of the header.
            message.setFrom(new InternetAddress(from));

            // Set To: header field of the header.
            message.setRecipients(Message.RecipientType.TO,
                    InternetAddress.parse(to));

            // Set Subject: header field
            message.setSubject("Deine Anmeldung bei PickmeUP");

            // Now set the actual message
            message.setText("Hallo "
                    + user.getName()
                    + ","
                    + "\n\n"
                    + "vielen Dank für deine Anmeldung bei PickmeUP! Dein Account ist jetzt aktiviert. Viel Spaß!");

            // Send message
            Transport.send(message);

        } catch (MessagingException e) {
            throw new RuntimeException(e);
        }
    }
}