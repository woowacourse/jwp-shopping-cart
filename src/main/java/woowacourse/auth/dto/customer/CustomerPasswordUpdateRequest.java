package woowacourse.auth.dto.customer;

import javax.validation.constraints.NotBlank;
import lombok.Getter;

@Getter
public class CustomerPasswordUpdateRequest {

    @NotBlank(message = "기존 비밀번호는 공백이 아니여야합니다")
    private String password;
    @NotBlank(message = "새로운 비밀번호는 공백이 아니여야합니다")
    private String newPassword;

    public CustomerPasswordUpdateRequest() {
    }

    public CustomerPasswordUpdateRequest(String password, String newPassword) {
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
