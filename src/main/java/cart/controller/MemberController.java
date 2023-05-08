package cart.controller;

import cart.dto.request.MemberRequest;
import cart.dto.response.MemberResponse;
import cart.service.MemberService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;

@RestController
@RequestMapping("/members")
public class MemberController {
    private final MemberService memberService;

    public MemberController(MemberService memberService) {
        this.memberService = memberService;
    }

    @PostMapping
    public ResponseEntity<MemberResponse> create(@RequestBody @Valid MemberRequest memberRequest) {
        MemberResponse memberResponse = memberService.create(memberRequest);
        return ResponseEntity.status(HttpStatus.CREATED).body(memberResponse);
    }

    @PutMapping("/{id}")
    public ResponseEntity<MemberResponse> update(@RequestBody @Valid MemberRequest productRequest, @PathVariable Long id) {
        MemberResponse updated = memberService.update(productRequest, id);
        return ResponseEntity.ok(updated);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        memberService.deleteById(id);
        return ResponseEntity.ok().build();
    }
}
