package woowacourse.shoppingcart.dto.response;

public class CustomerResponse {

    private final String account;
    private final String nickname;
    private final String address;
    private final PhoneNumberResponse phoneNumber;

    public CustomerResponse(String account, String nickname, String address,
            PhoneNumberResponse phoneNumber) {
        this.account = account;
        this.nickname = nickname;
        this.address = address;
        this.phoneNumber = phoneNumber;
    }

    public String getAccount() {
        return account;
    }

    public String getNickname() {
        return nickname;
    }

    public String getAddress() {
        return address;
    }

    public PhoneNumberResponse getPhoneNumber() {
        return phoneNumber;
    }
}
