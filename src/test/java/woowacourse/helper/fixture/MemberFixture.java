package woowacourse.helper.fixture;

import woowacourse.member.domain.Member;
import woowacourse.member.dto.MemberRegisterRequest;

public class MemberFixture {

    public static final String EMAIL = "member@gmail.com";
    public static final String PASSWORD = "Member1234!";
    public static final String NAME = "member";
    public static final String ENCODE_PASSWORD = "99a4c71c3553b29f63b9931c027cdc1afb12f545f78258e62a8fac043d9af89f";
    public static final String AUTHORIZATION_HEADER = "Authorization";

    public static Member createMember(String email, String password, String name) {
        return new Member(email, password, name);
    }
    public static MemberRegisterRequest createMemberRegisterRequest(String email, String password, String name) {
        return new MemberRegisterRequest(email, password, name);
    }
}
