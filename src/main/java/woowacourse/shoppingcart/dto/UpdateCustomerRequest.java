package woowacourse.shoppingcart.dto;

import javax.validation.constraints.NotNull;

public class UpdateCustomerRequest {

    @NotNull
    private String password;

    @NotNull
    private String phoneNumber;

    @NotNull
    private String address;

    private UpdateCustomerRequest() {
    }

    public UpdateCustomerRequest(String password) {
        this.password = password;
    }

    public UpdateCustomerRequest(String phoneNumber, String address) {
        this.phoneNumber = phoneNumber;
        this.address = address;
    }

    public String getPassword() {
        return password;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public String getAddress() {
        return address;
    }
}
