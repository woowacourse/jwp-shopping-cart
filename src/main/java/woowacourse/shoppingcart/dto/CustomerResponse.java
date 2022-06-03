package woowacourse.shoppingcart.dto;

import woowacourse.shoppingcart.domain.customer.Customer;

public class CustomerResponse {
    private String userName;
    private String nickName;
    private int age;

    private CustomerResponse() {
    }

    private CustomerResponse(String userName, String nickName, int age) {
        this.userName = userName;
        this.nickName = nickName;
        this.age = age;
    }

    public static CustomerResponse from(Customer customer) {
        return new CustomerResponse(
                customer.getUserName(), customer.getNickName(), customer.getAge()
        );
    }

    public String getUserName() {
        return userName;
    }

    public String getNickName() {
        return nickName;
    }

    public int getAge() {
        return age;
    }
}
