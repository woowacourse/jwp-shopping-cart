package woowacourse.shoppingcart.ui.dto;

public class CustomerUpdateRequest {

    private String nickname;
    private String address;
    private PhoneNumberRequest phoneNumber;

    private CustomerUpdateRequest() {
    }

    public CustomerUpdateRequest(String nickname, String address,
            PhoneNumberRequest phoneNumber) {
        this.nickname = nickname;
        this.address = address;
        this.phoneNumber = phoneNumber;
    }

    public String getNickname() {
        return nickname;
    }

    public String getAddress() {
        return address;
    }

    public PhoneNumberRequest getPhoneNumber() {
        return phoneNumber;
    }
}
