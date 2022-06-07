package woowacourse.shoppingcart.auth.ui;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import woowacourse.shoppingcart.auth.application.AuthService;
import woowacourse.shoppingcart.auth.dto.TokenRequest;
import woowacourse.shoppingcart.auth.dto.TokenResponse;
import woowacourse.shoppingcart.auth.support.AuthenticationPrincipal;

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
        // 로그아웃 기능 - 협의 중
        return ResponseEntity.noContent().build();
    }
}


