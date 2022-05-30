package woowacourse.auth.dto;

import woowacourse.auth.domain.Member;

public class AuthorizedMember {

    private final String email;
    private final String nickname;

    public AuthorizedMember(Member member) {
        this(member.getEmail(), member.getNickname());
    }

    public AuthorizedMember(String email, String nickname) {
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
