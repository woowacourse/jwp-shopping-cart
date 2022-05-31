package woowacourse.shoppingcart.application;

import org.springframework.stereotype.Service;
import woowacourse.auth.dto.TokenRequest;
import woowacourse.shoppingcart.dto.CustomerLoginRequest;
import woowacourse.shoppingcart.dto.CustomerLoginResponse;
import woowacourse.shoppingcart.dto.CustomerRequest;
import woowacourse.shoppingcart.dto.CustomerResponse;
import woowacourse.shoppingcart.dto.CustomerUpdateRequest;
import woowacourse.shoppingcart.dto.PasswordRequest;

@Service
public class CustomerService {

    public Long signUp(final CustomerRequest request) {
        return null;
    }

    public CustomerLoginResponse login(final CustomerLoginRequest request) {
        return null;
    }

    public CustomerResponse findById(final TokenRequest request) {
        return null;
    }

    public void update(final TokenRequest tokenRequest, final CustomerUpdateRequest customerUpdateRequest) {
    }

    public void updatePassword(final TokenRequest tokenRequest, final PasswordRequest passwordRequest) {
    }
}
