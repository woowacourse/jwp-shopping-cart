package woowacourse.auth.application;

import org.springframework.stereotype.Service;
import woowacourse.auth.application.dto.LoginServiceRequest;
import woowacourse.auth.support.JwtTokenProvider;
import woowacourse.shoppingcart.application.CustomerService;

@Service
public class AuthService {

    private final CustomerService customerService;
    private final JwtTokenProvider jwtTokenProvider;

    public AuthService(final CustomerService customerService, final JwtTokenProvider jwtTokenProvider) {
        this.customerService = customerService;
        this.jwtTokenProvider = jwtTokenProvider;
    }

    public String certify(final LoginServiceRequest loginServiceRequest) {
        final long id = customerService
                .validateCustomer(loginServiceRequest.getEmail(), loginServiceRequest.getPassword());
        return jwtTokenProvider.createToken(String.valueOf(id));
    }

    public Long parseToId(final String token) {
        return Long.parseLong(jwtTokenProvider.getPayload(token));
    }

    public boolean isValidToken(final String token) {
        return jwtTokenProvider.validateToken(token);
    }
}
