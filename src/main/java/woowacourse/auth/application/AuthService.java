package woowacourse.auth.application;

import org.springframework.stereotype.Service;
import woowacourse.auth.support.JwtTokenProvider;
import woowacourse.shoppingcart.dao.CustomerDao;
import woowacourse.shoppingcart.domain.Customer;
import woowacourse.shoppingcart.dto.AuthorizedCustomer;
import woowacourse.shoppingcart.dto.SignInRequest;
import woowacourse.shoppingcart.dto.SignInResponse;
import woowacourse.shoppingcart.exception.InvalidCustomerException;

@Service
public class AuthService {
    private static final String DIFFERENT_PASSWORD = "[ERROR] 비밀번호가 일치하지 않습니다.";

    private CustomerDao customerDao;
    private JwtTokenProvider jwtTokenProvider;

    public AuthService(JwtTokenProvider jwtTokenProvider, CustomerDao customerDao) {
        this.jwtTokenProvider = jwtTokenProvider;
        this.customerDao = customerDao;
    }

    public SignInResponse signIn(SignInRequest signInRequest) {
        var email = signInRequest.getEmail();
        var authorizedCustomer = customerDao.findCustomerByEmail(email);
        var customer = convertCustomer(authorizedCustomer);

        var username = customer.getUsername();

        validateSamePassword(signInRequest, customer);

        var token = jwtTokenProvider.createToken(username);

        return new SignInResponse(username, email, token);
    }

    private Customer convertCustomer(AuthorizedCustomer authorizedCustomer) {
        return new Customer(
                authorizedCustomer.getUsername(),
                authorizedCustomer.getEmail(),
                authorizedCustomer.getPassword()
        );
    }

    private void validateSamePassword(SignInRequest signInRequest, Customer customer) {
        if (!customer.isSamePassword(signInRequest.getPassword())) {
            throw new InvalidCustomerException(DIFFERENT_PASSWORD);
        }
    }

    public String decode(String token) {
        return jwtTokenProvider.getPayload(token);
    }

    public boolean isValidToken(String token) {
        return jwtTokenProvider.validateToken(token);
    }
}
