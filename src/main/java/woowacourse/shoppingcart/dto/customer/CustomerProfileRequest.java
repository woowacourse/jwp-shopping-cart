package woowacourse.shoppingcart.dto.customer;

import javax.validation.constraints.NotBlank;
import woowacourse.shoppingcart.domain.customer.Nickname;

public class CustomerProfileRequest {

    @NotBlank(message = "공백이 들어올 수 없습니다.")
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
