package woowacourse.shoppingcart.dto.response;

import woowacourse.shoppingcart.domain.Member;

public class MemberResponse {

    private String email;
    private String nickname;

    private MemberResponse() {
    }

    public MemberResponse(Member member) {
        this.email = member.getEmail();
        this.nickname = member.getNickname();
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
