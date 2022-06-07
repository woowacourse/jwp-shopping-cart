package woowacourse.shoppingcart.dto.customer;

import woowacourse.shoppingcart.domain.Customer;

public class CustomerLoginResponse {

    private String accessToken;
    private Long id;
    private String userId;
    private String nickname;

    private CustomerLoginResponse() {
    }

    private CustomerLoginResponse(final String accessToken, final Long id, final String userId, final String nickname) {
        this.accessToken = accessToken;
        this.id = id;
        this.userId = userId;
        this.nickname = nickname;
    }

    public static CustomerLoginResponse of(final String accessToken, final Customer customer) {
        return new CustomerLoginResponse(accessToken, customer.getId(), customer.getUserId(), customer.getNickname());
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
