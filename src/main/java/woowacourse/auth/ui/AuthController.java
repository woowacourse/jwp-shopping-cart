package woowacourse.auth.ui;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import woowacourse.auth.application.AuthService;
import woowacourse.auth.application.dto.TokenResponse;
import woowacourse.auth.ui.dto.LoginRequest;

import javax.validation.Valid;

@RestController
@RequestMapping("/api/login")
public class AuthController {

    private final AuthService authService;

    public AuthController(final AuthService authService) {
        this.authService = authService;
    }

    @PostMapping
    public ResponseEntity<TokenResponse> login(@RequestBody @Valid final LoginRequest loginRequest) {
        TokenResponse accessToken = authService.createToken(loginRequest.toServiceRequest());
        return ResponseEntity.ok(accessToken);
    }
}
