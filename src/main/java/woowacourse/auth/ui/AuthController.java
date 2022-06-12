package woowacourse.auth.ui;

import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import woowacourse.auth.application.AuthService;
import woowacourse.auth.dto.TokenRequest;
import woowacourse.auth.dto.TokenResponse;

@Controller
public class AuthController {

    private final AuthService authService;

    public AuthController(final AuthService authService) {
        this.authService = authService;
    }

    @PostMapping("/api/login")
    public ResponseEntity<TokenResponse> login(@RequestBody final TokenRequest tokenRequest) {
        final String nickname = authService.getNickname(tokenRequest);
        final String accessToken = authService.createToken(tokenRequest);
        final TokenResponse tokenResponse = new TokenResponse(nickname, accessToken);

        return ResponseEntity.ok(tokenResponse);
    }
}
