package cart.controller;

import cart.dto.MemberRequestDto;
import cart.service.MemberService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;

@RestController
public class MemberRestController {
    private final MemberService memberService;

    public MemberRestController(MemberService memberService) {
        this.memberService = memberService;
    }

    @PostMapping("/member")
    public ResponseEntity<Void> createMember(@RequestBody @Valid MemberRequestDto memberRequestDto) {
        memberService.addMember(memberRequestDto);
        return ResponseEntity.noContent().build();
    }
}
