package woowacourse.auth.ui;

import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import woowacourse.auth.application.AuthService;
import woowacourse.auth.dto.TokenRequest;
import woowacourse.auth.dto.TokenResponse;

@RestController
public class AuthController {

    private final AuthService authService;

    public AuthController(final AuthService authService) {
        this.authService = authService;
    }

    @PostMapping("/api/login")
    public TokenResponse login(@RequestBody final TokenRequest tokenRequest) {
        final String token = authService.login(tokenRequest.toServiceDto());
        return new TokenResponse(token);
    }
}
