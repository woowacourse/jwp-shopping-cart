package woowacourse.shoppingcart.ui;

import javax.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import woowacourse.shoppingcart.application.AuthService;
import woowacourse.shoppingcart.dto.LoginRequest;
import woowacourse.shoppingcart.dto.LoginResponse;

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
