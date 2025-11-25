package org.study.splearn.application;


import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.study.splearn.application.provided.MemberRegister;
import org.study.splearn.application.required.EmailSender;
import org.study.splearn.application.required.MemberRepository;
import org.study.splearn.domain.Member;
import org.study.splearn.domain.MemberRegisterRequest;
import org.study.splearn.domain.PasswordEncoder;

@Service
@RequiredArgsConstructor
public class MemberService implements MemberRegister {

    private final MemberRepository memberRepository;
    private final EmailSender emailSender;
    private final PasswordEncoder passwordEncoder;

    @Override
    public Member register(MemberRegisterRequest registerRequest) {
        //check
        Member member = Member.register(registerRequest, passwordEncoder);

        memberRepository.save(member);

        emailSender.send(member.getEmail(), "등록을 완료해주세요", "아래 링크를 클릭해서 등록을 완료해주세요");

        return member;
    }
}
