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
import woowacourse.shoppingcart.domain.customer.Customer;

@RestController
@RequestMapping("/api/customers")
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
    public ResponseEntity<Void> checkPassword(@AuthenticationPrincipal Customer customer,
                                              @Valid @RequestBody PasswordRequest request) {
        authService.checkPassword(customer, request);
        return ResponseEntity.ok().build();
    }

}
