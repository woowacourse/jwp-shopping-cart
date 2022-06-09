package woowacourse.shoppingcart.entity;

import woowacourse.shoppingcart.domain.customer.Customer;
import woowacourse.shoppingcart.domain.customer.vo.PhoneNumber;

public class CustomerEntity {

    private final Long id;
    private final String account;
    private final String nickname;
    private final String password;
    private final String address;
    private final String phoneNumber;

    public CustomerEntity(Long id, String account, String nickname, String password,
            String address, String phoneNumber) {
        this.id = id;
        this.account = account;
        this.nickname = nickname;
        this.password = password;
        this.address = address;
        this.phoneNumber = phoneNumber;
    }

    public CustomerEntity(String account, String nickname, String password,
            String address, String phoneNumber) {
        this(null, account, nickname, password, address, phoneNumber);
    }

    public static CustomerEntity from(Customer customer) {
        PhoneNumber phoneNumber = customer.getPhoneNumber();
        return new CustomerEntity(customer.getAccount().getValue(),
                customer.getNickname().getValue(),
                customer.getPassword().getValue(),
                customer.getAddress().getValue(),
                phoneNumber.getValue());
    }

    public Long getId() {
        return id;
    }

    public String getAccount() {

        return account;
    }

    public String getNickname() {
        return nickname;
    }

    public String getPassword() {
        return password;
    }

    public String getAddress() {
        return address;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }
}
