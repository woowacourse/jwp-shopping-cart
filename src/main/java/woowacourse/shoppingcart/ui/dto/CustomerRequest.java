package woowacourse.shoppingcart.ui.dto;

import woowacourse.shoppingcart.domain.customer.Customer;
import woowacourse.shoppingcart.domain.customer.vo.Account;
import woowacourse.shoppingcart.domain.customer.vo.Address;
import woowacourse.shoppingcart.domain.customer.vo.Nickname;
import woowacourse.shoppingcart.domain.customer.vo.Password;

public class CustomerRequest {

    private String account;
    private String nickname;
    private String password;
    private String address;
    private PhoneNumberRequest phoneNumber;

    private CustomerRequest() {
    }

    public CustomerRequest(String account, String nickname, String password, String address,
                           PhoneNumberRequest phoneNumber) {
        this.account = account;
        this.nickname = nickname;
        this.password = password;
        this.address = address;
        this.phoneNumber = phoneNumber;
    }

    public Customer toCustomer() {
        return new Customer(null, new Account(account), new Nickname(nickname), Password.plainText(password),
                new Address(address), phoneNumber.toPhoneNumber());
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

    public PhoneNumberRequest getPhoneNumber() {
        return phoneNumber;
    }
}
