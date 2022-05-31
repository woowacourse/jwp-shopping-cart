package woowacourse.auth.application;

import org.springframework.stereotype.Service;
import woowacourse.auth.dto.TokenRequest;
import woowacourse.auth.dto.TokenResponse;
import woowacourse.auth.support.JwtTokenProvider;
import woowacourse.shoppingcart.dao.CustomerDao;
import woowacourse.shoppingcart.domain.Customer;
import woowacourse.shoppingcart.exception.InvalidCustomerException;

@Service
public class AuthService {

    private final CustomerDao customerDao;
    private final JwtTokenProvider tokenProvider;

    public AuthService(CustomerDao customerDao, JwtTokenProvider tokenProvider) {
        this.customerDao = customerDao;
        this.tokenProvider = tokenProvider;
    }

    public TokenResponse login(TokenRequest tokenRequest) {
        Customer customer = customerDao.findByLoginId(tokenRequest.getEmail());
        if (!customer.isSamePassword(tokenRequest.getPassword())) {
            throw new InvalidCustomerException();
        }
        String token = tokenProvider.createToken(tokenRequest.getEmail());
        return new TokenResponse(token);
    }
}
