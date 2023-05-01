package cart.controller;

import java.net.URI;

import cart.dto.MemberRegisterRequest;
import cart.service.MemberService;
import cart.validation.ValidationSequence;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

@Controller
public class MemberController {

    private final MemberService memberService;

    public MemberController(final MemberService memberService) {
        this.memberService = memberService;
    }

    @PostMapping("/member/register")
    public ResponseEntity<Void> register(@RequestBody @Validated(ValidationSequence.class) MemberRegisterRequest memberRegisterRequest) {
        memberService.register(memberRegisterRequest);
        return ResponseEntity.created(URI.create("/member/settings")).build();
    }
}
