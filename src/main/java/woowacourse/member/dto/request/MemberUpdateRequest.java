package woowacourse.member.dto.request;

import javax.validation.constraints.NotBlank;

public class MemberUpdateRequest {

    @NotBlank
    private String nickname;

    public MemberUpdateRequest() {
    }

    public MemberUpdateRequest(String nickname) {
        this.nickname = nickname;
    }

    public String getNickname() {
        return nickname;
    }
}
