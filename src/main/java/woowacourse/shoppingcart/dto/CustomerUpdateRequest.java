package woowacourse.shoppingcart.dto;

public class CustomerUpdateRequest {

    private String address;
    private String phoneNumber;

    private CustomerUpdateRequest() {
    }

    public CustomerUpdateRequest(String address, String phoneNumber) {
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
