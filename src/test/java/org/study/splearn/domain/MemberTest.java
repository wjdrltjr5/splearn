package org.study.splearn.domain;

import org.junit.jupiter.api.Test;
import static org.assertj.core.api.Assertions.*;


class MemberTest {

    @Test
    void createMember(){
        //given
        //when
        var member =  new Member("wjdrltjr5@splearn.app", "secret", "wjdrltjr");
        //then
        assertThat(member.getStatus()).isEqualTo(MemberStatus.PENDING);
    }

    @Test
    void constructorNullCheck() {
        // given
        // when
        // then
        assertThatThrownBy(() -> new Member(null, null, null)).isInstanceOf(NullPointerException.class);
    }


    @Test
    void activate() {
        // given
        var member =  new Member("wjdrltjr5@splearn.app", "secret", "wjdrltjr");
        // when
        member.activate();
        // then
        assertThat(member.getStatus()).isEqualTo(MemberStatus.ACTIVE);
    }

    @Test
    void activateFail() {
        // given
        var member =  new Member("wjdrltjr5@splearn.app", "secret", "wjdrltjr");
        // when
        member.activate();
        // then
        assertThatThrownBy(member::activate).isInstanceOf(IllegalStateException.class);
    }

    @Test
    void deactivate() {
        // given
        var member =  new Member("wjdrltjr5@splearn.app", "secret", "wjdrltjr");
        member.activate();
        // when
        member.deactivate();
        // then
        assertThat(member.getStatus()).isEqualTo(MemberStatus.DEACTIVATED);
    }

    @Test
    void deactivateFail() {
        // given
        var member =  new Member("wjdrltjr5@splearn.app", "secret", "wjdrltjr");
        // when
        // then
        assertThatThrownBy(member::deactivate).isInstanceOf(IllegalStateException.class);

        member.activate();
        member.deactivate();

        assertThatThrownBy(member::deactivate).isInstanceOf(IllegalStateException.class);

    }


}