package woowacourse.customer.dto;

public class UpdateCustomerRequest {

    private String phoneNumber;

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
