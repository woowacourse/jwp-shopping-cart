package woowacourse.auth.ui;

import javax.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import woowacourse.auth.application.AuthService;
import woowacourse.auth.support.AuthenticationPrincipal;
import woowacourse.shoppingcart.dto.AuthorizedCustomer;
import woowacourse.shoppingcart.dto.SignInRequest;
import woowacourse.shoppingcart.dto.SignInResponse;

@RestController
public class AuthController {
    private final AuthService authService;

    public AuthController(AuthService authService) {
        this.authService = authService;
    }

    @PostMapping("/login")
    public ResponseEntity<SignInResponse> signIn(@Valid @RequestBody SignInRequest signInRequest) {
        return ResponseEntity.ok().body(authService.signIn(signInRequest));
    }

    @PostMapping("/token/refresh")
    public ResponseEntity<SignInResponse> refreshToken(@AuthenticationPrincipal AuthorizedCustomer authorizedCustomer) {
        var signInRequest = new SignInRequest(authorizedCustomer.getEmail(), authorizedCustomer.getPassword());

        return ResponseEntity.ok().body(authService.signIn(signInRequest));
    }
}
