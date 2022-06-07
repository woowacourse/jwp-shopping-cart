package woowacourse.shoppingcart.dto;

import woowacourse.shoppingcart.domain.customer.Customer;

public class CustomerResponse {
    private String username;
    private String nickname;
    private int age;

    private CustomerResponse() {
    }

    private CustomerResponse(String username, String nickname, int age) {
        this.username = username;
        this.nickname = nickname;
        this.age = age;
    }

    public static CustomerResponse from(Customer customer) {
        return new CustomerResponse(
                customer.getUsername(), customer.getNickname(), customer.getAge()
        );
    }

    public String getUsername() {
        return username;
    }

    public String getNickname() {
        return nickname;
    }

    public int getAge() {
        return age;
    }
}
