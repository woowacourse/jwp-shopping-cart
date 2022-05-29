package woowacourse.helper.fixture;

import woowacourse.member.domain.Member;
import woowacourse.member.dto.MemberRegisterRequest;

public class MemberFixture {

    public static final String EMAIL = "member@gmail.com";
    public static final String PASSWORD = "Member1234!";
    public static final String NAME = "member";

    public static Member createMember(String email, String password, String name) {
        return new Member(email, password, name);
    }
    public static MemberRegisterRequest createMemberRegisterRequest(String email, String password, String name) {
        return new MemberRegisterRequest(email, password, name);
    }
}
