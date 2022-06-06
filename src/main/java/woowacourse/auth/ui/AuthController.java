package woowacourse.auth.ui;

import java.net.URI;
import javax.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import woowacourse.shoppingcart.application.CustomerService;
import woowacourse.shoppingcart.dto.customer.SignUpRequest;
import woowacourse.shoppingcart.dto.login.LoginRequest;
import woowacourse.shoppingcart.dto.login.LoginResponse;

@RestController
@RequestMapping("/customers")
public class AuthController {

    private final CustomerService customerService;

    public AuthController(final CustomerService customerService) {
        this.customerService = customerService;
    }

    @PostMapping("/signUp")
    public ResponseEntity<Void> signUp(@Valid @RequestBody SignUpRequest signUpRequest) {
        Long customerId = customerService.signUp(signUpRequest);
        return ResponseEntity.created(URI.create("/customers/" + customerId)).build();
    }

    @PostMapping("/login")
    public ResponseEntity<LoginResponse> login(@Valid @RequestBody LoginRequest loginRequest) {
        LoginResponse loginResponse = customerService.login(loginRequest);
        return ResponseEntity.ok().body(loginResponse);
    }
}
