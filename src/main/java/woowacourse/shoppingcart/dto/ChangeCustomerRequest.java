package woowacourse.shoppingcart.dto;

public class ChangeCustomerRequest {

    private String nickname;

    public ChangeCustomerRequest() {
    }

    public ChangeCustomerRequest(String nickname) {
        this.nickname = nickname;
    }

    public String getNickname() {
        return nickname;
    }
}
