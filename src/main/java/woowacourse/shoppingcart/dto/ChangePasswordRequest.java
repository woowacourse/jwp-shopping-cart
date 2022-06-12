package woowacourse.shoppingcart.dto;

import javax.validation.constraints.NotBlank;

public class ChangePasswordRequest {

    private static final String INVALID_PASSWORD = "[ERROR] 비밀번호는 공백 또는 빈 값일 수 없습니다.";

    @NotBlank(message = INVALID_PASSWORD)
    private String password;

    @NotBlank(message = INVALID_PASSWORD)
    private String newPassword;

    public ChangePasswordRequest() {
    }

    public ChangePasswordRequest(final String password, final String newPassword) {
        this.password = password;
        this.newPassword = newPassword;
    }

    public String getPassword() {
        return password;
    }

    public String getNewPassword() {
        return newPassword;
    }
}
