package woowacourse.shoppingcart.dto;

import woowacourse.shoppingcart.domain.Customer;

public class CustomerResponse {

    private String loginId;
    private String name;

    public CustomerResponse() {
    }

    public CustomerResponse(String loginId, String name) {
        this.loginId = loginId;
        this.name = name;
    }

    public static CustomerResponse of(LoginCustomer loginCustomer) {
        return new CustomerResponse(loginCustomer.getLoginId(), loginCustomer.getUsername());
    }

    public static CustomerResponse of(CustomerRequest customerRequest) {
        return new CustomerResponse(customerRequest.getLoginId(), customerRequest.getName());
    }

    public static CustomerResponse of(Customer customer) {
        return new CustomerResponse(customer.getLoginId(), customer.getUsername());
    }

    public String getLoginId() {
        return loginId;
    }

    public String getName() {
        return name;
    }
}
