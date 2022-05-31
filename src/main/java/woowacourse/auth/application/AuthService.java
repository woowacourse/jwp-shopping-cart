package woowacourse.auth.application;

import org.springframework.stereotype.Service;
import woowacourse.auth.domain.Customer;
import woowacourse.auth.dto.SignInRequest;
import woowacourse.auth.dto.SignInResponse;
import woowacourse.auth.support.JwtTokenProvider;
import woowacourse.shoppingcart.dao.CustomerDao;
import woowacourse.shoppingcart.exception.AuthorizationException;
import woowacourse.shoppingcart.exception.InvalidCustomerException;

@Service
public class AuthService {

    private JwtTokenProvider jwtTokenProvider;
    private CustomerDao customerDao;

    public AuthService(JwtTokenProvider jwtTokenProvider, CustomerDao customerDao) {
        this.jwtTokenProvider = jwtTokenProvider;
        this.customerDao = customerDao;
    }

    public SignInResponse signIn(SignInRequest signInRequest) {
        if (!customerDao.isValidPasswordByEmail(signInRequest.getEmail(), signInRequest.getPassword())) {
            throw new InvalidCustomerException("로그인 실패");
        }

        Customer customer = customerDao.findByEmail(signInRequest.getEmail());

        return new SignInResponse(
                customer.getUsername(),
                customer.getEmail(),
                jwtTokenProvider.createToken(customer.getUsername())
        );
    }
}
