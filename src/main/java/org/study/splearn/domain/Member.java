package org.study.splearn.domain;

public class Member {
    private String email;
    private String passwordHash;
    private String nickname;
    private MemberStatus status;

    public Member(String email, String passwordHash, String nickname) {
        this.email = email;
        this.passwordHash = passwordHash;
        this.nickname = nickname;
        this.status = MemberStatus.PENDING;
    }

    public String getEmail() {
        return email;
    }

    public String getPasswordHash() {
        return passwordHash;
    }

    public String getNickname() {
        return nickname;
    }

    public MemberStatus getStatus() {
        return status;
    }
}
