package woowacourse.shoppingcart.dto;

import woowacourse.shoppingcart.validation.PasswordCheck;
import woowacourse.shoppingcart.validation.UserNameCheck;

import javax.validation.constraints.*;

public class SignUpRequest {

    private String username;

    private String email;

    private String password;

    public SignUpRequest(String username, String email, String password) {
        this.username = username;
        this.email = email;
        this.password = password;
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
