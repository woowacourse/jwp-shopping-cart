package woowacourse.shoppingcart.dto.request;

import javax.validation.constraints.Pattern;

public class MemberUpdateRequest {

    @Pattern(regexp = "^[가-힣]{1,5}$", message = "닉네임 형식이 올바르지 않습니다.")
    private String nickname;

    private MemberUpdateRequest() {
    }

    public MemberUpdateRequest(String nickname) {
        this.nickname = nickname;
    }

    public String getNickname() {
        return nickname;
    }
}
