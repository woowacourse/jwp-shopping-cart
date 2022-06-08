package woowacourse.auth.ui;

import javax.validation.Valid;
import javax.validation.constraints.NotBlank;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import woowacourse.auth.application.AuthService;
import woowacourse.auth.support.AuthenticationPrincipal;
import woowacourse.auth.dto.request.LoginRequest;
import woowacourse.auth.dto.request.MemberCreateRequest;
import woowacourse.auth.dto.request.MemberUpdateRequest;
import woowacourse.auth.dto.request.PasswordRequest;
import woowacourse.auth.dto.response.CheckResponse;
import woowacourse.auth.dto.response.LoginResponse;
import woowacourse.auth.dto.response.MemberResponse;

@RestController
@Validated
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

    @GetMapping("/members/email-check")
    public ResponseEntity<CheckResponse> checkDuplicatedEmail(@RequestParam @NotBlank String email) {
        CheckResponse checkResponse = new CheckResponse(!authService.existsEmail(email));
        return ResponseEntity.ok(checkResponse);
    }

    @PostMapping("/login")
    public ResponseEntity<LoginResponse> login(@RequestBody @Valid LoginRequest loginRequest) {
        return ResponseEntity.ok(authService.login(loginRequest));
    }

    @PostMapping("/members/password-check")
    public ResponseEntity<CheckResponse> confirmPassword(@AuthenticationPrincipal long memberId,
                                                         @RequestBody PasswordRequest passwordRequest) {
        boolean actual = authService.checkPassword(memberId, passwordRequest.getPassword());
        return ResponseEntity.ok(new CheckResponse(actual));
    }

    @GetMapping("/members/me")
    public ResponseEntity<MemberResponse> showMember(@AuthenticationPrincipal long memberId) {
        return ResponseEntity.ok(authService.findMember(memberId));
    }

    @PatchMapping("/members/me")
    public ResponseEntity<Void> updateMember(@AuthenticationPrincipal long memberId,
                                             @RequestBody @Valid MemberUpdateRequest memberUpdateRequest) {
        authService.updateMember(memberId, memberUpdateRequest);
        return ResponseEntity.noContent()
                .build();
    }

    @PatchMapping("/members/password")
    public ResponseEntity<Void> updatePassword(@AuthenticationPrincipal long memberId,
                                               @RequestBody @Valid PasswordRequest passwordRequest) {
        authService.updatePassword(memberId, passwordRequest.getPassword());
        return ResponseEntity.noContent()
                .build();
    }

    @DeleteMapping("/members/me")
    public ResponseEntity<Void> deleteMember(@AuthenticationPrincipal long memberId) {
        authService.delete(memberId);
        return ResponseEntity.noContent()
                .build();
    }
}
