package woowacourse.shoppingcart.dto.request;

import javax.validation.constraints.NotBlank;
import org.hibernate.validator.constraints.Length;

public class PasswordRequest {
    @NotBlank(message = "현재 비밀번호를 입력해주세요.")
    @Length(min = 8, max = 20, message = "비밀번호는 8자 이상 20자 이하여야 합니다.")
    private String oldPassword;

    @NotBlank(message = "변경할 비밀번호를 입력해주세요.")
    @Length(min = 8, max = 20, message = "비밀번호는 8자 이상 20자 이하여야 합니다.")
    private String newPassword;

    private PasswordRequest() {
    }

    public PasswordRequest(String oldPassword, String newPassword) {
        this.oldPassword = oldPassword;
        this.newPassword = newPassword;
    }

    public String getOldPassword() {
        return oldPassword;
    }

    public String getNewPassword() {
        return newPassword;
    }
}
