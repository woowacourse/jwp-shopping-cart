package woowacourse.shoppingcart.customer.application.dto.request;

public class CustomerProfileUpdateRequest {

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
