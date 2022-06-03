package woowacourse.shoppingcart.dto;

import woowacourse.shoppingcart.domain.customer.Customer;

public class CustomerResponse {

    private final Long id;
    private final String email;
    private final String nickname;

    public CustomerResponse(Long id, String email, String nickname) {
        this.id = id;
        this.email = email;
        this.nickname = nickname;
    }

    public static CustomerResponse from(Customer customer) {
        return new CustomerResponse(customer.getId(), customer.getEmail(),customer.getNickname());
    }

    public Long getId() {
        return id;
    }

    public String getEmail() {
        return email;
    }

    public String getNickname() {
        return nickname;
    }
}
