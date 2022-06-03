package woowacourse.auth.application;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import woowacourse.auth.dto.SignInRequest;
import woowacourse.auth.dto.SignInResponse;
import woowacourse.auth.support.JwtTokenProvider;
import woowacourse.shoppingcart.dao.CustomerDao;
import woowacourse.shoppingcart.domain.Customer;

@Service
@Transactional(readOnly = true)
public class AuthService {

    private final JwtTokenProvider jwtTokenProvider;
    private final CustomerDao customerDao;

    public AuthService(JwtTokenProvider jwtTokenProvider, CustomerDao customerDao) {
        this.jwtTokenProvider = jwtTokenProvider;
        this.customerDao = customerDao;
    }

    public SignInResponse signIn(SignInRequest signInRequest) {
        Customer customer = customerDao.findByEmail(signInRequest.getEmail());
        customer.validatePassword(signInRequest.getPassword());

        return new SignInResponse(
                customer.getUsername(),
                customer.getEmail(),
                jwtTokenProvider.createToken(customer.getUsername())
        );
    }
}
