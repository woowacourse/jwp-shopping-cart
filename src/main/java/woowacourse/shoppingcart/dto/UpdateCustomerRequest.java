package woowacourse.shoppingcart.dto;

public class UpdateCustomerRequest {

    private String phoneNumber;
    private String address;

    public UpdateCustomerRequest() {
    }

    public UpdateCustomerRequest(String phoneNumber, String address) {
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
