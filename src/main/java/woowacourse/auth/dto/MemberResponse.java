package woowacourse.auth.dto;

import woowacourse.auth.domain.Member;

public class MemberResponse {

    private final String email;
    private final String nickname;

    public MemberResponse(Member member) {
        this(member.getEmail(), member.getNickname());
    }

    public MemberResponse(String email, String nickname) {
        this.email = email;
        this.nickname = nickname;
    }

    public String getEmail() {
        return email;
    }

    public String getNickname() {
        return nickname;
    }
}
