package woowacourse.shoppingcart.dto;

import woowacourse.shoppingcart.domain.customer.Customer;

public class CustomerResponse {

    private final long id;
    private final String account;
    private final String nickname;
    private final String address;
    private final PhoneNumberFormat phoneNumber;

    public CustomerResponse(long id, String account, String nickname, String address, PhoneNumberFormat phoneNumber) {
        this.id = id;
        this.account = account;
        this.nickname = nickname;
        this.address = address;
        this.phoneNumber = phoneNumber;
    }


    public static CustomerResponse of(Customer customer) {
        final PhoneNumberFormat phoneNumber = PhoneNumberFormat.of(customer.getPhoneNumber());
        return new CustomerResponse(customer.getId(), customer.getAccount(), customer.getNickname(),
                customer.getAddress(), phoneNumber);
    }

    public long getId() {
        return id;
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

    public PhoneNumberFormat getPhoneNumber() {
        return phoneNumber;
    }
}
