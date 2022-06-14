package woowacourse.shoppingcart.application.dto;

import woowacourse.shoppingcart.domain.customer.Customer;

public class CustomerResponse {

    private final String account;
    private final String nickname;
    private final String address;
    private final PhoneNumberResponse phoneNumber;

    public CustomerResponse(String account, String nickname, String address, PhoneNumberResponse phoneNumber) {
        this.account = account;
        this.nickname = nickname;
        this.address = address;
        this.phoneNumber = phoneNumber;
    }

    public static CustomerResponse from(Customer customer) {
        return new CustomerResponse(
                customer.getAccount().getValue(),
                customer.getNickname().getValue(),
                customer.getAddress().getValue(),
                PhoneNumberResponse.from(customer.getPhoneNumber())
        );
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
