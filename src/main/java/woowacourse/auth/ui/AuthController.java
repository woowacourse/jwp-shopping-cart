package woowacourse.auth.ui;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import woowacourse.auth.application.AuthService;
import woowacourse.auth.dto.CustomerRequest;
import woowacourse.auth.dto.CustomerResponse;
import woowacourse.auth.dto.TokenRequest;
import woowacourse.auth.dto.TokenResponse;
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
    public ResponseEntity<CustomerResponse> registerCustomers(@RequestBody final CustomerRequest customerRequest){
        CustomerResponse customerResponse = authService.register(customerRequest);
        return ResponseEntity.created(URI.create("/customers/login")).body(customerResponse);
    }

    @PostMapping("/login")
    public ResponseEntity<TokenResponse> login(@RequestBody final TokenRequest tokenRequest) {
        final TokenResponse accessToken = authService.login(tokenRequest);
        return ResponseEntity.ok().body(accessToken);
    }

    @GetMapping
    public ResponseEntity<CustomerResponse> findCustomer(HttpServletRequest request){
        String token = AuthorizationExtractor.extract(request);
        CustomerResponse customerResponse = authService.findCustomerByToken(token);
        return ResponseEntity.ok().body(customerResponse);
    }
}
