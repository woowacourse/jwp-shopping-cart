package woowacourse.shoppingcart.dao.entity;

import woowacourse.shoppingcart.domain.customer.Customer;
import woowacourse.shoppingcart.domain.customer.vo.Account;
import woowacourse.shoppingcart.domain.customer.vo.Address;
import woowacourse.shoppingcart.domain.customer.vo.Nickname;
import woowacourse.shoppingcart.domain.customer.vo.Password;
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

    public static CustomerEntity from(Customer customer) {
        return new CustomerEntity(
                customer.getId(),
                customer.getAccount().getValue(),
                customer.getNickname().getValue(),
                customer.getPassword().getValue(),
                customer.getAddress().getValue(),
                customer.getPhoneNumber().getValue()
        );
    }

    public Customer toCustomer() {
        return new Customer(id, new Account(account), new Nickname(nickname), Password.encryptText(password),
                new Address(address), new PhoneNumber(phoneNumber));
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
