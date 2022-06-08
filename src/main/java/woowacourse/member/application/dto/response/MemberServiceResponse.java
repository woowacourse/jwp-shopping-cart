package woowacourse.member.application.dto.response;

import woowacourse.member.domain.Member;

public class MemberServiceResponse {

    private final String email;
    private final String nickname;

    public MemberServiceResponse(Member member) {
        this.email = member.getEmail();
        this.nickname = member.getNickname();
    }

    public String getEmail() {
        return email;
    }

    public String getNickname() {
        return nickname;
    }
}
