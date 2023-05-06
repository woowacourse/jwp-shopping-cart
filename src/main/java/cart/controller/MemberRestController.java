package cart.controller;

import cart.service.MemberService;
import cart.service.dto.MemberRequest;
import java.net.URI;
import javax.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/member")
public class MemberRestController {

    private final MemberService memberService;

    public MemberRestController(final MemberService memberService) {
        this.memberService = memberService;
    }

    @PostMapping
    public ResponseEntity<Void> addMember(@RequestBody @Valid final MemberRequest memberRequest) {
        final long savedMemberId = memberService.save(memberRequest);
        return ResponseEntity.created(URI.create("/member/" + savedMemberId)).build();
    }
}
