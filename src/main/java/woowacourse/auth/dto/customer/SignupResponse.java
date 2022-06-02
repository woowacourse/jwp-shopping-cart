package woowacourse.auth.dto.customer;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import woowacourse.auth.domain.Customer;

@NoArgsConstructor
@AllArgsConstructor
@Getter
public class SignupResponse {

    private String email;
    private String nickname;

    public SignupResponse(Customer customer) {
        this.email = customer.getEmail();
        this.nickname = customer.getNickname();
    }
}
