package woowacourse.auth.dto;

import java.util.Objects;
import javax.validation.constraints.Email;
import javax.validation.constraints.Pattern;

public class TokenRequest {

    @Email(message = "1000:이메일 양식이 잘못 되었습니다.")
    private String email;

    @Pattern(regexp = "^(?=.*\\d)(?=.*[a-zA-Z])[0-9a-zA-Z]{8,20}", message = "1000:비밀번호 양식이 잘못 되었습니다.")
    private String password;

    private TokenRequest() {
    }

    public TokenRequest(String email, String password) {
        this.email = email;
        this.password = password;
    }

    public String getEmail() {
        return email;
    }

    public String getPassword() {
        return password;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        TokenRequest that = (TokenRequest) o;
        return Objects.equals(email, that.email) && Objects.equals(password, that.password);
    }

    @Override
    public int hashCode() {
        return Objects.hash(email, password);
    }
}
