package woowacourse.auth.ui;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import woowacourse.auth.application.AuthService;
import woowacourse.auth.dto.TokenRequest;
import woowacourse.auth.dto.TokenResponse;
import woowacourse.shoppingcart.application.CustomerService;
import woowacourse.shoppingcart.application.dto.EmailDuplicationResponse;
import woowacourse.shoppingcart.application.dto.SignInDto;

@RestController
@RequestMapping("/api")
public class AuthController {

    private AuthService authService;
    private CustomerService customerService;

    public AuthController(AuthService authService, CustomerService customerService) {
        this.authService = authService;
        this.customerService = customerService;
    }

    @PostMapping("/customer/authentication/sign-in")
    public ResponseEntity<TokenResponse> signIn(@RequestBody final TokenRequest tokenRequest) {
        final TokenResponse tokenResponse = authService.signIn(SignInDto.fromTokenRequest(tokenRequest));
        return ResponseEntity.ok().body(tokenResponse);
    }

    @GetMapping("/validation")
    public ResponseEntity<EmailDuplicationResponse> checkEmailDuplication(@RequestParam String email) {
        EmailDuplicationResponse response = customerService.isDuplicatedEmail(email);
        return ResponseEntity.ok(response);
    }
}
