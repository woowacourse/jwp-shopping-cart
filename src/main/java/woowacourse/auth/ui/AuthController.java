package woowacourse.auth.ui;

import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import woowacourse.auth.application.AuthService;
import woowacourse.auth.dto.TokenRequest;
import woowacourse.auth.dto.TokenResponse;

@RestController
@RequestMapping("/api/auth")
@RequiredArgsConstructor
public class AuthController {

    private final AuthService authService;

    @Value("${security.jwt.token.expire-length}")
    private long validityInMilliseconds;

    @PostMapping("/login")
    public TokenResponse login(@RequestBody TokenRequest request) {
        String token = authService.createToken(request);

        return new TokenResponse(token, validityInMilliseconds);
    }
}
