package woowacourse.auth.application;

import org.springframework.stereotype.Service;
import woowacourse.auth.dto.TokenRequest;
import woowacourse.auth.dto.TokenResponse;
import woowacourse.auth.support.JwtTokenProvider;
import woowacourse.shoppingcart.dao.CustomerDao;
import woowacourse.shoppingcart.domain.customer.Customer;
import woowacourse.shoppingcart.exception.InvalidCustomerException;

@Service
public class AuthService {

    private final CustomerDao customerDao;
    private final JwtTokenProvider jwtTokenProvider;

    public AuthService(CustomerDao customerDao, JwtTokenProvider jwtTokenProvider) {
        this.customerDao = customerDao;
        this.jwtTokenProvider = jwtTokenProvider;
    }

    public TokenResponse login(TokenRequest tokenRequest) {
        Customer customer = customerDao.findByEmail(tokenRequest.getEmail())
                .orElseThrow(() -> new InvalidCustomerException("존재하지 않는 이메일 입니다."));
        if (!customer.checkPassword(tokenRequest.getPassword())) {
            throw new InvalidCustomerException("비밀번호가 일치하지 않습니다.");
        }
        final String accessToken = jwtTokenProvider.createToken(String.valueOf(customer.getId()));
        return new TokenResponse(accessToken);
    }
}
