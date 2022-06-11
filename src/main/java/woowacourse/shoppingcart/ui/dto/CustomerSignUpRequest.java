package woowacourse.shoppingcart.ui.dto;

import woowacourse.shoppingcart.application.dto.CustomerSaveRequest;

import javax.validation.constraints.NotBlank;

public class CustomerSignUpRequest {

    @NotBlank(message = "[ERROR] 이메일은 공백일 수 없습니다.")
    private String email;
    @NotBlank(message = "[ERROR] 비밀번호는 공백일 수 없습니다.")
    private String password;
    @NotBlank(message = "[ERROR] 닉네임은 공백일 수 없습니다.")
    private String nickname;

    private CustomerSignUpRequest() {
    }

    public CustomerSignUpRequest(final String email, final String password, final String nickname) {
        this.email = email;
        this.password = password;
        this.nickname = nickname;
    }

    public CustomerSaveRequest toServiceRequest() {
        return new CustomerSaveRequest(email, password, nickname);
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
    public String toString() {
        return "CustomerSignUpRequest{" +
                "email='" + email + '\'' +
                ", password='" + password + '\'' +
                ", nickname='" + nickname + '\'' +
                '}';
    }
}
