package woowacourse.shoppingcart.ui.dto;

import woowacourse.shoppingcart.application.dto.CustomerUpdateRequest;

import javax.validation.constraints.NotBlank;

public class CustomerChangeRequest {

    @NotBlank(message = "[ERROR] 닉네임은 공백일 수 없습니다.")
    private String nickname;

    public CustomerChangeRequest() {
    }

    public CustomerChangeRequest(final String nickname) {
        this.nickname = nickname;
    }

    public CustomerUpdateRequest toServiceRequest() {
        return new CustomerUpdateRequest(nickname);
    }

    public String getNickname() {
        return nickname;
    }
}
