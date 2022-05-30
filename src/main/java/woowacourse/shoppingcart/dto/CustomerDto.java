package woowacourse.shoppingcart.dto;

import woowacourse.auth.dto.PhoneNumber;
import woowacourse.shoppingcart.domain.Customer;

public class CustomerDto {

    private final long id;
    private final String account;
    private final String nickname;
    private final String address;
    private final PhoneNumber phoneNumber;

    public CustomerDto(long id, String account, String nickname, String address, PhoneNumber phoneNumber) {
        this.id = id;
        this.account = account;
        this.nickname = nickname;
        this.address = address;
        this.phoneNumber = phoneNumber;
    }


    public static CustomerDto of(Customer customer) {
        final PhoneNumber phoneNumber = PhoneNumber.of(customer.getPhoneNumber());
        return new CustomerDto(customer.getId(), customer.getAccount(), customer.getNickname(), customer.getAddress(), phoneNumber);
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

    public PhoneNumber getPhoneNumber() {
        return phoneNumber;
    }
}
