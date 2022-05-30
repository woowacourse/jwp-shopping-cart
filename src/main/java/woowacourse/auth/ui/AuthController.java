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
import woowacourse.auth.dto.EmailDuplicationCheckResponse;
import woowacourse.auth.dto.MemberCreateRequest;

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
    public ResponseEntity<EmailDuplicationCheckResponse> checkDuplicatedEmail(@RequestParam String email) {
        EmailDuplicationCheckResponse emailDuplicationCheckResponse =
                new EmailDuplicationCheckResponse(!authService.existsEmail(email));
        return ResponseEntity.ok(emailDuplicationCheckResponse);
    }

}
