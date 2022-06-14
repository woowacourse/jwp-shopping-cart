package woowacourse.member.ui;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import woowacourse.auth.application.AuthService;
import woowacourse.auth.dto.TokenResponse;
import woowacourse.auth.support.AuthenticationPrincipal;
import woowacourse.member.application.MemberService;
import woowacourse.member.dto.request.*;
import woowacourse.member.dto.response.MemberInfoResponse;

import javax.validation.Valid;

@RestController
@RequestMapping("/api")
public class MemberController {

    private final AuthService authService;
    private final MemberService memberService;

    public MemberController(AuthService authService, MemberService memberService) {
        this.authService = authService;
        this.memberService = memberService;
    }

    @PostMapping("/auth")
    @ResponseStatus(HttpStatus.OK)
    public TokenResponse login(@Valid @RequestBody LoginRequest request) {
        long id = memberService.logIn(request);
        return authService.createToken(id);
    }

    @PostMapping("/members")
    @ResponseStatus(HttpStatus.CREATED)
    public void signUp(@Valid @RequestBody SignUpRequest request) {
        memberService.signUp(request);
    }

    @GetMapping("/members/duplicate-email")
    @ResponseStatus(HttpStatus.OK)
    public void checkDuplicateEmail(@Valid @ModelAttribute DuplicateEmailRequest request) {
        memberService.checkDuplicateEmail(request);
    }

    @GetMapping("/members/me")
    @ResponseStatus(HttpStatus.OK)
    public MemberInfoResponse findMemberInfo(@AuthenticationPrincipal Long id) {
        return memberService.findMemberInfoById(id);
    }

    @PutMapping("/members/me/name")
    @ResponseStatus(HttpStatus.OK)
    public void updateName(@AuthenticationPrincipal Long id, @Valid @RequestBody UpdateNameRequest request) {
        memberService.updateName(id, request);
    }

    @PutMapping("/members/me/password")
    @ResponseStatus(HttpStatus.OK)
    public void updatePassword(@AuthenticationPrincipal Long id, @Valid @RequestBody UpdatePasswordRequest request) {
        memberService.updatePassword(id, request);
    }

    @DeleteMapping("/members/me")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deleteMember(@AuthenticationPrincipal Long id) {
        memberService.deleteMemberById(id);
    }
}
