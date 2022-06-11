package woowacourse.member.ui;

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
import woowacourse.member.application.MemberService;
import woowacourse.member.dto.request.LoginRequest;
import woowacourse.member.dto.request.MemberCreateRequest;
import woowacourse.member.dto.request.MemberUpdateRequest;
import woowacourse.member.dto.request.PasswordRequest;
import woowacourse.member.dto.response.UniqueEmailCheckResponse;
import woowacourse.member.dto.response.LoginResponse;
import woowacourse.member.dto.response.MemberResponse;
import woowacourse.member.dto.response.PasswordCheckResponse;
import woowacourse.member.support.AuthenticationPrincipal;

@RestController
@Validated
@RequestMapping("/api")
public class MemberController {

    private final MemberService memberService;

    public MemberController(MemberService memberService) {
        this.memberService = memberService;
    }

    @PostMapping("/members")
    public ResponseEntity<Void> signUp(@RequestBody @Valid MemberCreateRequest memberCreateRequest) {
        memberService.save(memberCreateRequest);
        return ResponseEntity.status(HttpStatus.CREATED)
                .build();
    }

    @GetMapping("/members/email-check")
    public ResponseEntity<UniqueEmailCheckResponse> checkDuplicatedEmail(@RequestParam @NotBlank String email) {
        return ResponseEntity.ok(memberService.checkUniqueEmail(email));
    }

    @PostMapping("/login")
    public ResponseEntity<LoginResponse> login(@RequestBody @Valid LoginRequest loginRequest) {
        return ResponseEntity.ok(memberService.login(loginRequest));
    }

    @PostMapping("/members/password-check")
    public ResponseEntity<PasswordCheckResponse> confirmPassword(@AuthenticationPrincipal long memberId,
                                                                 @RequestBody PasswordRequest passwordRequest) {
        return ResponseEntity.ok(memberService.checkPassword(memberId, passwordRequest));
    }

    @GetMapping("/members/me")
    public ResponseEntity<MemberResponse> showMember(@AuthenticationPrincipal long memberId) {
        return ResponseEntity.ok(memberService.findMember(memberId));
    }

    @PatchMapping("/members/me")
    public ResponseEntity<Void> updateMember(@AuthenticationPrincipal long memberId,
                                             @RequestBody @Valid MemberUpdateRequest memberUpdateRequest) {
        memberService.updateMember(memberId, memberUpdateRequest);
        return ResponseEntity.noContent()
                .build();
    }

    @PatchMapping("/members/password")
    public ResponseEntity<Void> updatePassword(@AuthenticationPrincipal long memberId,
                                               @RequestBody @Valid PasswordRequest passwordRequest) {
        memberService.updatePassword(memberId, passwordRequest);
        return ResponseEntity.noContent()
                .build();
    }

    @DeleteMapping("/members/me")
    public ResponseEntity<Void> deleteMember(@AuthenticationPrincipal long memberId) {
        memberService.deleteMember(memberId);
        return ResponseEntity.noContent()
                .build();
    }
}
