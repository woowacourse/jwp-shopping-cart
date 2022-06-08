package woowacourse.shoppingcart.dto.customer;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Pattern;

public class CustomerPasswordRequest {

    @NotBlank(message = "비밀번호가 입력되지 않았습니다.")
    @Pattern(regexp = "^(?=.*[A-Za-z])(?=.*\\d)(?=.*[$@$!%*#?&])[A-Za-z\\d$@$!%*#?&]{8,16}$",
            message = "비밀번호는 영문, 특수문자, 숫자를 필수로 조합하여 8 ~ 16 자를 입력해주세요.")
    private String password;

    public CustomerPasswordRequest() {
    }

    public CustomerPasswordRequest(final String password) {
        this.password = password;
    }

    public String getPassword() {
        return password;
    }
}
