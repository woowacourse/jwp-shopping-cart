package woowacourse.auth.ui;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import woowacourse.auth.dto.SignupRequest;

import java.net.URI;
import java.net.URISyntaxException;

@RestController
public class AuthController {

    @PostMapping("/signup")
    public ResponseEntity<Void> signup(@RequestBody SignupRequest signupRequest) throws URISyntaxException {
        return ResponseEntity.created(new URI("/signin"))
                .build();
    }
}
