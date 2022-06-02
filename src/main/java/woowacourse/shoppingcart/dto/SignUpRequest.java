package woowacourse.shoppingcart.dto;

import javax.validation.constraints.Pattern;
import org.hibernate.validator.constraints.Length;
import woowacourse.auth.support.PasswordCheck;
import woowacourse.auth.support.UsernameCheck;
import woowacourse.shoppingcart.domain.Customer;

public class SignUpRequest {

    @UsernameCheck
    @Length(max = 32)
    private String username;

    @Pattern(regexp = "^[a-zA-Z0-9+-\\_.]+@[a-zA-Z0-9-]+\\.[a-zA-Z0-9-.]+$")
    @Length(max = 64)
    private String email;
    @PasswordCheck
    @Length(min = 6)
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
