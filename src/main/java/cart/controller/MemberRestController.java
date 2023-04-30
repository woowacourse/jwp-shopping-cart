package cart.controller;

import cart.controller.dto.MemberDto;
import cart.service.MemberService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;
import java.net.URI;

@RestController
@RequestMapping("/member")
public class MemberRestController {

    private final MemberService memberService;

    public MemberRestController(final MemberService memberService) {
        this.memberService = memberService;
    }

    @PostMapping
    public ResponseEntity<Void> addMember(@RequestBody @Valid final MemberDto memberDto) {
        final long savedMemberId = memberService.save(memberDto);
        return ResponseEntity.created(URI.create("/member/" + savedMemberId)).build();
    }
}
