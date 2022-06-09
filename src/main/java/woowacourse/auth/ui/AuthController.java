package woowacourse.auth.ui;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import woowacourse.auth.application.AuthService;
import woowacourse.shoppingcart.application.CustomerService;
import woowacourse.shoppingcart.application.dto.request.LoginRequest;
import woowacourse.shoppingcart.application.dto.response.CustomerResponse;
import woowacourse.shoppingcart.application.dto.response.LoginResponse;

@RestController
public class AuthController {

    private final AuthService authService;
    private final CustomerService customerService;

    public AuthController(final AuthService authService, final CustomerService customerService) {
        this.authService = authService;
        this.customerService = customerService;
    }

    @PostMapping("/customers/login")
    public ResponseEntity<LoginResponse> login(@RequestBody LoginRequest loginRequest) {
        CustomerResponse customerResponse = customerService.login(loginRequest);
        String token = authService.createToken(customerResponse.getId());
        return ResponseEntity.ok().body(LoginResponse.of(token, customerResponse));
    }
}
