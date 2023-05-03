package cart.controller;

import cart.service.MemberService;
import cart.service.dto.member.MemberSearchResponse;
import java.util.List;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RequestMapping("/members")
@RestController
public class MemberController {

    private final MemberService memberService;

    public MemberController(final MemberService memberService) {
        this.memberService = memberService;
    }

    @GetMapping
    public ResponseEntity<List<MemberSearchResponse>> searchAll() {
        return ResponseEntity.ok()
                .body(memberService.searchAllMembers());
    }
}
