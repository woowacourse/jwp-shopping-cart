package woowacourse.shoppingcart.application;

import org.springframework.stereotype.Service;
import woowacourse.shoppingcart.domain.Customer;
import woowacourse.shoppingcart.dto.SignUpRequest;

@Service
public class CustomerService {

    public Long signUp(final SignUpRequest signUpRequest) {
        Customer customer = signUpRequest.toEntity();

        return 0L;
    }
}
