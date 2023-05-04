package cart.factory.member;

import cart.domain.member.Member;

public class MemberFactory {

    public static Member createMember() {
        return Member.from(1L, "test@test.com", "!!abc123");
    }
}
