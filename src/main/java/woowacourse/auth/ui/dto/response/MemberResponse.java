package woowacourse.auth.ui.dto.response;

import woowacourse.auth.application.dto.response.MemberServiceResponse;

public class MemberResponse {

    private String email;
    private String nickname;

    public MemberResponse() {
    }

    public MemberResponse(MemberServiceResponse memberServiceResponse) {
        this.email = memberServiceResponse.getEmail();
        this.nickname = memberServiceResponse.getNickname();
    }

    public String getEmail() {
        return email;
    }

    public String getNickname() {
        return nickname;
    }
}
