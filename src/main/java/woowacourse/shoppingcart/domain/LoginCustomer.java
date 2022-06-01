package woowacourse.shoppingcart.domain;

import javax.validation.constraints.Email;

public class LoginCustomer {

    @Email(regexp = "^[_a-z0-9-]+(.[_a-z0-9-]+)*@(?:\\w+\\.)+\\w+$", message = "이메일 형식이 아닙니다.")
    private String loginId;

    public LoginCustomer(String loginId) {
        this.loginId = loginId;
    }

    public static LoginCustomer of(Customer customer) {
        return new LoginCustomer(customer.getLoginId());
    }

    public String getLoginId() {
        return loginId;
    }
}
