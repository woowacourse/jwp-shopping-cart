package woowacourse.shoppingcart.dto.request;

import javax.validation.constraints.Pattern;

public class PasswordUpdateRequest {

    @Pattern(regexp = "^(?=.*[A-Za-z])(?=.*\\d)(?=.*[$@$!%*#?&])[A-Za-z\\d$@$!%*#?&]{8,20}$",
            message = "비밀번호 형식이 올바르지 않습니다.")
    private String password;

    private PasswordUpdateRequest() {
    }

    public PasswordUpdateRequest(String password) {
        this.password = password;
    }

    public String getPassword() {
        return password;
    }
}
