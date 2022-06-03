package woowacourse.auth.ui;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;
import woowacourse.auth.application.AuthService;
import woowacourse.auth.dto.TokenRequest;
import woowacourse.auth.dto.TokenResponse;
import woowacourse.auth.support.AuthenticationPrincipal;
import woowacourse.shoppingcart.dto.CustomerRequest;
import woowacourse.shoppingcart.dto.CustomerResponse;

@RestController
@RequestMapping("/customers")
public class AuthController {

    private final AuthService authService;

    public AuthController(AuthService authService) {
        this.authService = authService;
    }

    @PostMapping("/email")
    @ResponseStatus(HttpStatus.OK)
    public void checkEmail(@RequestParam String email) {
        authService.checkDuplicationEmail(email);
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public CustomerResponse save(@RequestBody CustomerRequest customerRequest) {
        return authService.save(customerRequest);
    }

    @PostMapping("/login")
    @ResponseStatus(HttpStatus.OK)
    public TokenResponse login(@RequestBody TokenRequest tokenRequest) {
        Long customerId = authService.loginCustomer(tokenRequest);
        return authService.createToken(customerId);
    }

    @GetMapping
    @ResponseStatus(HttpStatus.OK)
    public CustomerResponse showCustomer(@AuthenticationPrincipal Long customerId) {
        return authService.find(customerId);
    }

    @PutMapping
    @ResponseStatus(HttpStatus.OK)
    public void updateCustomer(@AuthenticationPrincipal Long customerId,
                               @RequestBody CustomerRequest customerRequest) {
        authService.update(customerId, customerRequest);
    }

    @DeleteMapping
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deleteCustomer(@AuthenticationPrincipal Long customerId) {
        authService.delete(customerId);
    }
}
