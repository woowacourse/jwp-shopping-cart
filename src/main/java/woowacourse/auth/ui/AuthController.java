package woowacourse.auth.ui;

import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import woowacourse.auth.application.AuthService;
import woowacourse.auth.ui.dto.SignInRequest;
import woowacourse.auth.application.dto.TokenResponse;

@RestController
public class AuthController {

    private final AuthService authService;

    public AuthController(AuthService authService) {
        this.authService = authService;
    }

    @PostMapping("/signin")
    public TokenResponse signIn(@RequestBody SignInRequest signinRequest) {
        return authService.login(signinRequest);
    }
}