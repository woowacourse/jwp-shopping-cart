package woowacourse.shoppingcart.dto;

public class CustomerUpdateProfileRequest {

    private String nickname;

    private CustomerUpdateProfileRequest() {
    }

    public CustomerUpdateProfileRequest(final String nickname) {
        this.nickname = nickname;
    }

    public String getNickname() {
        return nickname;
    }
}
