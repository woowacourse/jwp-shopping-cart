package woowacourse.shoppingcart.dto.customer;

import javax.validation.constraints.NotBlank;
import woowacourse.shoppingcart.domain.customer.Customer;

public class CustomerUpdateRequest {

    @NotBlank
    private String phoneNumber;

    @NotBlank
    private String address;

    private CustomerUpdateRequest() {
    }

    public CustomerUpdateRequest(final String phoneNumber, final String address) {
        this.phoneNumber = phoneNumber;
        this.address = address;
    }

    public Customer toCustomerWithId(final Long id) {
        return Customer.builder()
                .id(id)
                .phoneNumber(phoneNumber)
                .address(address)
                .build();
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public String getAddress() {
        return address;
    }
}
