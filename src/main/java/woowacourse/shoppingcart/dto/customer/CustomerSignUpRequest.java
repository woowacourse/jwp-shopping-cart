package woowacourse.shoppingcart.dto.customer;

import javax.validation.constraints.Email;
import javax.validation.constraints.Pattern;

public class CustomerSignUpRequest {

    @Email(message = "잘못된 이메일 형식입니다.")
    private String email;

    @Pattern(regexp = "^(?=.*[A-Za-z])(?=.*[0-9])[A-Za-z0-9]{8,20}$", message = "잘못된 비밀번호 형식입니다.")
    private String password;

    @Pattern(regexp = "^[가-힣A-Za-z0-9]{2,8}$", message = "잘못된 닉네임 형식입니다.")
    private String nickname;

    public CustomerSignUpRequest() {
    }

    public CustomerSignUpRequest(String email, String password, String nickname) {
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
}
