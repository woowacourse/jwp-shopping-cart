package cart.controller.api;

import cart.dto.request.member.MemberSignupRequest;
import cart.dto.response.Response;
import cart.dto.response.SimpleResponse;
import cart.service.MemberService;
import javax.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/members")
public class MemberApiController {
    private final MemberService memberService;

    public MemberApiController(MemberService memberService) {
        this.memberService = memberService;
    }

    @PostMapping("/signup")
    public ResponseEntity<Response> signupMember(@RequestBody @Valid MemberSignupRequest request) {
        memberService.signupMember(request.getEmail(), request.getPassword());
        return ResponseEntity.ok()
                .body(SimpleResponse.ok("회원가입이 정상적으로 되었습니다."));
    }
}
