package cart.fixture;

import cart.domain.Member;
import cart.dto.MemberRequest;
import cart.dto.MemberResponse;

public class MemberFixture {
    public static final class FIRST_MEMBER {
        public static final String EMAIL = "first@wooteco.com";
        public static final String PASSWORD = "first_password";
        public static final Member MEMBER = new Member(EMAIL, PASSWORD);
        public static final Member MEMBER_WITH_ID = new Member(1L, EMAIL, PASSWORD);
        public static final MemberRequest REQUEST = new MemberRequest(EMAIL, PASSWORD);
        public static final MemberResponse RESPONSE = MemberResponse.from(MEMBER_WITH_ID);
    }

    public static final class SECOND_MEMBER {
        public static final String EMAIL = "second@wooteco.com";
        public static final String PASSWORD = "second_password";
        public static final Member MEMBER = new Member(EMAIL, PASSWORD);
        public static final Member MEMBER_WITH_ID = new Member(2L, EMAIL, PASSWORD);
        public static final MemberRequest REQUEST = new MemberRequest(EMAIL, PASSWORD);
        public static final MemberResponse RESPONSE = MemberResponse.from(MEMBER_WITH_ID);
    }
}
