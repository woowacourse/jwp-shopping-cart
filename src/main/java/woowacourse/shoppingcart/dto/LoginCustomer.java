package woowacourse.shoppingcart.dto;

import woowacourse.shoppingcart.domain.Customer;

public class LoginCustomer {

    private final Long id;
    private final String email;
    private final String nickname;

    public LoginCustomer(Long id, String email, String nickname) {
        this.id = id;
        this.email = email;
        this.nickname = nickname;
    }

    public static LoginCustomer from(Customer customer) {
        return new LoginCustomer(customer.getId(), customer.getEmail(), customer.getNickname());
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
