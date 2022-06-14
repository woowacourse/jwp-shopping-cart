package woowacourse.auth.dto.customer;

import javax.validation.constraints.NotBlank;
import lombok.Getter;
import woowacourse.shoppingcart.domain.customer.Customer;

@Getter
public class SignupRequest {

    @NotBlank(message = "이메일은 공백이 아니여야합니다")
    private String email;
    @NotBlank(message = "닉네임은 공백이 아니여야합니다")
    private String nickname;
    @NotBlank(message = "비밀번호는 공백이 아니여야합니다")
    private String password;

    public SignupRequest() {
    }

    public SignupRequest(String email, String nickname, String password) {
        this.email = email;
        this.nickname = nickname;
        this.password = password;
    }

    public Customer toEntity() {
        return new Customer(email, nickname, password);
    }
}

