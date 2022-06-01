package woowacourse.shoppingcart.dto;

import woowacourse.shoppingcart.domain.Customer;

public class CustomerUpdateRequest {

    private String nickname;

    public CustomerUpdateRequest() {
    }

    public CustomerUpdateRequest(final String nickname) {
        this.nickname = nickname;
    }

    public Customer updatedCustomer(final Customer oldCustomer) {
        return new Customer(
                oldCustomer.getId(),
                oldCustomer.getUsername(),
                oldCustomer.getPassword(),
                nickname
        );
    }

    public String getNickname() {
        return nickname;
    }
}
