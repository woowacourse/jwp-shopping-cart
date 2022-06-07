package woowacourse.shoppingcart.dto.response;

import woowacourse.shoppingcart.domain.customer.Customer;

public class CustomerResponse {
    private String username;
    private String password;
    private String nickname;
    private int age;

    private CustomerResponse() {
    }

    private CustomerResponse(String username, String password, String nickname, int age) {
        this.username = username;
        this.password = password;
        this.nickname = nickname;
        this.age = age;
    }

    public static CustomerResponse from(Customer customer) {
        return new CustomerResponse(
                customer.getUserName(), customer.getPassword(), customer.getNickName(), customer.getAge()
        );
    }

    public String getUsername() {
        return username;
    }

    public String getPassword() {
        return password;
    }

    public String getNickname() {
        return nickname;
    }

    public int getAge() {
        return age;
    }
}
