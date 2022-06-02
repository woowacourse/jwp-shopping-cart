package woowacourse.auth.application;

import lombok.AllArgsConstructor;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import woowacourse.auth.dto.TokenRequest;
import woowacourse.auth.exception.LoginFailException;
import woowacourse.auth.specification.CustomerSpecification;
import woowacourse.auth.support.JwtTokenProvider;
import woowacourse.auth.utils.CryptoUtils;
import woowacourse.shoppingcart.application.CustomerService;
import woowacourse.shoppingcart.dao.CustomerDao;
import woowacourse.shoppingcart.domain.Customer;
import woowacourse.shoppingcart.dto.customer.CustomerCreateRequest;
import woowacourse.shoppingcart.exception.DuplicateEmailException;
import woowacourse.shoppingcart.exception.DuplicateUsernameException;
import woowacourse.shoppingcart.exception.InvalidCustomerException;

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
        String email = jwtTokenProvider.getPayload(token);
        return customerService.findByEmail(email);
    }

    private void encryptPassword(TokenRequest request) {
        String originPassword = request.getPassword();
        String encryptPassword = CryptoUtils.encrypt(originPassword);
        request.setPassword(encryptPassword);
    }
}
