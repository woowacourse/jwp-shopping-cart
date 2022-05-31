package woowacourse.auth.ui;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import woowacourse.auth.application.AuthService;
import woowacourse.auth.dto.request.LoginRequest;
import woowacourse.auth.dto.request.MemberCreateRequest;
import woowacourse.auth.dto.request.PasswordCheckRequest;
import woowacourse.auth.dto.response.CheckResponse;
import woowacourse.auth.dto.response.LoginResponse;
import woowacourse.auth.support.AuthenticationPrincipal;

@RestController
@RequestMapping("/api")
public class AuthController {

    private final AuthService authService;

    public AuthController(AuthService authService) {
        this.authService = authService;
    }

    @PostMapping("/members")
    public ResponseEntity<Void> signUp(@RequestBody MemberCreateRequest memberCreateRequest) {
        authService.save(memberCreateRequest);
        return ResponseEntity.status(HttpStatus.CREATED)
                .build();
    }

    @GetMapping("/members")
    public ResponseEntity<CheckResponse> checkDuplicatedEmail(@RequestParam String email) {
        CheckResponse checkResponse =
                new CheckResponse(!authService.existsEmail(email));
        return ResponseEntity.ok(checkResponse);
    }

    @PostMapping("/login")
    public ResponseEntity<LoginResponse> login(@RequestBody LoginRequest loginRequest) {
        return ResponseEntity.ok(authService.login(loginRequest));
    }

    @PostMapping("/members/auth/password-check")
    public ResponseEntity<CheckResponse> confirmPassword(@AuthenticationPrincipal String payload,
                                                         @RequestBody PasswordCheckRequest passwordCheckRequest) {
        return ResponseEntity.ok(authService.checkPassword(payload, passwordCheckRequest));
    }
}
