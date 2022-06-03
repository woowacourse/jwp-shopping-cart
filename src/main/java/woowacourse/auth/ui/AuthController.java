package woowacourse.auth.ui;

import java.net.URI;
import javax.servlet.http.HttpServletRequest;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import woowacourse.auth.application.AuthService;
import woowacourse.auth.dto.CustomerRequest;
import woowacourse.auth.dto.CustomerResponse;
import woowacourse.auth.dto.TokenRequest;
import woowacourse.auth.dto.TokenResponse;
import woowacourse.auth.support.AuthenticationPrincipal;
import woowacourse.auth.support.AuthorizationExtractor;

@RestController
@RequestMapping("/customers")
public class AuthController {

    private final AuthService authService;

    public AuthController(final AuthService authService) {
        this.authService = authService;
    }

    @PostMapping
    public ResponseEntity<CustomerResponse> registerCustomers(@RequestBody final CustomerRequest customerRequest) {
        CustomerResponse customerResponse = authService.register(customerRequest);
        return ResponseEntity.created(URI.create("/customers/login")).body(customerResponse);
    }

    @PostMapping("/login")
    public ResponseEntity<TokenResponse> login(@RequestBody final TokenRequest tokenRequest) {
        final TokenResponse accessToken = authService.login(tokenRequest);
        return ResponseEntity.ok().body(accessToken);
    }

    @GetMapping
    public ResponseEntity<CustomerResponse> findCustomer(@AuthenticationPrincipal Long id) {
        CustomerResponse customerResponse = authService.findCustomerById(id);
        return ResponseEntity.ok().body(customerResponse);
    }

    @PutMapping
    public ResponseEntity<Void> updateCustomer(@RequestBody CustomerRequest customerRequest, @AuthenticationPrincipal Long id) {
        authService.edit(id, customerRequest);
        return ResponseEntity.ok().build();
    }

    @DeleteMapping
    public ResponseEntity<Void> deleteCustomer(@AuthenticationPrincipal Long id){
        authService.delete(id);
        return ResponseEntity.noContent().build();
    }

    @PostMapping("/email")
    public ResponseEntity<Boolean> checkEmail(String email){
        boolean isValidEmail = authService.validateEmail(email);
        return ResponseEntity.ok().body(isValidEmail);
    }
}
