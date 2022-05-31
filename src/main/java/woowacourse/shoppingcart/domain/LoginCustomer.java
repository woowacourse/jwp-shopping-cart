package woowacourse.shoppingcart.domain;

public class LoginCustomer {

    private String loginId;
    private String name;

    private LoginCustomer() {
    }

    public LoginCustomer(String loginId, String name) {
        this.loginId = loginId;
        this.name = name;
    }

    public static LoginCustomer of(Customer customer) {
        return new LoginCustomer(customer.getLoginId(), customer.getName());
    }

    public String getLoginId() {
        return loginId;
    }

    public String getName() {
        return name;
    }

}
