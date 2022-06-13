package woowacourse.shoppingcart.dto;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import woowacourse.shoppingcart.domain.customer.Customer;

public class SignUpRequest {

    private static final String BLANK_USERNAME = "[ERROR] 이름은 공백 또는 빈 값일 수 없습니다.";
    private static final String EMAIL_REGEX = "^[a-zA-Z0-9+-\\_.]+@[a-zA-Z0-9-]+\\.[a-zA-Z0-9-.]+${1,64}";
    private static final String BLANK_EMAIL = "[ERROR] 이메일은 공백 또는 빈 값일 수 없습니다.";
    private static final String INVALID_PATTERN_EMAIL = "[ERROR] 올바른 이메일 형식이 아닙니다.";
    private static final String INVALID_PASSWORD = "[ERROR] 비밀번호는 공백 또는 빈 값일 수 없습니다.";

    @NotNull(message = BLANK_EMAIL)
    @Email(message = INVALID_PATTERN_EMAIL, regexp = EMAIL_REGEX)
    private String email;

    @NotBlank(message = BLANK_USERNAME)
    private String username;

    @NotBlank(message = INVALID_PASSWORD)
    private String password;

    public SignUpRequest() {
    }

    public SignUpRequest(final String username, final String email, final String password) {
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

    public Customer toCustomer() {
        return new Customer(username, email, password);
    }
}
