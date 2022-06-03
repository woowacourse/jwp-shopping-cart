package woowacourse.auth.ui;

import javax.validation.Valid;
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
import woowacourse.auth.dto.response.EmailUniqueCheckResponse;
import woowacourse.auth.dto.response.LoginResponse;
import woowacourse.auth.dto.response.PasswordCheckResponse;
import woowacourse.auth.support.AuthenticationPrincipal;

@RestController
@RequestMapping("/api")
public class AuthController {

    private final AuthService authService;

    public AuthController(AuthService authService) {
        this.authService = authService;
    }

    @PostMapping("/members")
    public ResponseEntity<Void> signUp(@RequestBody @Valid MemberCreateRequest memberCreateRequest) {
        authService.save(memberCreateRequest);
        return ResponseEntity.status(HttpStatus.CREATED)
                .build();
    }

    @GetMapping("/members/check-email")
    public ResponseEntity<EmailUniqueCheckResponse> checkUniqueEmail(@RequestParam String email) {
        EmailUniqueCheckResponse emailUniqueCheckResponse = new EmailUniqueCheckResponse(!authService.existsEmail(email));
        return ResponseEntity.ok(emailUniqueCheckResponse);
    }

    @PostMapping("/login")
    public ResponseEntity<LoginResponse> login(@RequestBody @Valid LoginRequest loginRequest) {
        return ResponseEntity.ok(authService.login(loginRequest));
    }

    @PostMapping("/members/password-check")
    public ResponseEntity<PasswordCheckResponse> confirmPassword(@AuthenticationPrincipal String payload,
                                                                 @RequestBody PasswordCheckRequest passwordCheckRequest) {
        return ResponseEntity.ok(authService.checkPassword(payload, passwordCheckRequest));
    }
}
