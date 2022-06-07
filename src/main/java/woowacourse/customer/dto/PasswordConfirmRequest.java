package woowacourse.customer.dto;

import javax.validation.constraints.NotBlank;

public class PasswordConfirmRequest {

    @NotBlank(message = "새 비밀번호를 입력해 주세요.")
    private String password;

    public PasswordConfirmRequest() {
    }

    public PasswordConfirmRequest(final String password) {
        this.password = password;
    }

    public String getPassword() {
        return password;
    }
}
