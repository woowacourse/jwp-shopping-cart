package woowacourse.shoppingcart.ui;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import woowacourse.auth.application.AuthService;
import woowacourse.auth.support.AuthorizationExtractor;
import woowacourse.shoppingcart.application.CustomerService;
import woowacourse.shoppingcart.dto.CustomerRequest;
import woowacourse.shoppingcart.dto.CustomerResponse;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;
import java.net.URI;

@RestController
@RequestMapping("/customers")
public class CustomerController {
    private final CustomerService customerService;
    private final AuthService authService;

    public CustomerController(CustomerService customerService, AuthService authService) {
        this.customerService = customerService;
        this.authService = authService;
    }

    @PostMapping("/signup")
    public ResponseEntity<Void> addCustomer(@Valid @RequestBody CustomerRequest customerRequest) {
        customerService.addCustomer(customerRequest);
        return ResponseEntity.created(URI.create("/customers/me")).build();
    }

    @GetMapping("/me")
    public ResponseEntity<CustomerResponse> findMyInfo(HttpServletRequest httpServletRequest) {
        String accessToken = AuthorizationExtractor.extract(httpServletRequest);
        CustomerResponse customerResponse = authService.findCustomerByToken(accessToken);
        return ResponseEntity.ok(customerResponse);
    }
}
