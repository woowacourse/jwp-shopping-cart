package woowacourse.shoppingcart.dto;

public class UpdateCustomerRequest {

    private final String address;
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
