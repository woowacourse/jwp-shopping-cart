package woowacourse.shoppingcart.ui;

import java.net.URI;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import woowacourse.shoppingcart.application.CustomerService;
import woowacourse.shoppingcart.dto.LoginRequest;
import woowacourse.shoppingcart.dto.LoginResponse;
import woowacourse.shoppingcart.dto.SignUpRequest;

@RestController
@RequestMapping("/customers")
public class CustomerController {

    private final CustomerService customerService;

    public CustomerController(final CustomerService customerService) {
        this.customerService = customerService;
    }

    @PostMapping("/signUp")
    public ResponseEntity<Void> signUp(@RequestBody SignUpRequest signUpRequest) {
        Long customerId = customerService.signUp(signUpRequest);
        return ResponseEntity.created(URI.create("/customers/" + customerId)).build();
    }

    @PostMapping("/login")
    public ResponseEntity<LoginResponse> login(@RequestBody LoginRequest loginRequest) {
        LoginResponse loginResponse = customerService.login(loginRequest);
        return ResponseEntity.ok().body(loginResponse);
    }
}
