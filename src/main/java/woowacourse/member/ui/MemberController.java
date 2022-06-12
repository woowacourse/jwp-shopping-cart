package woowacourse.member.ui;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import woowacourse.auth.support.AuthenticationPrincipal;
import woowacourse.member.application.MemberService;
import woowacourse.member.dto.FindMemberInfoResponse;
import woowacourse.member.dto.SignUpRequest;
import woowacourse.member.dto.UpdateNameRequest;
import woowacourse.member.dto.UpdatePasswordRequest;

import javax.validation.Valid;

@RestController
@RequestMapping("/api/members")
public class MemberController {

    private final MemberService memberService;

    public MemberController(MemberService memberService) {
        this.memberService = memberService;
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public void signUp(@Valid @RequestBody SignUpRequest request) {
        memberService.signUp(request);
    }

    @GetMapping("/me")
    @ResponseStatus(HttpStatus.OK)
    public FindMemberInfoResponse findMemberInfo(@AuthenticationPrincipal long id) {
        return memberService.findMemberInfo(id);
    }

    @GetMapping("/duplicate-email")
    @ResponseStatus(HttpStatus.OK)
    public void checkDuplicateEmail(@RequestParam String email) {
        memberService.checkDuplicateEmail(email);
    }

    @PutMapping("/me/name")
    @ResponseStatus(HttpStatus.OK)
    public void updateName(@AuthenticationPrincipal long id, @Valid @RequestBody UpdateNameRequest request) {
        memberService.updateName(id, request);
    }

    @PutMapping("/me/password")
    @ResponseStatus(HttpStatus.OK)
    public void updatePassword(@AuthenticationPrincipal long id, @Valid @RequestBody UpdatePasswordRequest request) {
        memberService.updatePassword(id, request);
    }

    @DeleteMapping("/me")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deleteMember(@AuthenticationPrincipal long id) {
        memberService.withdraw(id);
    }
}
