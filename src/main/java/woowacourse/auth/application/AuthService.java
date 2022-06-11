package woowacourse.auth.application;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import woowacourse.auth.dto.TokenRequest;
import woowacourse.auth.exception.LoginFailException;
import woowacourse.shoppingcart.application.CustomerService;
import woowacourse.shoppingcart.domain.Customer;
import woowacourse.shoppingcart.exception.notfound.InvalidCustomerException;
import woowacourse.utils.CryptoUtils;
import woowacourse.utils.JwtTokenProvider;

@Service
@RequiredArgsConstructor
public class AuthService {

    private final JwtTokenProvider jwtTokenProvider;
    private final CustomerService customerService;

    public String createToken(TokenRequest request) {
        encryptPassword(request);
        try {
            Customer loginCustomer = customerService.findByEmailAndPassword(request.getEmail(), request.getPassword());
            return jwtTokenProvider.createToken(loginCustomer.getEmail());
        } catch (InvalidCustomerException exception) {
            throw new LoginFailException();
        }
    }

    public Customer findCustomerByToken(String token) {
        String email = jwtTokenProvider.restorePayload(token);
        return customerService.findByEmail(email);
    }

    private void encryptPassword(TokenRequest request) {
        String originPassword = request.getPassword();
        String encryptPassword = CryptoUtils.encrypt(originPassword);
        request.setPassword(encryptPassword);
    }
}
