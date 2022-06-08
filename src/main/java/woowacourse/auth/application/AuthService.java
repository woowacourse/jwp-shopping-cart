package woowacourse.auth.application;

import org.springframework.stereotype.Service;
import woowacourse.auth.dto.TokenRequest;
import woowacourse.auth.exception.InvalidLoginFormException;
import woowacourse.auth.support.JwtTokenProvider;
import woowacourse.auth.support.PasswordEncoder;
import woowacourse.shoppingcart.dao.CustomerDao;
import woowacourse.shoppingcart.domain.customer.Customer;
import woowacourse.shoppingcart.exception.customer.InvalidCustomerBadRequestException;

@Service
public class AuthService {

    private final CustomerDao customerDao;
    private final JwtTokenProvider jwtTokenProvider;

    public AuthService(CustomerDao customerDao, JwtTokenProvider jwtTokenProvider) {
        this.customerDao = customerDao;
        this.jwtTokenProvider = jwtTokenProvider;
    }

    public String createToken(TokenRequest tokenRequest) {
        Customer customer = customerDao.findByEmail(tokenRequest.getEmail())
                .orElseThrow(InvalidCustomerBadRequestException::new);

        String encryptPassword = PasswordEncoder.encrypt(tokenRequest.getPassword());
        if (!customer.isValidPassword(encryptPassword)) {
            throw new InvalidLoginFormException();
        }

        return jwtTokenProvider.createToken(customer.getId());
    }
}
