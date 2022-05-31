package woowacourse.auth.application;

import org.springframework.stereotype.Service;
import woowacourse.auth.dto.LoginRequest;
import woowacourse.auth.exception.InvalidLoginFormException;
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

    public String createToken(LoginRequest loginRequest) {
        Customer customer = customerDao.findByEmail(loginRequest.getEmail())
                .orElseThrow(InvalidCustomerException::new);

        if (!customer.isValidPassword(loginRequest.getPassword())) {
            throw new InvalidLoginFormException();
        }

        return jwtTokenProvider.createToken(loginRequest.getEmail());
    }

}
