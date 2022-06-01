package woowacourse.shoppingcart.dto;

import javax.validation.constraints.NotBlank;
import woowacourse.shoppingcart.domain.Nickname;

public class CustomerProfileRequest {

    @NotBlank
    private String nickname;

    private CustomerProfileRequest() {
    }

    public CustomerProfileRequest(String nickname) {
        this.nickname = nickname;
    }

    public Nickname toNickname() {
        return new Nickname(nickname);
    }

    public String getNickname() {
        return nickname;
    }
}
