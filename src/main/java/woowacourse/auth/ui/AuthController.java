package woowacourse.auth.ui;

import javax.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import woowacourse.auth.application.AuthService;
import woowacourse.auth.dto.PasswordRequest;
import woowacourse.auth.dto.TokenRequest;
import woowacourse.auth.dto.TokenResponse;
import woowacourse.auth.support.AuthenticationPrincipal;

@RestController
@RequestMapping("/api/customer")
public class AuthController {

    private final AuthService authService;

    public AuthController(final AuthService authService) {
        this.authService = authService;
    }

    @PostMapping("/login")
    public ResponseEntity<TokenResponse> login(@Valid @RequestBody TokenRequest request) {
        return ResponseEntity.ok(authService.login(request));
    }

    @PostMapping("/password")
    public ResponseEntity<Void> checkPassword(@AuthenticationPrincipal Long customerId,
                                              @Valid @RequestBody PasswordRequest request) {
        authService.checkPassword(customerId, request);
        return ResponseEntity.ok().build();
    }

}
