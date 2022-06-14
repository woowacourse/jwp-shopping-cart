package woowacourse.auth.dto.customer;

import javax.validation.constraints.NotBlank;
import lombok.Getter;

@Getter
public class CustomerProfileUpdateRequest {

    @NotBlank(message = "닉네임은 공백이 아니여야합니다")
    private String nickname;

    public CustomerProfileUpdateRequest() {
    }

    public CustomerProfileUpdateRequest(String nickname) {
        this.nickname = nickname;
    }

    public String getNickname() {
        return nickname;
    }
}
