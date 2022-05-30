package woowacourse.member.ui;

import java.net.URI;
import javax.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import woowacourse.auth.support.AuthenticationPrincipal;
import woowacourse.member.dto.MemberNameUpdateRequest;
import woowacourse.member.dto.MemberRegisterRequest;
import woowacourse.member.application.MemberService;
import woowacourse.member.dto.MemberResponse;

@RequestMapping("/api/members")
@RestController
public class MemberController {

    private final MemberService memberService;

    public MemberController(MemberService memberService) {
        this.memberService = memberService;
    }

    @PostMapping
    public ResponseEntity<Void> register(@Valid @RequestBody MemberRegisterRequest memberRegisterRequest) {
        memberService.save(memberRegisterRequest);
        return ResponseEntity.status(HttpStatus.CREATED).build();
    }

    @GetMapping("/me")
    public ResponseEntity<MemberResponse> getMyInformation(@AuthenticationPrincipal Long memberId) {
        MemberResponse memberResponse = memberService.getMemberInformation(memberId);
        return ResponseEntity.ok(memberResponse);
    }

    @PutMapping("/me/name")
    public ResponseEntity<Void> updateName(@AuthenticationPrincipal Long memberId,
                                           @Valid @RequestBody MemberNameUpdateRequest memberNameUpdateRequest) {
        memberService.updateName(memberId, memberNameUpdateRequest);
        return ResponseEntity.noContent().build();
    }
}
