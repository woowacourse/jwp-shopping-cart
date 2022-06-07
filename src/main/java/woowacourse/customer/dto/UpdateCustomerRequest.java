package woowacourse.customer.dto;

import javax.validation.constraints.NotBlank;

public class UpdateCustomerRequest {

    @NotBlank(message = "전화번호를 입력해 주세요.")
    private String phoneNumber;

    @NotBlank(message = "주소를 입력해 주세요.")
    private String address;

    private UpdateCustomerRequest() {
    }

    public UpdateCustomerRequest(final String phoneNumber, final String address) {
        this.phoneNumber = phoneNumber;
        this.address = address;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public String getAddress() {
        return address;
    }
}
