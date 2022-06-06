package woowacourse.shoppingcart.dto.customer;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Pattern;

public class CustomerLoginRequest {

    @NotBlank(message = "이메일이 입력되지 않았습니다.")
    @Pattern(regexp = "^[a-zA-Z0-9+-_.]+@[a-zA-Z0-9-]+\\.[a-zA-Z0-9-.]+$",
            message = "아이디는 이메일 형식으로 입력해주세요.")
    private String userId;

    @NotBlank(message = "비밀번호가 입력되지 않았습니다.")
    @Pattern(regexp = "^(?=.*[A-Za-z])(?=.*\\d)(?=.*[$@$!%*#?&])[A-Za-z\\d$@$!%*#?&]{8,16}$",
            message = "비밀번호는 영문, 특수문자, 숫자를 필수로 조합하여 8 ~ 16 자를 입력해주세요.")
    private String password;

    private CustomerLoginRequest() {
    }

    public CustomerLoginRequest(final String userId, final String password) {
        this.userId = userId;
        this.password = password;
    }

    public String getUserId() {
        return userId;
    }

    public String getPassword() {
        return password;
    }
}
