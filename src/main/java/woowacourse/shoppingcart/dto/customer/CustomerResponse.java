package woowacourse.shoppingcart.dto.customer;

import com.fasterxml.jackson.annotation.JsonTypeInfo;
import com.fasterxml.jackson.annotation.JsonTypeName;
import woowacourse.shoppingcart.domain.customer.Customer;

@JsonTypeName("customer")
@JsonTypeInfo(include = JsonTypeInfo.As.WRAPPER_OBJECT, use = JsonTypeInfo.Id.NAME)
public class CustomerResponse {

    private String username;
    private String phoneNumber;
    private String address;

    private CustomerResponse() {
    }

    public CustomerResponse(final String username, final String phoneNumber, final String address) {
        this.username = username;
        this.phoneNumber = phoneNumber;
        this.address = address;
    }

    public static CustomerResponse from(final Customer customer) {
        return new CustomerResponse(customer.getUsername(), customer.getPhoneNumber(), customer.getAddress());
    }

    public String getUsername() {
        return username;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public String getAddress() {
        return address;
    }
}
