package woowacourse.shoppingcart.dto;

import woowacourse.shoppingcart.domain.Customer;

public class CustomerResponse {

    private final long id;
    private final String account;
    private final String nickname;
    private final String address;
    private final PhoneNumberFormat phoneNumber;

    public CustomerResponse(final long id, final String account, final String nickname, final String address, final PhoneNumberFormat phoneNumber) {
        this.id = id;
        this.account = account;
        this.nickname = nickname;
        this.address = address;
        this.phoneNumber = phoneNumber;
    }

    public static CustomerResponse of(final Customer customer) {
        final PhoneNumberFormat phoneNumberFormat = PhoneNumberFormat.of(customer.getPhoneNumber().getValue());
        return new CustomerResponse(customer.getId(), customer.getAccount().getValue(),
                customer.getNickname().getValue(), customer.getAddress().getValue(), phoneNumberFormat);
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
