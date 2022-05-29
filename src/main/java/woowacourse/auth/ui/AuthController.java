package woowacourse.auth.ui;

import org.apache.coyote.Response;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import woowacourse.auth.application.AuthService;
import woowacourse.auth.dto.CustomerRequest;
import woowacourse.auth.dto.CustomerResponse;

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
}
