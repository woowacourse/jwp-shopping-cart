package woowacourse.auth.ui;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import woowacourse.auth.service.AuthService;
import woowacourse.auth.ui.dto.TokenRequest;
import woowacourse.auth.ui.dto.TokenResponse;

@RestController
@RequestMapping("/api/login")
public class AuthController {
    private final AuthService authService;

    public AuthController(AuthService authService) {
        this.authService = authService;
    }

    @PostMapping
    public ResponseEntity<TokenResponse> getToken(@RequestBody TokenRequest tokenRequest) {
        final TokenResponse tokenResponse = authService.getToken(tokenRequest);
        return ResponseEntity.ok().body(tokenResponse);
    }
}
