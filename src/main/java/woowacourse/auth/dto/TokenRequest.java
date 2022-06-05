package woowacourse.auth.dto;

import javax.validation.constraints.NotBlank;
import woowacourse.shoppingcart.domain.customer.Email;
import woowacourse.shoppingcart.domain.customer.Password;

public class TokenRequest {

    @javax.validation.constraints.Email
    private String email;
    @NotBlank
    private String password;

    public TokenRequest() {
    }

    public TokenRequest(String email, String password) {
        this.email = email;
        this.password = password;
    }

    public Email toEmail() {
        return new Email(email);
    }

    public Password toPassword() {
        return new Password(password);
    }

    public String getEmail() {
        return email;
    }

    public String getPassword() {
        return password;
    }
}
