package woowacourse.shoppingcart.dto;

import java.util.Objects;
import javax.validation.constraints.Email;
import javax.validation.constraints.Pattern;

public class CustomerCreationRequest {

    @Email(message = "1000:이메일 양식이 잘못 되었습니다.")
    private String email;

    @Pattern(regexp = "^(?=.*\\d)(?=.*[a-zA-Z])[0-9a-zA-Z]{8,20}", message = "1000:비밀번호 양식이 잘못 되었습니다.")
    private String password;

    @Pattern(regexp = "[a-zA-Z0-9가-힣]{2,8}", message = "1000:닉네임 양식이 잘못 되었습니다.")
    private String nickname;

    private CustomerCreationRequest() {
    }

    public CustomerCreationRequest(String email, String password, String nickname) {
        this.email = email;
        this.password = password;
        this.nickname = nickname;
    }

    public String getEmail() {
        return email;
    }

    public String getPassword() {
        return password;
    }

    public String getNickname() {
        return nickname;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        CustomerCreationRequest that = (CustomerCreationRequest) o;
        return Objects.equals(email, that.email) && Objects.equals(password, that.password)
                && Objects.equals(nickname, that.nickname);
    }

    @Override
    public int hashCode() {
        return Objects.hash(email, password, nickname);
    }
}
