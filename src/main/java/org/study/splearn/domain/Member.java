package org.study.splearn.domain;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.ToString;

import java.util.Objects;

import static org.springframework.util.Assert.*;

@Getter
@ToString
public class Member {

    private String email;
    private String passwordHash;
    private String nickname;
    private MemberStatus status;

    public Member(String email, String passwordHash, String nickname) {
        this.email = Objects.requireNonNull(email);
        this.passwordHash = Objects.requireNonNull(passwordHash);
        this.nickname = Objects.requireNonNull(nickname);

        this.status = MemberStatus.PENDING;
    }

    public void activate() {
        state(status == MemberStatus.PENDING, "PENDING 상태가 아닙니다.");

        this.status = MemberStatus.ACTIVE;
    }

    public void deactivate() {
        state(status == MemberStatus.ACTIVE, "ACTIVE 상태가 아닙니다.");

        this.status = MemberStatus.DEACTIVATED;
    }
}
