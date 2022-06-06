package woowacourse.shoppingcart.dto;

import woowacourse.shoppingcart.domain.Customer;

public class CustomerResponse {

    private final String email;
    private final String name;
    private final String phone;
    private final String address;

    public CustomerResponse(String email, String name, String phone, String address) {
        this.email = email;
        this.name = name;
        this.phone = phone;
        this.address = address;
    }

    public static CustomerResponse from(Customer customer) {
        return new CustomerResponse(customer.getEmail(), customer.getName(), customer.getPhone(),
                customer.getAddress());
    }

    public String getEmail() {
        return email;
    }

    public String getName() {
        return name;
    }

    public String getPhone() {
        return phone;
    }

    public String getAddress() {
        return address;
    }
}
