package woowacourse.shoppingcart.customer.application.dto.request;

import javax.validation.constraints.NotNull;

public class CustomerProfileUpdateRequest {

    @NotNull
    private String nickname;

    public CustomerProfileUpdateRequest() {
    }

    public CustomerProfileUpdateRequest(final String nickname) {
        this.nickname = nickname;
    }

    public String getNickname() {
        return nickname;
    }
}
