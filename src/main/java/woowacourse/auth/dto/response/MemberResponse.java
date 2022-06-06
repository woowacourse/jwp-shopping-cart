package woowacourse.auth.dto.response;

import woowacourse.auth.domain.Member;

public class MemberResponse {

    private String email;
    private String nickname;

    public MemberResponse() {
    }

    public MemberResponse(Member member) {
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
