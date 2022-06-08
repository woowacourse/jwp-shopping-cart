package woowacourse.auth.ui;

import java.net.URI;
import javax.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import woowacourse.shoppingcart.application.CustomerService;
import woowacourse.shoppingcart.dto.customer.CustomerLoginRequest;
import woowacourse.shoppingcart.dto.customer.CustomerLoginResponse;
import woowacourse.shoppingcart.dto.customer.CustomerSignUpRequest;

@RestController
@RequestMapping("/customers")
public class AuthController {

    private final CustomerService customerService;

    public AuthController(final CustomerService customerService) {
        this.customerService = customerService;
    }

    @PostMapping("/signUp")
    public ResponseEntity<Void> signUp(@Valid @RequestBody CustomerSignUpRequest customerSignUpRequest) {
        Long customerId = customerService.signUp(customerSignUpRequest);
        return ResponseEntity.created(URI.create("/customers/" + customerId)).build();
    }

    @PostMapping("/login")
    public ResponseEntity<CustomerLoginResponse> login(@Valid @RequestBody CustomerLoginRequest customerLoginRequest) {
        CustomerLoginResponse customerLoginResponse = customerService.login(customerLoginRequest);
        return ResponseEntity.ok().body(customerLoginResponse);
    }

    @GetMapping(value = "/check", params = "userId")
    public ResponseEntity<CustomerLoginResponse> checkDuplicateUserId(@RequestParam("userId") String userId) {
        customerService.checkDuplicateUserId(userId);
        return ResponseEntity.ok().build();
    }

    @GetMapping(value = "/check", params = "nickname")
    public ResponseEntity<CustomerLoginResponse> checkDuplicateNickname(@RequestParam("nickname") String nickname) {
        customerService.checkDuplicateNickname(nickname);
        return ResponseEntity.ok().build();
    }
}
