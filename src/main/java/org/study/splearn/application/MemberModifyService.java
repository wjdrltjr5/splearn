package org.study.splearn.application;


import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.validation.annotation.Validated;
import org.study.splearn.application.provided.MemberFinder;
import org.study.splearn.application.provided.MemberRegister;
import org.study.splearn.application.required.EmailSender;
import org.study.splearn.application.required.MemberRepository;
import org.study.splearn.domain.*;


@Service
@Transactional
@Validated
@RequiredArgsConstructor
public class MemberModifyService implements MemberRegister {

    private final MemberRepository memberRepository;
    private final EmailSender emailSender;
    private final PasswordEncoder passwordEncoder;
    private final MemberFinder memberFinder;

    @Override
    public Member register(MemberRegisterRequest registerRequest) {

        checkDuplicateEmail(registerRequest);

        Member member = Member.register(registerRequest, passwordEncoder);

        memberRepository.save(member);

        sendWelcomeEmail(member);

        return member;
    }
    /*문자열을 결합하는 연산은 비싼 연산 람다로 함수를 만들어서 던지면 실행할때만 해당 연산이 실행 + 예외 오브젝트가 생성됨*/
    @Override
    public Member activate(Long memberId) {

        Member member = memberFinder.find(memberId);

        member.activate();

        return memberRepository.save(member); // 더티체킹이 아니라 이렇게 하는 이유는 spring data에서는 update 상황에서도 save를 호출하라고 공식적으로 말한다.
        // 이유는 => 스프링 데이터는 Jpa만을 위한 기술이 아니다. noSQL등 여러 기술이 포함된 기술 즉 스프링 데이터는 추상화 기술 추후에 다른 종류 NoSql로 변경되더라도 손댈 필요가 없다.
        // 이유2 => Spring domain event을 발생시키기 위해서
    }

    private void sendWelcomeEmail(Member member) {
        emailSender.send(member.getEmail(), "등록을 완료해주세요", "아래 링크를 클릭해서 등록을 완료해주세요");
    }

    private void checkDuplicateEmail(MemberRegisterRequest registerRequest) {
        if(memberRepository.findByEmail(new Email(registerRequest.email())).isPresent()){
            throw new DuplicateEmailException("이미 사용중인 Email입니다.");
        }
    }
}
