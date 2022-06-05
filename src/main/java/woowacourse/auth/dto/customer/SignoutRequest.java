package woowacourse.auth.dto.customer;

import javax.validation.constraints.NotBlank;
import lombok.Getter;

@Getter
public class SignoutRequest {
    @NotBlank
    private String password;

    public SignoutRequest() {
    }

    public SignoutRequest(String password) {
        this.password = password;
    }
}
