package woowacourse.shoppingcart.dto;

import javax.validation.constraints.Email;
import woowacourse.auth.support.PasswordCheck;
import woowacourse.auth.support.UsernameCheck;
import woowacourse.shoppingcart.domain.Customer;

public class SignUpRequest {

    @UsernameCheck
    private String username;
    @Email(regexp = "^[a-zA-Z0-9+-\\_.]+@[a-zA-Z0-9-]+\\.[a-zA-Z0-9-.]+$",
            message = "Email에 한글과 공백은 입력할 수 없습니다.")
    private String email;
    @PasswordCheck
    private String password;

    public SignUpRequest() {
    }

    public SignUpRequest(String username, String email, String password) {
        this.username = username;
        this.email = email;
        this.password = password;
    }

    public Customer toCustomer() {
        return new Customer(username, email, password);
    }

    public String getUsername() {
        return username;
    }

    public String getEmail() {
        return email;
    }

    public String getPassword() {
        return password;
    }
}
