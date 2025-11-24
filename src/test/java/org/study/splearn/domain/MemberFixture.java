package org.study.splearn.domain;

public class MemberFixture {

    public static MemberRegisterRequest createMemberRequest() {
        return new MemberRegisterRequest("wjdrltjr5@splearn.app", "wjdrltjr", "secret");
    }

    public static MemberRegisterRequest createMemberResisterRequest(String email) {
        return new MemberRegisterRequest(email, "test", "secret");
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
