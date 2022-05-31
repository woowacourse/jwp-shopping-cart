package woowacourse.auth.ui;

import javax.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import woowacourse.auth.application.AuthService;
import woowacourse.auth.dto.request.LoginRequest;
import woowacourse.auth.dto.request.MemberCreateRequest;
import woowacourse.auth.dto.request.MemberUpdateRequest;
import woowacourse.auth.dto.request.PasswordCheckRequest;
import woowacourse.auth.dto.request.PasswordUpdateRequest;
import woowacourse.auth.dto.response.CheckResponse;
import woowacourse.auth.dto.response.LoginResponse;
import woowacourse.auth.dto.response.MemberResponse;
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

    @GetMapping("/members")
    public ResponseEntity<CheckResponse> checkDuplicatedEmail(@RequestParam String email) {
        CheckResponse checkResponse =
                new CheckResponse(!authService.existsEmail(email));
        return ResponseEntity.ok(checkResponse);
    }

    @PostMapping("/login")
    public ResponseEntity<LoginResponse> login(@RequestBody @Valid LoginRequest loginRequest) {
        return ResponseEntity.ok(authService.login(loginRequest));
    }

    @PostMapping("/members/auth/password-check")
    public ResponseEntity<CheckResponse> confirmPassword(@AuthenticationPrincipal String payload,
                                                         @RequestBody PasswordCheckRequest passwordCheckRequest) {
        return ResponseEntity.ok(authService.checkPassword(payload, passwordCheckRequest));
    }

    @GetMapping("/members/auth/me")
    public ResponseEntity<MemberResponse> showMember(@AuthenticationPrincipal String payload) {
        return ResponseEntity.ok(authService.findMember(payload));
    }

    @PatchMapping("/members/auth/me")
    public ResponseEntity<Void> updateMember(@AuthenticationPrincipal String payload,
                                             @RequestBody @Valid MemberUpdateRequest memberUpdateRequest) {
        authService.updateMember(payload, memberUpdateRequest);
        return ResponseEntity.noContent()
                .build();
    }

    @PatchMapping("/members/auth/password")
    public ResponseEntity<Void> updatePassword(@AuthenticationPrincipal String payload,
                                               @RequestBody @Valid PasswordUpdateRequest passwordUpdateRequest) {
        authService.updatePassword(payload, passwordUpdateRequest);
        return ResponseEntity.noContent()
                .build();
    }

    @DeleteMapping("/members/auth/me")
    public ResponseEntity<Void> deleteMember(@AuthenticationPrincipal String payload) {
        authService.delete(payload);
        return ResponseEntity.noContent()
                .build();
    }
}
