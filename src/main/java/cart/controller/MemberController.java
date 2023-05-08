package cart.controller;

import cart.auth.EncodePassword;
import cart.domain.member.dto.MemberCreateDto;
import cart.domain.member.dto.MemberInformation;
import cart.domain.member.service.MemberService;
import cart.dto.MemberCreateRequest;
import cart.dto.MemberCreateResponse;
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
        final MemberInformation memberInformation = new MemberInformation(request.getEmail(),
            request.getPassword());
        final MemberCreateDto memberCreateDto = memberService.create(memberInformation);
        final MemberCreateResponse response = MemberCreateResponse.of(memberCreateDto);
        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }
}
