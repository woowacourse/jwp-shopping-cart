package woowacourse.auth.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import woowacourse.auth.dto.TokenRequest;
import woowacourse.auth.dto.TokenResponse;
import woowacourse.auth.service.AuthService;

@RequestMapping("/api")
@RestController
public class AuthController {
    private final AuthService authService;

    public AuthController(AuthService authService) {
        this.authService = authService;
    }

    @PostMapping("/customer/authentication/sign-in")
    public ResponseEntity<TokenResponse> signIn(@RequestBody TokenRequest tokenRequest) {
        TokenResponse tokenResponse = authService.generateToken(tokenRequest);
        return ResponseEntity.ok(tokenResponse);
    }

    @PostMapping("/customers/{customerId}/authentication/sign-out")
    public ResponseEntity<Void> signOut(@Jwt String accessToken) {
        authService.validateToken(accessToken);
        return ResponseEntity.noContent().build();
    }
}
