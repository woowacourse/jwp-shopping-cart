package woowacourse.auth.ui;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import woowacourse.auth.application.AuthService;
import woowacourse.auth.dto.LogInRequest;
import woowacourse.auth.dto.LogInResponse;
import woowacourse.auth.support.AuthenticationPrincipal;

import javax.validation.Valid;

@RestController
public class AuthController {

    private final AuthService authService;

    public AuthController(AuthService authService) {
        this.authService = authService;
    }

    @PostMapping("/login")
    public ResponseEntity<LogInResponse> signIn(@Valid @RequestBody LogInRequest logInRequest) {
        LogInResponse logInResponse = authService.signIn(logInRequest);
        return ResponseEntity.ok(logInResponse);
    }

    @PostMapping("/token/refresh")
    public ResponseEntity<LogInResponse> autoSignIn(@AuthenticationPrincipal String userNameByToken) {
        LogInResponse logInResponse = authService.autoSignIn(userNameByToken);
        return ResponseEntity.ok(logInResponse);
    }
}
