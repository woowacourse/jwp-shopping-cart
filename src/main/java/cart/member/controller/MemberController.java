package cart.member.controller;

import cart.member.domain.Member;
import cart.member.service.MemberService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/members")
public class MemberController {

    private final MemberService memberService;

    @GetMapping("/")
    public ResponseEntity<List<Member>> getAllMember() {
        final List<Member> members = memberService.findAll();

        return ResponseEntity.ok(members);
    }

    @GetMapping("/{memberId}")
    public ResponseEntity<Member> getMember(@PathVariable final Long memberId) {
        final Member member = memberService.find(memberId);

        return ResponseEntity.ok(member);
    }
}
