package woowacourse.auth.application;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import woowacourse.auth.dto.LoginRequest;
import woowacourse.auth.dto.TokenResponse;
import woowacourse.auth.support.JwtTokenProvider;
import woowacourse.customer.application.CustomerService;

@Transactional
@Service
public class AuthService {

    private final JwtTokenProvider jwtTokenProvider;
    private final CustomerService customerService;

    public AuthService(
        final JwtTokenProvider jwtTokenProvider,
        final CustomerService customerService
    ) {
        this.jwtTokenProvider = jwtTokenProvider;
        this.customerService = customerService;
    }

    public TokenResponse createToken(final LoginRequest loginRequest) {
        validateLogin(loginRequest);
        final String accessToken = jwtTokenProvider.createToken(loginRequest.getUsername());
        return new TokenResponse(accessToken);
    }

    private void validateLogin(final LoginRequest loginRequest) {
        customerService.confirmPassword(loginRequest.getUsername(), loginRequest.getPassword());
    }
}
