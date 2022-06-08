package woowacourse.shoppingcart.dto.customer;

public class CustomerResponse {
    private String email;
    private String nickname;

    public CustomerResponse() {
    }

    public CustomerResponse(String email, String nickname) {
        this.email = email;
        this.nickname = nickname;
    }

    public String getEmail() {
        return email;
    }

    public String getNickname() {
        return nickname;
    }
}
