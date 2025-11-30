package org.study.splearn.application.required;

import jakarta.persistence.EntityManager;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.dao.DataIntegrityViolationException;
import org.study.splearn.domain.Member;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.study.splearn.domain.MemberFixture.*;

@DataJpaTest
class MemberRepositoryTest {
    @Autowired
    MemberRepository memberRepository;

    @Autowired
    EntityManager entityManager;

    @Test
    void createMember() {
        Member member = Member.register(createMemberRegisterRequest(), craetePasswordEncoder());

        memberRepository.save(member);

        assertThat(member.getId()).isNotNull();

        entityManager.flush();
    }

    @Test
    void duplicateEmailFail() {
        // given
        memberRepository.save(Member.register(createMemberRegisterRequest(), craetePasswordEncoder()));
        // when
        // then
        assertThatThrownBy(() -> memberRepository.save(Member.register(createMemberRegisterRequest(), craetePasswordEncoder()))).isInstanceOf(DataIntegrityViolationException.class);

    }
}