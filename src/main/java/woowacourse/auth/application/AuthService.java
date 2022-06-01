package woowacourse.auth.application;

import org.springframework.stereotype.Service;
import woowacourse.auth.dto.SignInDto;
import woowacourse.auth.dto.TokenResponseDto;
import woowacourse.auth.support.JwtTokenProvider;
import woowacourse.auth.support.PasswordEncoder;
import woowacourse.shoppingcart.dao.CustomerDao;
import woowacourse.shoppingcart.domain.Customer;
import woowacourse.shoppingcart.dto.CustomerDto;
import woowacourse.shoppingcart.exception.AuthorizationFailException;

@Service
public class AuthService {

    private final JwtTokenProvider jwtTokenProvider;
    private final CustomerDao customerDao;
    private final PasswordEncoder passwordEncoder;

    public AuthService(final JwtTokenProvider jwtTokenProvider, final CustomerDao customerDao,
                       final PasswordEncoder passwordEncoder) {
        this.jwtTokenProvider = jwtTokenProvider;
        this.customerDao = customerDao;
        this.passwordEncoder = passwordEncoder;
    }


    public String extractEmail(final String token) {
        return jwtTokenProvider.extractClaim(token);
    }

    public TokenResponseDto login(final SignInDto signInDto) {
        final Customer customer = customerDao.findByEmail(signInDto.getEmail())
                .orElseThrow(() -> new AuthorizationFailException("로그인에 실패했습니다."));

        checkPassword(signInDto, customer);

        final CustomerDto customerDto = new CustomerDto(customer.getId(), customer.getEmail(), customer.getUsername());
        final String accessToken = makeAccessToken(customerDto);
        return new TokenResponseDto(accessToken, jwtTokenProvider.getValidityInMilliseconds(), customerDto);
    }

    private String makeAccessToken(final CustomerDto customer) {
        return jwtTokenProvider.createToken(customer.getEmail());
    }

    private void checkPassword(final SignInDto signInDto, final Customer customer) {
        if (!passwordEncoder.matches(signInDto.getPassword(), customer.getPassword())) {
            throw new AuthorizationFailException("로그인에 실패했습니다.");
        }
    }
}
