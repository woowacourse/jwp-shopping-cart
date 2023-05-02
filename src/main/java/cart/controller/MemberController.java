package cart.controller;

import cart.domain.member.service.MemberService;
import cart.dto.MemberCreateRequest;
import cart.dto.MemberCreateResponse;
import java.net.URI;
import javax.validation.Valid;
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

    @PostMapping("/member")
    public ResponseEntity<MemberCreateResponse> createMember(
        @RequestBody @Valid final MemberCreateRequest request) {
        final MemberCreateResponse response = memberService.create(request);
        return ResponseEntity.created(URI.create("/")).body(response);
    }
}
