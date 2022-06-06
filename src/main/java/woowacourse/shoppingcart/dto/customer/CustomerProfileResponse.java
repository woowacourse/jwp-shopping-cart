package woowacourse.shoppingcart.dto.customer;

import woowacourse.shoppingcart.entity.Customer;

public class CustomerProfileResponse {

    private Long id;
    private String userId;
    private String nickname;

    private CustomerProfileResponse() {
    }

    private CustomerProfileResponse(final Long id, final String userId, final String nickname) {
        this.id = id;
        this.userId = userId;
        this.nickname = nickname;
    }

    public static CustomerProfileResponse from(final Customer customer) {
        return new CustomerProfileResponse(customer.getId(), customer.getUserId(), customer.getNickname());
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
