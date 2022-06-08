package woowacourse.shoppingcart.customer.application.dto.response;

public class CustomerResponse {

    private String email;
    private String nickname;

    public CustomerResponse(final String email, final String nickname) {
        this.email = email;
        this.nickname = nickname;
    }

    public CustomerResponse() {
    }

    public String getEmail() {
        return email;
    }

    public String getNickname() {
        return nickname;
    }
}
