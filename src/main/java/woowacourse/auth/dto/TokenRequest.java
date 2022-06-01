package woowacourse.auth.dto;

import woowacourse.shoppingcart.domain.Email;
import woowacourse.shoppingcart.domain.Password;

public class TokenRequest {
    private String email;
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
