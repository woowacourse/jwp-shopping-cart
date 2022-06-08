package woowacourse.auth.application;

import org.springframework.stereotype.Service;
import woowacourse.auth.support.JwtTokenProvider;
import woowacourse.shoppingcart.dao.CustomerDao;
import woowacourse.shoppingcart.domain.*;
import woowacourse.shoppingcart.dto.SignInRequest;
import woowacourse.shoppingcart.dto.SignInResponse;
import woowacourse.shoppingcart.exception.InvalidCustomerException;

@Service
public class AuthService {
    private static final String NOT_EXIST_EMAIL = "[ERROR] 존재하지 않는 이메일 입니다.";

    private CustomerDao customerDao;
    private JwtTokenProvider jwtTokenProvider;

    public AuthService(JwtTokenProvider jwtTokenProvider, CustomerDao customerDao) {
        this.jwtTokenProvider = jwtTokenProvider;
        this.customerDao = customerDao;
    }

    public SignInResponse signIn(SignInRequest signInRequest) {
        String email = signInRequest.getEmail();
        Customer requestedCustomer = new Customer(email, signInRequest.getPassword());
        validateExistEmail(email);
        Customer customer = customerDao.findCustomerByEmail(email);
        customer.validateSamePassword(requestedCustomer);
        String username = customer.getUsername();
        String token = jwtTokenProvider.createToken(username);

        return new SignInResponse(username, email, token);
    }

    private void validateExistEmail(String email) {
        if (!customerDao.isValidEmail(email)) {
            throw new InvalidCustomerException(NOT_EXIST_EMAIL);
        }
    }

    public String decode(String token) {
        return jwtTokenProvider.getPayload(token);
    }

    public boolean isValidToken(String token) {
        return jwtTokenProvider.validateToken(token);
    }

    public SignInResponse autoSignIn(String username) {
        Customer customer = customerDao.findCustomerByUserName(username);
        String email = customer.getEmail();
        String token = jwtTokenProvider.createToken(username);
        return new SignInResponse(username, email, token);
    }
}
