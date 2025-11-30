package org.study.splearn.adapter.integration;

import org.springframework.context.annotation.Fallback;
import org.springframework.stereotype.Component;
import org.study.splearn.application.required.EmailSender;
import org.study.splearn.domain.Email;

@Component
@Fallback  // 다른 빈을 찾다가 없으면 사용
public class DummyEmailSender implements EmailSender {
    @Override
    public void send(Email email, String subject, String content) {
        System.out.println("DummyEmailSender send email : " + email);
    }
}
