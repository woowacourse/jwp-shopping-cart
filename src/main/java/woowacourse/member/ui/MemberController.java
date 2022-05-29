package woowacourse.member.ui;

import java.net.URI;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import woowacourse.member.dto.MemberRegisterRequest;
import woowacourse.member.service.MemberService;

@RequestMapping("/api/members")
@RestController
public class MemberController {

    private final MemberService memberService;

    public MemberController(MemberService memberService) {
        this.memberService = memberService;
    }

    @PostMapping
    public ResponseEntity<Void> register(@RequestBody MemberRegisterRequest memberRegisterRequest) {
        memberService.save(memberRegisterRequest);
        return ResponseEntity.created(URI.create("/api/members")).build();
    }

}
