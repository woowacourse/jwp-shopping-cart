package cart.presentation.adapter;

import cart.business.domain.member.Member;
import cart.business.domain.member.MemberEmail;
import cart.business.domain.member.MemberId;
import cart.business.domain.member.MemberPassword;
import cart.presentation.dto.AuthInfo;

public class MemberConverter {

    private static final Integer NULL_ID = null;

    public static Member toEntity(AuthInfo authInfo) {
        return new Member(new MemberId(NULL_ID), new MemberEmail(authInfo.getEmail()),
                new MemberPassword(authInfo.getPassword())
        );
    }
}
