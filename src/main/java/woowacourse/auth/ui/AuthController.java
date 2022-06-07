package woowacourse.auth.ui;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import woowacourse.auth.application.AuthService;
import woowacourse.auth.dto.TokenRequest;
import woowacourse.auth.dto.TokenResponse;
import woowacourse.shoppingcart.application.dto.SignInDto;

@RestController
@RequestMapping("/api")
public class AuthController {

    private AuthService authService;

    public AuthController(AuthService authService) {
        this.authService = authService;
    }

    @PostMapping("/customer/authentication/sign-in")
    public ResponseEntity<TokenResponse> signIn(@RequestBody final TokenRequest tokenRequest) {
        final TokenResponse tokenResponse = authService.signIn(SignInDto.fromTokenRequest(tokenRequest));
        return ResponseEntity.ok().body(tokenResponse);
    }
}
