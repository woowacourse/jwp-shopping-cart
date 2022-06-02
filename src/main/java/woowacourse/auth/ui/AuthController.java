package woowacourse.auth.ui;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import woowacourse.auth.application.AuthService;
import woowacourse.auth.dto.*;
import woowacourse.auth.support.AuthorizationExtractor;

import javax.servlet.http.HttpServletRequest;
import java.net.URI;

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
    public ResponseEntity<CustomerResponse> findCustomer(HttpServletRequest request) {
        String token = AuthorizationExtractor.extract(request);
        CustomerResponse customerResponse = authService.findCustomerByToken(token);
        return ResponseEntity.ok().body(customerResponse);
    }

    @PostMapping("/email")
    public ResponseEntity<ValidEmailResponse> checkDuplicationOfEmail(@RequestBody ValidEmailRequest validEmailRequest) {
        final ValidEmailResponse validEmailResponse = authService.isValidEmail(validEmailRequest);
        return ResponseEntity.ok().body(validEmailResponse);
    }

    @PutMapping
    public ResponseEntity<Void> updateCustomer(@RequestBody CustomerRequest customerRequest, HttpServletRequest request) {
        String token = AuthorizationExtractor.extract(request);
        authService.edit(token, customerRequest);
        return ResponseEntity.ok().build();
    }

    @DeleteMapping
    public ResponseEntity<Void> deleteCustomer(HttpServletRequest request){
        String token = AuthorizationExtractor.extract(request);
        authService.delete(token);
        return ResponseEntity.noContent().build();
    }
}
