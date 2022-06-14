package woowacourse.shoppingcart.application.dto;

import woowacourse.shoppingcart.domain.Customer;

public class CustomerResponse {

    private Long id;
    private String email;
    private String nickname;

    public CustomerResponse() {
    }

    public CustomerResponse(Customer customer) {
        this(customer.getId(), customer.getEmail(), customer.getNickname());
    }

    public CustomerResponse(Long id, String email, String nickname) {
        this.id = id;
        this.email = email;
        this.nickname = nickname;
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
