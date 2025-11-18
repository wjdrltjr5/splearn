package org.study.splearn.domain;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.assertj.core.api.Assertions.*;


class MemberTest {

    Member member;
    PasswordEncoder passwordEncoder;

    @BeforeEach
    void setUp() {
        this.passwordEncoder = new PasswordEncoder() {
            @Override
            public String encode(String password) {
                return password.toUpperCase();
            }

            @Override
            public boolean matches(String password, String passwordHash) {
                return encode(password).equals(passwordHash);
            }
        };

        var createRequest = new MemberCreateRequest("wjdrltjr5@splearn.app", "wjdrltjr", "secret");

        member = Member.create( createRequest, passwordEncoder);

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
        assertThat(member.verifyPassword("secret", passwordEncoder)).isTrue();
    }

    @Test
    void verifyPasswordFail() {
        // given
        // when
        // then
        assertThat(member.verifyPassword("test", passwordEncoder)).isFalse();
    }

    @Test
    void changeNickname() {
        // given
        assertThat(member.getNickname()).isEqualTo("wjdrltjr");
        // when
        member.changeNickname("wjdrltjr5");
        // then
        assertThat(member.getNickname()).isEqualTo("wjdrltjr5");
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
        member.deactivate();
        assertThat(member.isActivated()).isFalse();
    }

    @Test
    void invalidEmail() {
        // given
        // when
        // then
        assertThatThrownBy(() ->
                    Member.create(new MemberCreateRequest("invalid email", "test", "secret"), passwordEncoder)
        ).isInstanceOf(IllegalArgumentException.class);
    }

}