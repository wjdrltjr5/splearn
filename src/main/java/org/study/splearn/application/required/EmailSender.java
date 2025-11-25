package org.study.splearn.application.required;

import org.springframework.stereotype.Component;
import org.study.splearn.domain.Email;

/**
 * 이메일을 발송한다.
 * */
@Component
public interface EmailSender {
    void send(Email email, String subject, String content);
}
