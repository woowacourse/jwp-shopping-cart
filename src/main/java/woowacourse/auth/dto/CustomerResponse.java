package woowacourse.auth.dto;

import com.fasterxml.jackson.annotation.JsonProperty;

public class CustomerResponse {
    @JsonProperty("customer")
    private CustomerDto customerDto;

    private CustomerResponse() {
    }

    public CustomerResponse(CustomerDto customerDto) {
        this.customerDto = customerDto;
    }

    public CustomerDto getCustomerDto() {
        return customerDto;
    }
}
