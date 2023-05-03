package cart.controller.api;

import cart.dto.CreateMemberRequest;
import cart.service.MemberService;
import java.net.URI;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class MemberController {

    private final MemberService memberService;

    public MemberController(final MemberService memberService) {
        this.memberService = memberService;
    }

    @PostMapping("/members")
    public ResponseEntity<Void> addMember(@RequestBody final CreateMemberRequest createMemberRequest) {
        memberService.create(createMemberRequest);
        return ResponseEntity.created(URI.create("/members")).build();
    }
}
