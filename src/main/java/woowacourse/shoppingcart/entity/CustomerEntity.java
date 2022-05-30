package woowacourse.shoppingcart.entity;

import woowacourse.shoppingcart.domain.customer.Customer;
import woowacourse.shoppingcart.domain.customer.vo.PhoneNumber;

public class CustomerEntity {

    private final String account;
    private final String nickname;
    private final String password;
    private final String address;
    private final String phoneNumber;

    public CustomerEntity(String account, String nickname, String password, String address,
            String phoneNumber) {
        this.account = account;
        this.nickname = nickname;
        this.password = password;
        this.address = address;
        this.phoneNumber = phoneNumber;
    }

    public static CustomerEntity from(Customer customer) {
        PhoneNumber phoneNumber = customer.getPhoneNumber();
        return new CustomerEntity(customer.getAccount().getValue(),
                customer.getNickname().getValue(),
                customer.getPassword().getValue(),
                customer.getAddress().getValue(),
                phoneNumber.getStart() + phoneNumber.getMiddle() + phoneNumber.getLast());
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
