package cart.member.controller;

import cart.member.dto.MemberAddRequest;
import cart.member.service.MemberService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;
import java.net.URI;

@RestController
@RequestMapping("/members")
public class MemberApiController {

    private final MemberService memberService;

    @Autowired
    public MemberApiController(MemberService memberService) {
        this.memberService = memberService;
    }

    @PostMapping
    public ResponseEntity<Void> add(@Valid @RequestBody MemberAddRequest memberAddRequest) {
        final long id = memberService.register(memberAddRequest);
        return ResponseEntity.created(URI.create("/members/" + id)).build();
    }
}
