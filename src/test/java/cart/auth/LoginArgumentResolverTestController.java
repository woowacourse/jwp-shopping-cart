package cart.auth;

import cart.domain.member.Member;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class LoginArgumentResolverTestController {

    @GetMapping("/test")
    public ResponseEntity<TestMemberResponse> testLoginArgumentResolver(@Login Member member) {
        return ResponseEntity.ok(TestMemberResponse.from(member));
    }
}
