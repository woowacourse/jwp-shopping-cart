package woowacourse.shoppingcart.ui;

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
import woowacourse.shoppingcart.application.MemberService;
import woowacourse.shoppingcart.dto.request.MemberCreateRequest;
import woowacourse.shoppingcart.dto.request.MemberUpdateRequest;
import woowacourse.shoppingcart.dto.request.PasswordUpdateRequest;
import woowacourse.shoppingcart.dto.response.EmailUniqueCheckResponse;
import woowacourse.shoppingcart.dto.response.MemberResponse;
import woowacourse.auth.support.AuthenticationPrincipal;

@RestController
@RequestMapping("/api/members")
public class MemberController {

    private final MemberService memberService;

    public MemberController(MemberService memberService) {
        this.memberService = memberService;
    }

    @PostMapping
    public ResponseEntity<Void> signUp(@RequestBody @Valid MemberCreateRequest memberCreateRequest) {
        memberService.save(memberCreateRequest);
        return ResponseEntity.status(HttpStatus.CREATED)
                .build();
    }

    @GetMapping("/email-check")
    public ResponseEntity<EmailUniqueCheckResponse> checkUniqueEmail(@RequestParam String email) {
        EmailUniqueCheckResponse emailUniqueCheckResponse = new EmailUniqueCheckResponse(!memberService.existsEmail(email));
        return ResponseEntity.ok(emailUniqueCheckResponse);
    }

    @GetMapping("/me")
    public ResponseEntity<MemberResponse> showMember(@AuthenticationPrincipal Long memberId) {
        return ResponseEntity.ok(memberService.find(memberId));
    }

    @PatchMapping("/me")
    public ResponseEntity<Void> updateMember(@AuthenticationPrincipal Long memberId,
                                             @RequestBody @Valid MemberUpdateRequest memberUpdateRequest) {
        memberService.updateMember(memberId, memberUpdateRequest);
        return ResponseEntity.noContent()
                .build();
    }

    @PatchMapping("/password")
    public ResponseEntity<Void> updatePassword(@AuthenticationPrincipal Long memberId,
                                               @RequestBody @Valid PasswordUpdateRequest passwordUpdateRequest) {
        memberService.updatePassword(memberId, passwordUpdateRequest);
        return ResponseEntity.noContent()
                .build();
    }

    @DeleteMapping("/me")
    public ResponseEntity<Void> deleteMember(@AuthenticationPrincipal Long memberId) {
        memberService.delete(memberId);
        return ResponseEntity.noContent()
                .build();
    }
}
