package woowacourse.customer.dto;

import com.fasterxml.jackson.annotation.JsonTypeInfo;
import com.fasterxml.jackson.annotation.JsonTypeName;

import woowacourse.customer.domain.Customer;

@JsonTypeName(value = "customer")
@JsonTypeInfo(include = JsonTypeInfo.As.WRAPPER_OBJECT, use = JsonTypeInfo.Id.NAME)
public class CustomerResponse {

    private String username;
    private String phoneNumber;
    private String address;

    public CustomerResponse() {
    }

    public CustomerResponse(String username, String phoneNumber, String address) {
        this.username = username;
        this.phoneNumber = phoneNumber;
        this.address = address;
    }

    public static CustomerResponse from(Customer customer) {
        return new CustomerResponse(
            customer.getUsername().getValue(),
            customer.getPhoneNumber().getValue(),
            customer.getAddress()
        );
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
