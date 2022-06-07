package woowacourse.auth.ui;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import woowacourse.auth.dto.TokenRequest;
import woowacourse.auth.dto.TokenResponse;
import woowacourse.auth.support.JwtTokenProvider;
import woowacourse.auth.support.TokenProvider;
import woowacourse.shoppingcart.application.CustomerService;

import javax.validation.Valid;

@RestController
@RequestMapping("/api")
public class AuthController {

    private final TokenProvider tokenProvider;
    private final CustomerService customerService;

    public AuthController(final TokenProvider tokenProvider, final CustomerService customerService) {
        this.tokenProvider = tokenProvider;
        this.customerService = customerService;
    }

    @PostMapping("/login")
    public ResponseEntity<TokenResponse> login(@RequestBody @Valid final TokenRequest tokenRequest) {
        customerService.validateNameAndPassword(tokenRequest.getUserName(), tokenRequest.getPassword());
        final String token = tokenProvider.createToken(tokenRequest);
        return ResponseEntity.ok(new TokenResponse(token));
    }
}
