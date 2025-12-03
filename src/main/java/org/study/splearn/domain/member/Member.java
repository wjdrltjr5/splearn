package org.study.splearn.domain.member;

import jakarta.persistence.*;
import jakarta.validation.Valid;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;
import org.hibernate.annotations.NaturalId;
import org.hibernate.annotations.NaturalIdCache;
import org.study.splearn.domain.AbstractEntity;
import org.study.splearn.domain.shared.Email;

import java.util.Objects;

import static java.util.Objects.*;
import static org.springframework.util.Assert.*;

@Getter
@ToString(callSuper = true, exclude = "detail")
@Entity
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@NaturalIdCache
public class Member extends AbstractEntity {

    @NaturalId
    private Email email;

    private String passwordHash;

    private String nickname;

    private MemberStatus status;

    @OneToOne(cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private MemberDetail detail;

    public static Member register(@Valid MemberRegisterRequest registerRequest, PasswordEncoder passwordEncoder) {
        Member member = new Member();

        member.email = new Email(registerRequest.email());
        member.nickname = requireNonNull(registerRequest.nickname());
        member.passwordHash = requireNonNull(passwordEncoder.encode(registerRequest.password()));

        member.status = MemberStatus.PENDING;

        member.detail = MemberDetail.create();

        return member;
    }

    public void activate() {
        state(status == MemberStatus.PENDING, "PENDING 상태가 아닙니다.");

        this.status = MemberStatus.ACTIVE;
        this.detail.activate();
    }

    public void deactivate() {
        state(status == MemberStatus.ACTIVE, "ACTIVE 상태가 아닙니다.");

        this.status = MemberStatus.DEACTIVATED;
        this.detail.deactivate();
    }

    public boolean verifyPassword(String password, PasswordEncoder passwordEncoder) {
        return passwordEncoder.matches(password, this.passwordHash);
    }

    public void changeNickname(String nickname) {
        this.nickname = requireNonNull(nickname);
    }

    public void updateInfo(MemberInfoUpdateRequest updateRequest){
        this.nickname = Objects.requireNonNull(updateRequest.nickname());

        this.detail.updateInfo(updateRequest);
    }

    public void changePassword(String password, PasswordEncoder passwordEncoder) {
        this.passwordHash = passwordEncoder.encode(requireNonNull(password));
    }

    public boolean isActivated() {
        return this.status == MemberStatus.ACTIVE;
    }
}
