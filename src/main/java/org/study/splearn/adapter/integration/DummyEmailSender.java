package org.study.splearn.adapter.integration;

import org.springframework.stereotype.Component;
import org.study.splearn.application.required.EmailSender;
import org.study.splearn.domain.Email;

@Component
public class DummyEmailSender implements EmailSender {
    @Override
    public void send(Email email, String subject, String content) {
        System.out.println("DummyEmailSender send email : " + email);
    }
}
