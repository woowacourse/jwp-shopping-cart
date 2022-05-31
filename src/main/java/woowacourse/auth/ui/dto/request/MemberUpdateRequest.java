package woowacourse.auth.ui.dto.request;

import javax.validation.constraints.Pattern;
import woowacourse.auth.application.dto.request.MemberUpdateServiceRequest;

public class MemberUpdateRequest {

    @Pattern(regexp = "^[가-힣]{1,5}$", message = "닉네임 형식이 올바르지 않습니다.")
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
