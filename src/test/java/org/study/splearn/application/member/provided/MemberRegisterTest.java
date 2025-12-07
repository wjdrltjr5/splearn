package org.study.splearn.application.member.provided;

import jakarta.persistence.EntityManager;
import jakarta.validation.ConstraintViolationException;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.annotation.Import;
import org.springframework.transaction.annotation.Transactional;
import org.study.splearn.SplearnTestConfiguration;
import org.study.splearn.application.member.provided.MemberRegister;
import org.study.splearn.domain.member.*;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

@SpringBootTest
@Import(SplearnTestConfiguration.class)
@Transactional
record MemberRegisterTest(MemberRegister memberRegister, EntityManager entityManager) {

    @Test
    void register() {
        Member member = memberRegister.register(MemberFixture.createMemberRegisterRequest());
        assertThat(member.getId()).isNotNull();
        assertThat(member.getStatus()).isEqualTo(MemberStatus.PENDING);
        assertThat(member.getDetail().getRegisteredAt()).isNotNull();
    }

    @Test
    void duplicateEmailFail(){
        memberRegister.register(MemberFixture.createMemberRegisterRequest());
        assertThatThrownBy(() -> memberRegister.register(MemberFixture.createMemberRegisterRequest())).isInstanceOf(DuplicateEmailException.class);
    }

    @Test
    void activate(){
        Member member = registerMember();

        member = memberRegister.activate(member.getId());

        entityManager.flush();

        assertThat(member.getStatus()).isEqualTo(MemberStatus.ACTIVE);
        assertThat(member.getDetail().getActivateAt()).isNotNull();
    }

    @Test
    void deactivate() {
        Member member = registerMember();

        memberRegister.activate(member.getId());
        entityManager.flush();
        entityManager.clear();

        member = memberRegister.deactivate(member.getId());

        assertThat(member.getStatus()).isEqualTo(MemberStatus.DEACTIVATED);
        assertThat(member.getDetail().getDeactivatedAt()).isNotNull();
    }

    @Test
    void updateInfo() {
        Member member = registerMember();
        memberRegister.activate(member.getId());

        entityManager.flush();
        entityManager.clear();
        MemberInfoUpdateRequest request = new MemberInfoUpdateRequest("newNickname", "wjdrltjr5", "자기소개");
        member = memberRegister.updateInfo(member.getId(), request);

        assertThat(member.getDetail().getProfile().address()).isEqualTo("wjdrltjr5");
    }

    @Test
    void updateInfoFail() {
        Member member = registerMember();
        memberRegister.activate(member.getId());
        memberRegister.updateInfo(member.getId(), new MemberInfoUpdateRequest("newNickname", "wjdrltjr5", "자기소개"));


        Member member2 = registerMember("wjdrltjr5@naver.com");
        member2.activate();
        entityManager.flush();
        entityManager.clear();
        assertThatThrownBy(() ->
                memberRegister.updateInfo(member2.getId(), new MemberInfoUpdateRequest("James", "wjdrltjr5", "자기소개"))
                ).isInstanceOf(DuplicateProfileException.class);
    }

    @Test
    void memberRegisterRequestFail(){
        checkValidation(new MemberRegisterRequest("wjdrltjr5@naver.com", "wjdrltjr", "1"));
        checkValidation(new MemberRegisterRequest("wjdrltjr5naver.com", "wjdrltjr", "12334567"));
        checkValidation(new MemberRegisterRequest("wjdrltjr5@naver.com", "w", "1234455555"));

    }

    private Member registerMember() {
        Member member = memberRegister.register(MemberFixture.createMemberRegisterRequest());
        entityManager.flush();
        entityManager.clear();
        return member;
    }

    private Member registerMember(String email) {
        Member member = memberRegister.register(MemberFixture.createMemberRegisterRequest(email));
        entityManager.flush();
        entityManager.clear();
        return member;
    }

    private void checkValidation(MemberRegisterRequest invalid) {
        assertThatThrownBy(() -> memberRegister.register(invalid)).isInstanceOf(ConstraintViolationException.class);
    }
}