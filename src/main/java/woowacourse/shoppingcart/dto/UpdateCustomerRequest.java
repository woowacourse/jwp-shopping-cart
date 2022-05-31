package woowacourse.shoppingcart.dto;

public class UpdateCustomerRequest {

    private String password;
    private String phoneNumber;
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
