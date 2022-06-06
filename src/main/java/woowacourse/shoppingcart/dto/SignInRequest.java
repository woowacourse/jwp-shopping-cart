package woowacourse.shoppingcart.dto;

import woowacourse.shoppingcart.validation.PasswordCheck;

import javax.validation.constraints.*;

public class SignInRequest {

    private final String password;

    private final String email;

    public SignInRequest(String email, String password) {
        this.email = email;
        this.password = password;
    }

    public String getPassword() {
        return password;
    }

    public String getEmail() {
        return email;
    }
}
