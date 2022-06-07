package woowacourse.auth.application;

import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.stereotype.Service;
import woowacourse.auth.dto.TokenResponse;
import woowacourse.auth.exception.BadRequestException;
import woowacourse.auth.exception.NotFoundException;
import woowacourse.auth.support.AuthorizationExtractor;
import woowacourse.auth.support.JwtTokenProvider;
import woowacourse.shoppingcart.application.dto.SignInDto;
import woowacourse.shoppingcart.dao.CustomerDao;
import woowacourse.shoppingcart.domain.customer.Email;
import woowacourse.shoppingcart.domain.customer.Password;
import woowacourse.shoppingcart.dto.CustomerDto;

@Service
public class AuthService {

    private final JwtTokenProvider jwtTokenProvider;
    private final CustomerDao customerDao;

    public AuthService(JwtTokenProvider jwtTokenProvider, CustomerDao customerDao) {
        this.jwtTokenProvider = jwtTokenProvider;
        this.customerDao = customerDao;
    }

    public boolean isValid(String header) {
        String token = AuthorizationExtractor.extract(header);
        jwtTokenProvider.validateToken(token);
        return true;
    }

    public TokenResponse signIn(final SignInDto signInDto) {
        final Email email = new Email(signInDto.getEmail());
        final CustomerDto customer = checkSignUpCustomer(email);
        verifyPassword(signInDto.getPassword(), customer.getPassword());
        return new TokenResponse(customer.getId(), jwtTokenProvider.createToken(String.valueOf(customer.getId())));
    }

    private void verifyPassword(final String password, final String hashedPassword) {
        final Password targetPassword = new Password(password);
        if (!targetPassword.isSamePassword(hashedPassword)) {
            throw new BadRequestException("올바르지 않은 비밀번호입니다.");
        }
    }

    private CustomerDto checkSignUpCustomer(Email email) {
        try {
            return customerDao.findCustomerByEmail(email);
        } catch (EmptyResultDataAccessException e) {
            throw new NotFoundException("가입하지 않은 유저입니다.");
        }
    }
}
