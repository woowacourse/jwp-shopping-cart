package woowacourse.auth.dto.customer;

import javax.validation.constraints.NotBlank;
import lombok.Getter;

@Getter
public class SignoutRequest {
    @NotBlank(message = "비밀번호는 공백이 아니여야합니다")
    private String password;

    public SignoutRequest() {
    }

    public SignoutRequest(String password) {
        this.password = password;
    }
}
