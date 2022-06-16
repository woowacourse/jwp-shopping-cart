package woowacourse.auth.application;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import woowacourse.auth.dto.TokenRequest;
import woowacourse.auth.dto.TokenResponse;
import woowacourse.auth.support.JwtTokenProvider;
import woowacourse.shoppingcart.dao.CustomerDao;
import woowacourse.shoppingcart.domain.Customer;
import woowacourse.shoppingcart.dto.request.LoginCustomer;
import woowacourse.shoppingcart.exception.InvalidCustomerException;
import woowacourse.shoppingcart.exception.InvalidCustomerLoginException;
import woowacourse.shoppingcart.exception.InvalidTokenException;
import woowacourse.shoppingcart.util.HashTool;

@Service
@Transactional(readOnly = true)
public class AuthService {

    private final CustomerDao customerDao;
    private final JwtTokenProvider jwtTokenProvider;

    public AuthService(CustomerDao customerDao, JwtTokenProvider jwtTokenProvider) {
        this.customerDao = customerDao;
        this.jwtTokenProvider = jwtTokenProvider;
    }

    @Transactional
    public TokenResponse createToken(TokenRequest tokenRequest) {
        try {
            Customer customer = customerDao.findByLoginId(tokenRequest.getLoginId())
                .orElseThrow(InvalidCustomerException::new);
            checkPassword(customer, tokenRequest.getPassword());

            String token = jwtTokenProvider.createToken(tokenRequest.getLoginId());
            return new TokenResponse(token, customer.getUsername());
        } catch (InvalidCustomerException | IllegalArgumentException e) {
            throw new InvalidCustomerLoginException();
        }
    }

    public LoginCustomer findCustomerByToken(String token) {
        if (!jwtTokenProvider.validateToken(token)) {
            throw new InvalidTokenException();
        }

        String payload = jwtTokenProvider.getPayload(token);
        Customer customer = customerDao.findByLoginId(payload)
            .orElseThrow(InvalidCustomerException::new);

        return new LoginCustomer(customer);
    }

    public void checkPassword(Customer customer, String password) {
        String hashedPassword = HashTool.hashing(password);

        if (!customer.isSamePassword(hashedPassword)) {
            throw new IllegalArgumentException("비밀번호가 일치하지 않습니다.");
        }
    }
}
