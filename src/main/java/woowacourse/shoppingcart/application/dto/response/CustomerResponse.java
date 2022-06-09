package woowacourse.shoppingcart.application.dto.response;

import woowacourse.shoppingcart.domain.customer.Customer;

public class CustomerResponse {

    private final Long id;
    private final String userId;
    private final String nickname;

    private CustomerResponse(final Long id, final String userId, final String nickname) {
        this.id = id;
        this.userId = userId;
        this.nickname = nickname;
    }

    public static CustomerResponse from(final Customer customer) {
        return new CustomerResponse(customer.getId(), customer.getUserId(), customer.getNickname());
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
