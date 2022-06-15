package woowacourse.shoppingcart.auth.ui;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import woowacourse.shoppingcart.auth.application.AuthService;
import woowacourse.shoppingcart.auth.application.dto.request.TokenRequest;
import woowacourse.shoppingcart.auth.application.dto.response.TokenResponse;
import woowacourse.shoppingcart.auth.support.jwt.AuthenticationPrincipal;

@RestController
@RequestMapping("/auth")
public class AuthController {

    private final AuthService authService;

    public AuthController(final AuthService authService) {
        this.authService = authService;
    }

    @PostMapping("/login")
    public ResponseEntity<TokenResponse> login(@RequestBody final TokenRequest tokenRequest) {
        final TokenResponse tokenResponse = authService.login(tokenRequest);
        return ResponseEntity.ok().body(tokenResponse);
    }

    @PostMapping("/logout")
    public ResponseEntity<Void> logout(@AuthenticationPrincipal final Long id) {
        return ResponseEntity.noContent().build();
    }
}


