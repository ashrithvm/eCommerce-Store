package com.puzzlesapi.email;

import org.springframework.core.env.Environment;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Component;

import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.internet.MimeMessage;

@Component
public class EmailService {
    private JavaMailSender mailSender;
    private Environment env;

    public EmailService(JavaMailSender mailSender, Environment env) {
        this.mailSender = mailSender;
        this.env = env;
    }

    /**
     * Sends a simple email message
     * @param to Recipient email address
     * @param subject Subject of the email
     * @param text Body of the email
     */
    public void sendSimpleMessage(String sender, String to, String subject, String text) {
        SimpleMailMessage message = new SimpleMailMessage();
        message.setFrom(sender);
        message.setTo(to);
        message.setSubject(subject);
        message.setText(text);
        mailSender.send(message);
    }

    public void sendHtmlEmail(String sender, String to, String subject, String html) throws MessagingException {
        MimeMessage message = mailSender.createMimeMessage();
        message.setFrom(sender);
        message.setRecipients(Message.RecipientType.TO, to);
        message.setSubject(subject);
        message.setContent(html, "text/html");
        mailSender.send(message);
    }
}
