package cart.controller;

import cart.dto.MemberResponse;
import cart.dto.MemberRequest;
import cart.service.MemberService;
import java.net.URI;
import java.util.List;
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
        List<MemberResponse> allMembers = memberService.findAllMembers();
        return ResponseEntity.ok(allMembers);
    }

    @PostMapping
    public ResponseEntity<MemberResponse> createMember (@RequestBody @Valid MemberRequest memberRequest) {
        MemberResponse savedMember = memberService.createMember(memberRequest);
        return ResponseEntity.created(URI.create("members"+ savedMember.getId())).body(savedMember);
    }

    @PutMapping("/{id}")
    public ResponseEntity<MemberResponse> updateMember(@PathVariable Long id, @RequestBody @Valid MemberRequest memberRequest) {
        MemberResponse updatedMember = memberService.updateMember(id, memberRequest);
        return ResponseEntity.ok(updatedMember);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> removeMember(@PathVariable Long id) {
        memberService.removeMember(id);
        return ResponseEntity.noContent().build();
    }

}
