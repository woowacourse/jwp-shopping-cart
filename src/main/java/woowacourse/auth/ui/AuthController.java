package woowacourse.auth.ui;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import woowacourse.auth.service.AuthenticationService;
import woowacourse.auth.ui.dto.TokenRequest;
import woowacourse.auth.ui.dto.TokenResponse;

@RestController
@RequestMapping("/api/login")
public class AuthController {
    private final AuthenticationService authenticationService;

    public AuthController(AuthenticationService authenticationService) {
        this.authenticationService = authenticationService;
    }

    @PostMapping
    public ResponseEntity<TokenResponse> getToken(@RequestBody TokenRequest tokenRequest) {
        final String accessToken = authenticationService.getToken(tokenRequest);
        return ResponseEntity.ok().body(new TokenResponse(accessToken));
    }
}
