package woowacourse.auth.ui.dto.request;

import javax.validation.constraints.NotBlank;
import woowacourse.auth.application.dto.request.MemberUpdateServiceRequest;

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

    public MemberUpdateServiceRequest toServiceDto() {
        return new MemberUpdateServiceRequest(nickname);
    }
}
