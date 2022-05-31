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

    public AuthController(AuthService authService) {
        this.authService = authService;
    }

    @PostMapping("/api/login")
    public ResponseEntity<TokenResponse> login(@RequestBody TokenRequest tokenRequest) {
        String nickname = authService.getNickname(tokenRequest);
        String accessToken = authService.createToken(tokenRequest);
        TokenResponse tokenResponse = new TokenResponse(nickname, accessToken);

        return ResponseEntity.ok().body(tokenResponse);
    }
}
