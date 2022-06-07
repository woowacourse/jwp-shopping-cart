package woowacourse.shoppingcart.dto.customer;

public class CustomerUpdateResponse {

    private String nickname;

    public CustomerUpdateResponse() {
    }

    public CustomerUpdateResponse(final String nickname) {
        this.nickname = nickname;
    }

    public String getNickname() {
        return nickname;
    }
}
