package org.study.splearn.domain.member;

public class MemberFixture {

    public static MemberRegisterRequest createMemberRegisterRequest() {
        return new MemberRegisterRequest("wjdrltjr5@splearn.app", "wjdrltjr", "secret111");
    }

    public static MemberRegisterRequest createMemberRegisterRequest(String email) {
        return new MemberRegisterRequest(email, "test22", "secret111");
    }

    public static PasswordEncoder craetePasswordEncoder() {
        return new PasswordEncoder() {
            @Override
            public String encode(String password) {
                return password.toUpperCase();
            }

            @Override
            public boolean matches(String password, String passwordHash) {
                return encode(password).equals(passwordHash);
            }
        };
    }


}
