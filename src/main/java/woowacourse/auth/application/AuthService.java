package woowacourse.auth.application;

import org.springframework.stereotype.Service;
import woowacourse.auth.dto.TokenRequest;
import woowacourse.auth.exception.InvalidLoginFormException;
import woowacourse.auth.support.Encoder;
import woowacourse.auth.support.JwtTokenProvider;
import woowacourse.shoppingcart.dao.CustomerDao;
import woowacourse.shoppingcart.domain.Customer;
import woowacourse.auth.support.Sha256Encoder;
import woowacourse.shoppingcart.exception.InvalidCustomerException;

@Service
public class AuthService {

    private final CustomerDao customerDao;
    private final JwtTokenProvider jwtTokenProvider;
    private final Encoder encoder;

    public AuthService(CustomerDao customerDao, JwtTokenProvider jwtTokenProvider,
                       Encoder encoder) {
        this.customerDao = customerDao;
        this.jwtTokenProvider = jwtTokenProvider;
        this.encoder = encoder;
    }

    public String createToken(TokenRequest tokenRequest) {
        Customer customer = customerDao.findByEmail(tokenRequest.getEmail())
                .orElseThrow(InvalidCustomerException::new);

        String encryptPassword = encoder.encrypt(tokenRequest.getPassword());
        if (!customer.isValidPassword(encryptPassword)) {
            throw new InvalidLoginFormException();
        }

        return jwtTokenProvider.createToken(tokenRequest.getEmail());
    }

}
