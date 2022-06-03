package woowacourse.shoppingcart.dto;

import woowacourse.shoppingcart.entity.Customer;

public class LoginResponse {

    private String accessToken;
    private Long id;
    private String userId;
    private String nickname;

    private LoginResponse() {
    }

    private LoginResponse(final String accessToken, final Long id, final String userId, final String nickname) {
        this.accessToken = accessToken;
        this.id = id;
        this.userId = userId;
        this.nickname = nickname;
    }

    public static LoginResponse of(final String accessToken, final Customer customer) {
        return new LoginResponse(accessToken, customer.getId(), customer.getUserId(), customer.getNickname());
    }

    public String getAccessToken() {
        return accessToken;
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
