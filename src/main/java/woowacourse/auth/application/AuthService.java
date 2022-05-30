package woowacourse.auth.application;

import org.springframework.stereotype.Service;
import woowacourse.auth.dto.TokenResponse;
import woowacourse.auth.support.JwtTokenProvider;
import woowacourse.shoppingcart.dao.CustomerDao;
import woowacourse.shoppingcart.domain.Customer;
import woowacourse.shoppingcart.exception.InvalidCustomerException;

@Service
public class AuthService {
    private final CustomerDao customerDao;
    private final JwtTokenProvider jwtTokenProvider;

    public AuthService(CustomerDao customerDao, JwtTokenProvider jwtTokenProvider) {
        this.customerDao = customerDao;
        this.jwtTokenProvider = jwtTokenProvider;
    }

    public TokenResponse login(String email, String password) {
        final Customer customer = customerDao.findByEmail(email);
        if (customer.isDifferentPassword(password)) {
            throw new InvalidCustomerException("패스워드가 일치하지 않습니다.");
        }

        final String token = jwtTokenProvider.createToken(customer.getEmail());
        return new TokenResponse(token);
    }
}
