package woowacourse.auth.application;

import org.springframework.stereotype.Service;
import woowacourse.auth.dto.SignInDto;
import woowacourse.auth.dto.TokenResponseDto;
import woowacourse.auth.support.JwtTokenProvider;
import woowacourse.shoppingcart.dao.CustomerDao;
import woowacourse.shoppingcart.domain.Customer;
import woowacourse.shoppingcart.dto.CustomerDto;
import woowacourse.shoppingcart.exception.AuthorizationFailException;

@Service
public class AuthService {

    private final JwtTokenProvider jwtTokenProvider;
    private final CustomerDao customerDao;

    public AuthService(final JwtTokenProvider jwtTokenProvider, final CustomerDao customerDao) {
        this.jwtTokenProvider = jwtTokenProvider;
        this.customerDao = customerDao;
    }


    public String extractPayload(final String token) {
        return jwtTokenProvider.extractEmail(token);
    }

    public TokenResponseDto login(final SignInDto signInDto) {
        final Customer customer = customerDao.findByEmail(signInDto.getEmail())
                .orElseThrow(() -> new AuthorizationFailException("로그인에 실패했습니다."));

        checkPassword(signInDto, customer);

        final String accessToken = makeAccessToken(new CustomerDto(customer.getId(), customer.getEmail(), customer.getUsername()));
        return new TokenResponseDto(accessToken, jwtTokenProvider.getValidityInMilliseconds());
    }

    private String makeAccessToken(final CustomerDto customer) {
        return jwtTokenProvider.createToken(customer.getEmail());
    }

    private void checkPassword(final SignInDto signInDto, final Customer customer) {
        if (!customer.getPassword().equals(signInDto.getPassword())) {
            throw new AuthorizationFailException("로그인에 실패했습니다.");
        }
    }
}
