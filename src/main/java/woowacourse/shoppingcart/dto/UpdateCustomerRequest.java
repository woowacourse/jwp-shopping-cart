package woowacourse.shoppingcart.dto;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;

public class UpdateCustomerRequest {

    @NotBlank
    @Size(max = 255)
    private final String address;

    @NotBlank
    @Pattern(regexp = "^010-[0-9]{4}-[0-9]{4}")
    private final String phoneNumber;

    private UpdateCustomerRequest() {
        this(null, null);
    }

    public UpdateCustomerRequest(String address, String phoneNumber) {
        this.address = address;
        this.phoneNumber = phoneNumber;
    }

    public String getAddress() {
        return address;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }
}
