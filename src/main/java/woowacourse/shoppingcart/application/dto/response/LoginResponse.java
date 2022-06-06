package woowacourse.shoppingcart.application.dto.response;

import woowacourse.shoppingcart.domain.customer.Customer;

public class LoginResponse {

    private final String accessToken;
    private final Long id;
    private final String userId;
    private final String nickname;

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
