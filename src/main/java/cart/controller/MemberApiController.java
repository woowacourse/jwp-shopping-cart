package cart.controller;

import cart.domain.Member;
import cart.dto.MemberResponse;
import cart.dto.MemberRequest;
import cart.service.MemberService;
import java.net.URI;
import java.util.List;
import java.util.stream.Collectors;
import javax.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
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


    @GetMapping
    public ResponseEntity<List<MemberResponse>> findAllMembers() {
        List<Member> allMembers = memberService.findAllMembers();
        List<MemberResponse> memberResponses = allMembers.stream()
                .map(MemberResponse::from)
                .collect(Collectors.toList());
        return ResponseEntity.ok(memberResponses);
    }

    @PostMapping
    public ResponseEntity<Void> createMember (@RequestBody @Valid MemberRequest memberRequest) {
        Long savedMemberId = memberService.createMember(memberRequest);
        return ResponseEntity.created(URI.create("members"+ savedMemberId)).build();
    }

    @PutMapping("/{id}")
    public ResponseEntity<MemberResponse> updateMember(@PathVariable Long id, @RequestBody @Valid MemberRequest memberRequest) {
        MemberResponse updatedMember = MemberResponse.from(memberService.updateMember(id, memberRequest));
        return ResponseEntity.ok(updatedMember);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> removeMember(@PathVariable Long id) {
        memberService.removeMember(id);
        return ResponseEntity.noContent().build();
    }

}
