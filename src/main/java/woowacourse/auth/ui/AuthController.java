package woowacourse.auth.ui;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import woowacourse.auth.application.AuthService;
import woowacourse.auth.dto.LogInRequest;
import woowacourse.auth.dto.LogInResponse;

@RestController
public class AuthController {

    private final AuthService authService;

    public AuthController(AuthService authService) {
        this.authService = authService;
    }

    @PostMapping("/login")
    public ResponseEntity<LogInResponse> signIn(@RequestBody LogInRequest logInRequest) {
        LogInResponse logInResponse = authService.signIn(logInRequest);
        return ResponseEntity.ok(logInResponse);
    }
}
