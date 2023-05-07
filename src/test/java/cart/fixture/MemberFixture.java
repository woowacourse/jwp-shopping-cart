package cart.fixture;

import cart.domain.member.Email;
import cart.domain.member.Member;
import cart.domain.member.Password;
import cart.dto.MembetDto;

public class MemberFixture {
    public static class HERB {
        private static final String email = "herb@teco.com";
        private static final String password = "herbPassword";

        public static final Member MEMBER = new Member(1L, new Email(email), new Password(password));
        public static final MembetDto MEMBET_DTO = MembetDto.from(MEMBER);
    }

    public static class BLACKCAT {
        private static final String email = "blackcat@teco.com";
        private static final String password = "blackcatPassword";

        public static final Member MEMBER = new Member(2L, new Email(email), new Password(password));
        public static final MembetDto MEMBET_DTO = MembetDto.from(MEMBER);
    }
}
