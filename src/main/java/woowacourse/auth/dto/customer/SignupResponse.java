package woowacourse.auth.dto.customer;

import lombok.Getter;
import woowacourse.shoppingcart.domain.customer.Customer;

@Getter
public class SignupResponse {

    private String email;
    private String nickname;

    public SignupResponse() {
    }

    public SignupResponse(Customer customer) {
        this.email = customer.getEmail();
        this.nickname = customer.getNickname();
    }
}
