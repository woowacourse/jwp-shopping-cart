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
import woowacourse.auth.application.dto.response.MemberServiceResponse;
import woowacourse.auth.support.AuthenticationPrincipal;
import woowacourse.auth.ui.dto.request.LoginRequest;
import woowacourse.auth.ui.dto.request.MemberCreateRequest;
import woowacourse.auth.ui.dto.request.MemberUpdateRequest;
import woowacourse.auth.ui.dto.request.PasswordCheckRequest;
import woowacourse.auth.ui.dto.request.PasswordUpdateRequest;
import woowacourse.auth.ui.dto.response.CheckResponse;
import woowacourse.auth.ui.dto.response.LoginResponse;
import woowacourse.auth.ui.dto.response.MemberResponse;

@RestController
@RequestMapping("/api")
public class AuthController {

    private final AuthService authService;

    public AuthController(AuthService authService) {
        this.authService = authService;
    }

    @PostMapping("/members")
    public ResponseEntity<Void> signUp(@RequestBody @Valid MemberCreateRequest memberCreateRequest) {
        authService.save(memberCreateRequest.toServiceDto());
        return ResponseEntity.status(HttpStatus.CREATED)
                .build();
    }

    @GetMapping("/members/check-email")
    public ResponseEntity<CheckResponse> checkDuplicatedEmail(@RequestParam String email) {
        CheckResponse checkResponse =
                new CheckResponse(!authService.existsEmail(email));
        return ResponseEntity.ok(checkResponse);
    }

    @PostMapping("/login")
    public ResponseEntity<LoginResponse> login(@RequestBody @Valid LoginRequest loginRequest) {
        LoginResponse loginResponse = new LoginResponse(authService.login(loginRequest.toServiceDto()));
        return ResponseEntity.ok(loginResponse);
    }

    @PostMapping("/members/password-check")
    public ResponseEntity<CheckResponse> confirmPassword(@AuthenticationPrincipal String payload,
                                                         @RequestBody PasswordCheckRequest passwordCheckRequest) {
        boolean actual = authService.checkPassword(payload, passwordCheckRequest.getPassword());
        return ResponseEntity.ok(new CheckResponse(actual));
    }

    @GetMapping("/members/me")
    public ResponseEntity<MemberResponse> showMember(@AuthenticationPrincipal String payload) {
        MemberServiceResponse memberServiceResponse = authService.findMember(payload);
        return ResponseEntity.ok(new MemberResponse(memberServiceResponse));
    }

    @PatchMapping("/members/me")
    public ResponseEntity<Void> updateMember(@AuthenticationPrincipal String payload,
                                             @RequestBody @Valid MemberUpdateRequest memberUpdateRequest) {
        authService.updateMember(payload, memberUpdateRequest.toServiceDto());
        return ResponseEntity.noContent()
                .build();
    }

    @PatchMapping("/members/password")
    public ResponseEntity<Void> updatePassword(@AuthenticationPrincipal String payload,
                                               @RequestBody @Valid PasswordUpdateRequest passwordUpdateRequest) {
        authService.updatePassword(payload, passwordUpdateRequest.getPassword());
        return ResponseEntity.noContent()
                .build();
    }

    @DeleteMapping("/members/me")
    public ResponseEntity<Void> deleteMember(@AuthenticationPrincipal String payload) {
        authService.delete(payload);
        return ResponseEntity.noContent()
                .build();
    }
}
