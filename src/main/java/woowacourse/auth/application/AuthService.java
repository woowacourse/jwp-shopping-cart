package woowacourse.auth.application;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import woowacourse.auth.dto.SignInRequest;
import woowacourse.auth.dto.SignInResponse;
import woowacourse.auth.support.JwtTokenProvider;
import woowacourse.shoppingcart.dao.CustomerDao;
import woowacourse.shoppingcart.domain.Customer;
import woowacourse.shoppingcart.exception.InvalidCustomerException;

@Service
public class AuthService {

    private final JwtTokenProvider jwtTokenProvider;
    private final CustomerDao customerDao;

    public AuthService(JwtTokenProvider jwtTokenProvider, CustomerDao customerDao) {
        this.jwtTokenProvider = jwtTokenProvider;
        this.customerDao = customerDao;
    }

    @Transactional(readOnly = true)
    public SignInResponse signIn(SignInRequest signInRequest) {
        validatePassword(signInRequest.getEmail(), signInRequest.getPassword());

        Customer customer = customerDao.findByEmail(signInRequest.getEmail());

        return new SignInResponse(
                customer.getUsername(),
                customer.getEmail(),
                jwtTokenProvider.createToken(customer.getUsername())
        );
    }

    private void validatePassword(String email, String password) {
        if (!customerDao.isValidPasswordByEmail(email, password)) {
            throw new InvalidCustomerException("로그인 실패");
        }
    }
}
