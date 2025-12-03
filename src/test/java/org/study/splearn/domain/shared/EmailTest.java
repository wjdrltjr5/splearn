package org.study.splearn.domain.shared;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class EmailTest {

    @Test
    void equality() {
        // given
        var email1 = new Email("wjdrltjr5@naver.com");
        var email2 = new Email("wjdrltjr5@naver.com");
        // when
        // then
        assertTrue(email1.equals(email2));
    }

}