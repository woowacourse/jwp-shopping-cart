package woowacourse.shoppingcart.dto;

import woowacourse.shoppingcart.domain.customer.Customer;

public class LoginResponse {

    private String token;
    private Long id;
    private String userId;
    private String nickname;

    private LoginResponse() {
    }

    private LoginResponse(final String token, final Long id, final String userId, final String nickname) {
        this.token = token;
        this.id = id;
        this.userId = userId;
        this.nickname = nickname;
    }

    public static LoginResponse ofExceptToken(final Customer customer) {
        return new LoginResponse(null, customer.getId(), customer.getUserId(), customer.getNickname());
    }

    public String getToken() {
        return token;
    }

    public Long getId() {
        return id;
    }

    public String getUserId() {
        return userId;
    }

    public String getNickname() {
        return nickname;
    }
}
