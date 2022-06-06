package woowacourse.shoppingcart.ui.dto;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Size;

public class UpdateCustomerRequest {

    @NotEmpty(message = "주소는 비어있을 수 없습니다.")
    @Size(max = 255)
    private final String address;

    @NotEmpty(message = "전화번호는 비어있을 수 없습니다.")
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
