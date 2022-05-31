package woowacourse.shoppingcart.dao.entity;

import woowacourse.shoppingcart.domain.Customer;

public class CustomerEntity {

    private final Long id;
    private final Customer customer;

    private CustomerEntity(Long id, Customer customer) {
        this.id = id;
        this.customer = customer;
    }

    public CustomerEntity(Long id, String email, String nickname, String password) {
        this(id, new Customer(email, nickname, password));
    }

    public Long getId() {
        return id;
    }

    public Customer getCustomer() {
        return customer;
    }

    public String getNickname() {
        return customer.getNickname();
    }
}
