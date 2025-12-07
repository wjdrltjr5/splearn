package org.study.splearn.domain.member;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.*;
import static org.study.splearn.domain.member.MemberFixture.createMemberRegisterRequest;


class MemberTest {

    Member member;
    PasswordEncoder passwordEncoder;

    @BeforeEach
    void setUp() {
        this.passwordEncoder = MemberFixture.craetePasswordEncoder();
        member = Member.register( MemberFixture.createMemberRegisterRequest(), this.passwordEncoder);
    }

    @Test
    void createMember(){
        //given
        //when
        //then
        assertThat(member.getStatus()).isEqualTo(MemberStatus.PENDING);
    }

    @Test
    void activate() {
        // given
        // when
        member.activate();
        // then
        assertThat(member.getStatus()).isEqualTo(MemberStatus.ACTIVE);
    }

    @Test
    void activateFail() {
        // given
        // when
        member.activate();
        // then
        assertThatThrownBy(member::activate).isInstanceOf(IllegalStateException.class);
    }

    @Test
    void deactivate() {
        // given
        member.activate();
        // when
        member.deactivate();
        // then
        assertThat(member.getStatus()).isEqualTo(MemberStatus.DEACTIVATED);
    }

    @Test
    void deactivateFail() {
        // given
        // when
        // then
        assertThatThrownBy(member::deactivate).isInstanceOf(IllegalStateException.class);

        member.activate();
        member.deactivate();

        assertThatThrownBy(member::deactivate).isInstanceOf(IllegalStateException.class);

    }

    @Test
    void verifyPassword() {
        // given
        // when
        // then
        assertThat(member.verifyPassword("secret111", passwordEncoder)).isTrue();
    }

    @Test
    void verifyPasswordFail() {
        // given
        // when
        // then
        assertThat(member.verifyPassword("test11", passwordEncoder)).isFalse();
    }

    @Test
    void changePassword() {
        // given
        // when
        member.changePassword("verysecret", passwordEncoder);
            // then
        assertThat(member.verifyPassword("verysecret", passwordEncoder)).isTrue();
    }

    @Test
    void isActive() {
        // given
        // when
        // then
        assertThat(member.isActivated()).isFalse();
        member.activate();
        assertThat(member.isActivated()).isTrue();
        assertThat(member.getDetail().getActivateAt()).isNotNull();
        member.deactivate();
        assertThat(member.isActivated()).isFalse();
        assertThat(member.getDetail().getDeactivatedAt()).isNotNull();

    }

    @Test
    void invalidEmail() {
        // given
        // when
        // then
        assertThatThrownBy(() ->
                    Member.register(createMemberRegisterRequest("invalid email"), passwordEncoder)
        ).isInstanceOf(IllegalArgumentException.class);
    }

    @Test
    void updateInfo() {
        // given
        member.activate();
        // when
        MemberInfoUpdateRequest request = new MemberInfoUpdateRequest("newNickname", "wjdrltjr5", "자기소개");
        member.updateInfo(request);
        // then
        assertThat(member.getNickname()).isEqualTo(request.nickname());
        assertThat(member.getDetail().getProfile().address()).isEqualTo(request.profileAddress());
        assertThat(member.getDetail().getIntroduction()).isEqualTo(request.introduction());
    }

    @Test
    void updateInfoFail() {
        assertThatThrownBy(() -> {
            var request = new MemberInfoUpdateRequest("Leo", "wjdrltjr5", "자기소개");
            member.updateInfo(request);
        }).isInstanceOf(IllegalStateException.class);
    }

}