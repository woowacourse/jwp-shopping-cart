package woowacourse.shoppingcart.dto;

import woowacourse.shoppingcart.domain.customer.Customer;

public class SignUpRequest {

    private String userId;
    private String nickname;
    private String password;

    private SignUpRequest() {
    }

    public SignUpRequest(final String userId, final String nickname, final String password) {
        this.userId = userId;
        this.nickname = nickname;
        this.password = password;
    }

    public Customer toEntity() {
        return new Customer(null, userId, nickname, password);
    }

    public String getUserId() {
        return userId;
    }

    public String getNickname() {
        return nickname;
    }

    public String getPassword() {
        return password;
    }
}
