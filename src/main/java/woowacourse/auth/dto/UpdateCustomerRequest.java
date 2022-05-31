package woowacourse.auth.dto;

public class UpdateCustomerRequest {

    private final String nickname;
    private final String address;
    private final PhoneNumber phoneNumber;

    public UpdateCustomerRequest(String nickname, String address, PhoneNumber phoneNumber) {
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

    public PhoneNumber getPhoneNumber() {
        return phoneNumber;
    }
}
