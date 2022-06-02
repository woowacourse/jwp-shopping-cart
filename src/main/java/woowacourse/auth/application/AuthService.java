package woowacourse.auth.application;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import woowacourse.auth.dto.TokenRequest;
import woowacourse.auth.dto.TokenResponse;
import woowacourse.auth.support.JwtTokenProvider;
import woowacourse.shoppingcart.dao.CustomerDao;
import woowacourse.shoppingcart.domain.customer.Customer;
import woowacourse.shoppingcart.exception.InvalidCustomerException;

@Transactional
@Service
public class AuthService {
    private final JwtTokenProvider jwtTokenProvider;
    private final CustomerDao customerDao;

    public AuthService(JwtTokenProvider jwtTokenProvider, CustomerDao customerDao) {
        this.jwtTokenProvider = jwtTokenProvider;
        this.customerDao = customerDao;
    }

    public TokenResponse createToken(TokenRequest tokenRequest) {
        checkInvalidLogin(tokenRequest.getUsername(), tokenRequest.getPassword());
        String accessToken = jwtTokenProvider.createToken(tokenRequest.getUsername());
        return new TokenResponse(accessToken);
    }

    private void checkInvalidLogin(String principal, String credentials) {
        Customer customer = customerDao.findCustomerByUserName(principal)
                .orElseThrow(() -> new IllegalArgumentException("아이디를 확인해주세요."));
        customer.validatePassword(credentials);
    }

    public Customer findCustomer(String token) {
        String userName = jwtTokenProvider.getPayload(token);
        return customerDao.findCustomerByUserName(userName)
                .orElseThrow(InvalidCustomerException::new);
    }
}
