package woowacourse.customer.dto;

import javax.validation.constraints.NotBlank;

public class UpdatePasswordRequest {

    @NotBlank(message = "새 비밀번호를 입력해 주세요.")
    private String password;

    private UpdatePasswordRequest() {
    }

    public UpdatePasswordRequest(final String password) {
        this.password = password;
    }

    public String getPassword() {
        return password;
    }
}
