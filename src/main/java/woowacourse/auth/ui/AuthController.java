package woowacourse.auth.ui;

import javax.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import woowacourse.auth.application.AuthService;
import woowacourse.auth.application.dto.TokenResponse;
import woowacourse.auth.ui.dto.TokenRequest;

@RestController
@RequestMapping("/api/login")
public class AuthController {

    private final AuthService authService;

    public AuthController(AuthService authService) {
        this.authService = authService;
    }

    @PostMapping
    public ResponseEntity<TokenResponse> login(@RequestBody @Valid TokenRequest tokenRequest) {
        TokenResponse accessToken = authService.createToken(tokenRequest.toServiceRequest());
        return ResponseEntity.ok(accessToken);
    }
}
