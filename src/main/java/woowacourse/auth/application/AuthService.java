package woowacourse.auth.application;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import woowacourse.auth.dto.TokenRequest;
import woowacourse.shoppingcart.exception.badrequest.InvalidLoginFormException;
import woowacourse.auth.support.Encoder;
import woowacourse.auth.support.JwtTokenProvider;
import woowacourse.shoppingcart.dao.CustomerDao;
import woowacourse.shoppingcart.domain.user.Customer;
import woowacourse.shoppingcart.domain.user.Password;
import woowacourse.shoppingcart.exception.badrequest.InvalidUserException;

@Service
@Transactional
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
                .orElseThrow(InvalidUserException::new);

        String encryptPassword = encoder.encrypt(tokenRequest.getPassword());
        if (!customer.isValidPassword(new Password(encryptPassword))) {
            throw new InvalidLoginFormException();
        }

        return jwtTokenProvider.createToken(tokenRequest.getEmail());
    }

}
