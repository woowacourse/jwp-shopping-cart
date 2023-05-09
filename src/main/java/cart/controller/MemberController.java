package cart.controller;

import cart.auth.EncodePassword;
import cart.domain.member.dto.CreatedMemberDto;
import cart.domain.member.dto.MemberDto;
import cart.domain.member.service.MemberService;
import cart.dto.request.MemberCreateRequest;
import cart.dto.response.MemberCreateResponse;
import javax.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api")
public class MemberController {

    private final MemberService memberService;

    public MemberController(final MemberService memberService) {
        this.memberService = memberService;
    }

    @PostMapping("/member")
    public ResponseEntity<MemberCreateResponse> createMember(
        @RequestBody @Valid @EncodePassword final MemberCreateRequest request) {
        final MemberDto memberDto = MemberDto.of(request);
        final CreatedMemberDto createdMemberDto = memberService.create(memberDto);
        final MemberCreateResponse response = MemberCreateResponse.of(createdMemberDto);
        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }
}
