package woowacourse.auth.application;

import org.springframework.stereotype.Service;
import woowacourse.auth.dto.TokenRequest;
import woowacourse.auth.support.JwtTokenProvider;
import woowacourse.shoppingcart.dao.CustomerDao;
import woowacourse.shoppingcart.domain.Customer;

@Service
public class AuthService {

    private final CustomerDao customerDao;
    private final JwtTokenProvider jwtTokenProvider;

    public AuthService(CustomerDao customerDao, JwtTokenProvider jwtTokenProvider) {
        this.customerDao = customerDao;
        this.jwtTokenProvider = jwtTokenProvider;
    }

    public String login(TokenRequest tokenRequest) {
        Customer customer = customerDao.findIdByEmail(tokenRequest.getEmail())
          .orElseThrow(() -> new IllegalArgumentException("존재하지 않는 회원입니다."));
        checkPassword(tokenRequest, customer);
        return jwtTokenProvider.createToken(tokenRequest.getEmail());
    }

    private void checkPassword(TokenRequest tokenRequest, Customer customer) {
        if (!customer.isPasswordMatched(tokenRequest.getPassword())) {
            throw new IllegalArgumentException("비밀번호가 일치하지 않습니다.");
        }
    }

    public Customer findLoginCustomer(String email) {
        return customerDao.findIdByEmail(email)
          .orElseThrow(() -> new IllegalArgumentException("존재하지 않는 회원입니다."));
    }
}
