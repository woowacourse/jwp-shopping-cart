package woowacourse.shoppingcart.dto;

import woowacourse.shoppingcart.domain.Customer;

public class CustomerResponse {

    private Long id;
    private String userId;
    private String nickname;

    public CustomerResponse() {
    }

    public CustomerResponse(final Long id, final String userId, final String nickname) {
        this.id = id;
        this.userId = userId;
        this.nickname = nickname;
    }

    public static CustomerResponse of(final Customer customer) {
        return new CustomerResponse(customer.getId(), customer.getUsername(), customer.getNickname());
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
