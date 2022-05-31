package woowacourse.shoppingcart.dto;

public class ChangeCustomerRequest {

    private final String nickname;

    public ChangeCustomerRequest(String nickname) {
        this.nickname = nickname;
    }

    public String getNickname() {
        return nickname;
    }
}
