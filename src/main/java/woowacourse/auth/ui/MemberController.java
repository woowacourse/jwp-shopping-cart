package woowacourse.auth.ui;

import javax.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import woowacourse.auth.application.MemberService;
import woowacourse.auth.dto.request.MemberUpdateRequest;
import woowacourse.auth.dto.request.PasswordUpdateRequest;
import woowacourse.auth.dto.response.MemberResponse;
import woowacourse.auth.support.AuthenticationPrincipal;

@RestController
@RequestMapping("/api/members")
public class MemberController {

    private final MemberService memberService;

    public MemberController(MemberService memberService) {
        this.memberService = memberService;
    }

    @GetMapping("/me")
    public ResponseEntity<MemberResponse> showMember(@AuthenticationPrincipal String payload) {
        return ResponseEntity.ok(memberService.find(payload));
    }

    @PatchMapping("/me")
    public ResponseEntity<Void> updateMember(@AuthenticationPrincipal String payload,
                                             @RequestBody @Valid MemberUpdateRequest memberUpdateRequest) {
        memberService.updateMember(payload, memberUpdateRequest);
        return ResponseEntity.noContent()
                .build();
    }

    @PatchMapping("/password")
    public ResponseEntity<Void> updatePassword(@AuthenticationPrincipal String payload,
                                               @RequestBody @Valid PasswordUpdateRequest passwordUpdateRequest) {
        memberService.updatePassword(payload, passwordUpdateRequest);
        return ResponseEntity.noContent()
                .build();
    }

    @DeleteMapping("/me")
    public ResponseEntity<Void> deleteMember(@AuthenticationPrincipal String payload) {
        memberService.delete(payload);
        return ResponseEntity.noContent()
                .build();
    }
}
