package woowacourse.auth.dto;

import javax.validation.constraints.NotBlank;
import woowacourse.shoppingcart.domain.customer.Email;
import woowacourse.shoppingcart.domain.customer.Password;

public class TokenRequest {

    @javax.validation.constraints.Email(message = "이메일 형식이 잘못되었습니다.")
    private String email;
    @NotBlank(message = "공백이 들어올 수 없습니다.")
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
