package woowacourse.auth.ui;

import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import woowacourse.shoppingcart.application.CustomerService;
import woowacourse.shoppingcart.dto.request.SignInRequest;
import woowacourse.shoppingcart.dto.response.TokenResponse;

@RestController
public class AuthController {

    private final CustomerService customerService;

    public AuthController(CustomerService customerService) {
        this.customerService = customerService;
    }

    @PostMapping("/signin")
    public TokenResponse signIn(@RequestBody SignInRequest signinRequest) {
        return customerService.signIn(signinRequest);
    }

}
