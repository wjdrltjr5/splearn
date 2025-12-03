package org.study.splearn.domain.member;

import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.junit.jupiter.api.Assertions.*;

class ProfileTest {

    @Test
    void profile() {
        // given

        // when

        // then
        new Profile("123aa");
        new Profile("wjdrltjr");
        new Profile("111111");
    }

    @Test
    void profileFail() {
        // given
        // when
        // then
        assertThatThrownBy(() ->
                new Profile("")).isInstanceOf(IllegalArgumentException.class);
        assertThatThrownBy(() ->
                new Profile("1111111111111112123123123123123")).isInstanceOf(IllegalArgumentException.class);
    }

    @Test
    void url() {
        // given
        var profile = new Profile("123aa");
        // when
        // then
        assertThat(profile.url()).isEqualTo("@123aa");
    }



}