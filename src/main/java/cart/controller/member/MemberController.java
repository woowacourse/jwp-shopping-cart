package cart.controller.member;

import cart.dto.MemberDto;
import cart.service.MemberService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;
import java.net.URI;

@RestController
public class MemberController {

    private final MemberService memberService;

    public MemberController(final MemberService memberService) {
        this.memberService = memberService;
    }

    @PostMapping("/register")
    ResponseEntity<Void> register(@Valid @RequestBody final MemberDto memberDto) {
        final Long id = memberService.registerMember(memberDto);
        return ResponseEntity.created(URI.create("/register/" + id)).build();
    }

    @PostMapping("/settings")
    ResponseEntity<MemberDto> login(@Valid @RequestBody final MemberDto memberDto) {
        final String email = memberDto.getEmail();
        final String password = memberDto.getPassword();
        final MemberDto member = memberService.loginMember(email, password);
        return ResponseEntity.ok().body(member);
    }
}
