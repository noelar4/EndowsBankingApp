package com.endows.app.helper;

import android.os.AsyncTask;

import java.util.Date;
import java.util.Properties;

import javax.mail.Message;
import javax.mail.PasswordAuthentication;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;

public class EmailHelper extends AsyncTask<String, Void, Void> {
    @Override
    protected Void doInBackground(String... strings) {
        final String username = EncryptPasswords.decrypt("v0wTNKXJtZ9rCnVA7KJezo1j/9PfFYms");
        final String password = EncryptPasswords.decrypt("g+tpRVxNs1LTsXa01bcc4w==");
        Properties prop = new Properties();
        prop.put("mail.smtp.host", "smtp.gmail.com");
        prop.put("mail.smtp.port", "587");
        prop.put("mail.smtp.auth", "true");
        prop.put("mail.smtp.starttls.enable", "true"); //TLS

        Session session = Session.getInstance(prop,
                new javax.mail.Authenticator() {
                    protected PasswordAuthentication getPasswordAuthentication() {
                        return new PasswordAuthentication(username, password);
                    }
                });

        try {

            Message message = new MimeMessage(session);
            message.setFrom(new InternetAddress(username));
            message.setRecipients(
                    Message.RecipientType.TO,
                    InternetAddress.parse(strings[0])
            );
            message.setSubject("Testing Gmail TLS");
            message.setSentDate(new Date());
            message.setContent(strings[1], "text/html");

            // sends the e-mail
            Transport.send(message);

        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }
}
