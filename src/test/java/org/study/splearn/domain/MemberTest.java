package org.study.splearn.domain;

import org.junit.jupiter.api.Test;
import static org.assertj.core.api.Assertions.*;


class MemberTest {

    @Test
    void createMember(){
        var member =  new Member("wjdrltjr5@splearn.app", "secret", "wjdrltjr");

        assertThat(member.getStatus()).isEqualTo(MemberStatus.PENDING);
    }
}