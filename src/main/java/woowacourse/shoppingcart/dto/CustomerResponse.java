package woowacourse.shoppingcart.dto;

import woowacourse.shoppingcart.domain.customer.Customer;

public class CustomerResponse {
    private Long id;
    private String userName;
    private String password;
    private String nickName;
    private int age;

    private CustomerResponse() {
    }

    public CustomerResponse(Long id, String userName, String password, String nickName, int age) {
        this.id = id;
        this.userName = userName;
        this.password = password;
        this.nickName = nickName;
        this.age = age;
    }

    public static CustomerResponse from(Customer customer) {
        return new CustomerResponse(
                null, customer.getUserName(), customer.getPassword(), customer.getNickName(), customer.getAge()
        );
    }

    public Long getId() {
        return id;
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
