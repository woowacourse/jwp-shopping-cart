package woowacourse.member.ui;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import woowacourse.auth.support.AuthenticationPrincipal;
import woowacourse.member.application.MemberService;
import woowacourse.member.dto.DuplicateEmailRequest;
import woowacourse.member.dto.MemberInfoResponse;
import woowacourse.member.dto.SignUpRequest;

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
    public MemberInfoResponse findMemberInfo(@AuthenticationPrincipal Long id) {
        return memberService.findMemberById(id);
    }

    @DeleteMapping("/me")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deleteMember(@AuthenticationPrincipal Long id) {
        memberService.deleteMemberById(id);
    }

    @PostMapping("/duplicate-email")
    @ResponseStatus(HttpStatus.OK)
    public void checkDuplicateEmail(@Valid @RequestBody DuplicateEmailRequest request) {
        memberService.checkDuplicateEmail(request);
    }
}
