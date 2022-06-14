package woowacourse.auth.dto.customer;

import javax.validation.constraints.NotBlank;
import lombok.Getter;

@Getter
public class CustomerUpdateRequest {

    @NotBlank(message = "닉네임은 공백이 아니여야합니다")
    private String nickname;
    @NotBlank(message = "기존 비밀번호는 공백이 아니여야합니다")
    private String password;
    @NotBlank(message = "새로운 비밀번호는 공백이 아니여야합니다")
    private String newPassword;

    public CustomerUpdateRequest() {
    }

    public CustomerUpdateRequest(String nickname, String password, String newPassword) {
        this.nickname = nickname;
        this.password = password;
        this.newPassword = newPassword;
    }
}
