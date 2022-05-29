package woowacourse.member.ui;

import java.net.URI;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RequestMapping("/api/members")
@RestController
public class MemberController {

    @PostMapping
    public ResponseEntity<Void> register() {
        return ResponseEntity.created(URI.create("/api/members")).build();
    }

}
