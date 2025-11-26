package org.study.splearn.application.provided;


import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.test.util.ReflectionTestUtils;
import org.study.splearn.application.MemberService;
import org.study.splearn.application.required.EmailSender;
import org.study.splearn.application.required.MemberRepository;
import org.study.splearn.domain.Email;
import org.study.splearn.domain.Member;
import org.study.splearn.domain.MemberFixture;
import org.study.splearn.domain.MemberStatus;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;

class MemberRegisterManualTest {

    @Test
    void registerStub() {
        // given
        MemberRegister register = new MemberService(new MemberRepositoryStub(), new EmailSenderStub(), MemberFixture.craetePasswordEncoder());

        Member member = register.register(MemberFixture.createMemberRegisterRequest());

        assertThat(member.getId()).isNotNull();
        assertThat(member.getStatus()).isEqualTo(MemberStatus.PENDING);
    }

    @Test
    void registerMock() {
        // given

        EmailSenderMock emailSender = new EmailSenderMock();

        MemberRegister register = new MemberService(new MemberRepositoryStub(), emailSender, MemberFixture.craetePasswordEncoder());

        Member member = register.register(MemberFixture.createMemberRegisterRequest());

        assertThat(member.getId()).isNotNull();
        assertThat(member.getStatus()).isEqualTo(MemberStatus.PENDING);

        assertThat(emailSender.getTos()).hasSize(1);
        assertThat(emailSender.getTos().getFirst()).isEqualTo(member.getEmail());

    }

    @Test
    void registerMockito() {
        // given

        EmailSender emailSenderMock = Mockito.mock(EmailSenderMock.class);

        MemberRegister register = new MemberService(new MemberRepositoryStub(), emailSenderMock, MemberFixture.craetePasswordEncoder());

        Member member = register.register(MemberFixture.createMemberRegisterRequest());

        assertThat(member.getId()).isNotNull();
        assertThat(member.getStatus()).isEqualTo(MemberStatus.PENDING);

        Mockito.verify(emailSenderMock).send(eq(member.getEmail()), any(), any());
    }

    static class MemberRepositoryStub implements  MemberRepository{
        @Override
        public Member save(Member member) {
            ReflectionTestUtils.setField(member, "id", 1L);
            return member;
        }

        @Override
        public Optional<Member> findByEmail(Email email) {
            return Optional.empty();
        }
    }

    static class EmailSenderStub implements EmailSender {
        @Override
        public void send(Email email, String subject, String content) {
        }
    }

    static class EmailSenderMock implements EmailSender {
        List<Email> tos = new ArrayList<>();

        @Override
        public void send(Email email, String subject, String content) {
            tos.add(email);
        }

        public List<Email> getTos() {
            return tos;
        }
    }
}