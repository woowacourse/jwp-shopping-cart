package woowacourse.auth.dto.customer;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;
import lombok.Getter;
import woowacourse.shoppingcart.domain.customer.Customer;

@Getter
public class SignupRequest {

    @NotBlank
    @Email
    private String email;
    @Size(min = 2, max = 10, message = "닉네임은 2~10 길이어야 합니다.")
    private String nickname;
    @NotBlank
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

