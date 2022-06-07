package woowacourse.shoppingcart.dto.response;

import woowacourse.shoppingcart.domain.customer.Customer;

public class CustomerResponse {
    private String userName;
    private String password;
    private String nickName;
    private int age;

    private CustomerResponse() {
    }

    private CustomerResponse(String userName, String password, String nickName, int age) {
        this.userName = userName;
        this.password = password;
        this.nickName = nickName;
        this.age = age;
    }

    public static CustomerResponse from(Customer customer) {
        return new CustomerResponse(
                customer.getUserName(), customer.getPassword(), customer.getNickName(), customer.getAge()
        );
    }

    public String getUserName() {
        return userName;
    }

    public String getPassword() {
        return password;
    }

    public String getNickName() {
        return nickName;
    }

    public int getAge() {
        return age;
    }
}
