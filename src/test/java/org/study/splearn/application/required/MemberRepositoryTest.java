package org.study.splearn.application.required;

import jakarta.persistence.EntityManager;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.study.splearn.domain.Member;
import org.study.splearn.domain.MemberFixture;

import static org.assertj.core.api.Assertions.assertThat;
import static org.study.splearn.domain.MemberFixture.*;

@DataJpaTest
class MemberRepositoryTest {
    @Autowired
    MemberRepository memberRepository;

    @Autowired
    EntityManager entityManager;

    @Test
    void createMember() {
        Member member = Member.register(createMemberRequest(), craetePasswordEncoder());

        memberRepository.save(member);

        assertThat(member.getId()).isNotNull();

        entityManager.flush();
    }
}