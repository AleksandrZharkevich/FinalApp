package by.mrbregovich.iba.project.service.impl;

import by.mrbregovich.iba.project.constants.AppConstants;
import by.mrbregovich.iba.project.dto.MailDto;
import by.mrbregovich.iba.project.service.MailService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;

@Service
public class MailServiceImpl implements MailService {

    JavaMailSender mailSender;

    @Autowired
    public MailServiceImpl(JavaMailSender mailSender) {
        this.mailSender = mailSender;
    }

    @Override
    public void sendEmail(MailDto email) {
        SimpleMailMessage msg = new SimpleMailMessage();
        msg.setTo(AppConstants.DEV_EMAIL);
        msg.setSubject("The CHOICE MAIL");
        msg.setText(
                "Author: " + email.getFirstName() + " " + email.getLastName() + "\n\n" +
                        "Email: " + email.getEmail() + "\n\n" +
                        "Subject: " + email.getSubject() + "\n\n" +
                        email.getContent()
        );
        msg.setReplyTo(email.getEmail());
        mailSender.send(msg);
    }
}
