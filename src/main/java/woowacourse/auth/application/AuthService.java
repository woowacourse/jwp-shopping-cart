package woowacourse.auth.application;

import org.springframework.stereotype.Service;

import woowacourse.auth.domain.BcryptPasswordMatcher;
import woowacourse.auth.dto.TokenRequest;
import woowacourse.auth.dto.TokenResponse;
import woowacourse.auth.exception.AuthenticationFailureException;
import woowacourse.auth.support.JwtTokenProvider;
import woowacourse.shoppingcart.dao.CustomerDao;
import woowacourse.shoppingcart.domain.customer.Customer;
import woowacourse.shoppingcart.domain.customer.UserName;

@Service
public class AuthService {

    private final CustomerDao customerDao;
    private final JwtTokenProvider tokenProvider;

    public AuthService(CustomerDao customerDao, JwtTokenProvider tokenProvider) {
        this.customerDao = customerDao;
        this.tokenProvider = tokenProvider;
    }

    public TokenResponse createToken(TokenRequest tokenRequest) {
        Customer customer = customerDao.findByName(new UserName(tokenRequest.getName()))
            .orElseThrow(AuthenticationFailureException::new);
        if (!customer.isPasswordMatch(tokenRequest.getPassword(), new BcryptPasswordMatcher())) {
            throw new AuthenticationFailureException();
        }
        return new TokenResponse(tokenProvider.createToken(customer.getId().toString()));
    }
}
