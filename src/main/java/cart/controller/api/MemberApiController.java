package cart.controller.api;

import cart.dto.member.MemberDto;
import cart.dto.request.member.MemberSignupRequest;
import cart.dto.response.Response;
import cart.dto.response.ResultResponse;
import cart.dto.response.SimpleResponse;
import cart.service.MemberService;
import java.util.List;
import javax.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
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

    @GetMapping
    public ResponseEntity<Response> findAllMembers() {
        List<MemberDto> allMembers = memberService.findAllMembers();
        return ResponseEntity.ok()
                .body(ResultResponse.ok(allMembers.size() + "명의 회원이 조회되었습니다.", allMembers));
    }
}
