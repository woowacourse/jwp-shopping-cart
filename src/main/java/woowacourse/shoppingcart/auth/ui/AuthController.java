package woowacourse.shoppingcart.auth.ui;

import javax.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import woowacourse.shoppingcart.auth.application.AuthService;
import woowacourse.shoppingcart.auth.dto.LoginRequest;
import woowacourse.shoppingcart.auth.dto.LoginResponse;

@RestController
public class AuthController {

    private final AuthService authService;

    public AuthController(final AuthService authService) {
        this.authService = authService;
    }

    @PostMapping("/login")
    public ResponseEntity<LoginResponse> login(@Valid @RequestBody final LoginRequest request) {
        final String accessToken = authService.login(request);
        final LoginResponse response = new LoginResponse(accessToken);
        return ResponseEntity.ok(response);
    }
}
