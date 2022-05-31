package woowacourse.auth.application;

import org.springframework.stereotype.Service;
import woowacourse.auth.support.JwtTokenProvider;
import woowacourse.shoppingcart.dao.CustomerDao;
import woowacourse.shoppingcart.dto.SignInRequest;
import woowacourse.shoppingcart.dto.SignInResponse;

@Service
public class AuthService {

    private CustomerDao customerDao;
    private JwtTokenProvider jwtTokenProvider;

    public AuthService(JwtTokenProvider jwtTokenProvider, CustomerDao customerDao) {
        this.jwtTokenProvider = jwtTokenProvider;
        this.customerDao = customerDao;
    }

    public SignInResponse signIn(SignInRequest signInRequest) {
        String email = signInRequest.getEmail();

        var customer = customerDao.findCustomerByEmail(email);
        String username = customer.getUsername();

        if (!customer.isSamePassword(signInRequest.getPassword())) {
            throw new IllegalArgumentException("[ERROR] 비밀번호가 일치하지 않습니다.");
        }

        String token = jwtTokenProvider.createToken(username);

        return new SignInResponse(username, email, token);
    }
}
