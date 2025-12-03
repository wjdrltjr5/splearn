package org.study.splearn;

import org.springframework.boot.test.context.TestConfiguration;
import org.springframework.context.annotation.Bean;
import org.study.splearn.application.member.required.EmailSender;
import org.study.splearn.domain.member.MemberFixture;
import org.study.splearn.domain.member.PasswordEncoder;
@TestConfiguration
public class SplearnTestConfiguration {
    @Bean
    public EmailSender emailSender() {
        return (email, subject, content) -> System.out.println("Sending Email "  + email);
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return MemberFixture.craetePasswordEncoder();
    }
}
