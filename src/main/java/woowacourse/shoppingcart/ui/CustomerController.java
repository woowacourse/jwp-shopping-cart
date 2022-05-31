package woowacourse.shoppingcart.ui;

import java.net.URI;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import woowacourse.auth.support.AuthenticationPrincipal;
import woowacourse.shoppingcart.application.CustomerService;
import woowacourse.shoppingcart.dto.CustomerRequest;
import woowacourse.shoppingcart.dto.CustomerResponse;

@RestController
@RequestMapping("/api/customers")
public class CustomerController {

    private final CustomerService customerService;

    public CustomerController(CustomerService customerService) {
        this.customerService = customerService;
    }

    @PostMapping
    public ResponseEntity<Void> register(@RequestBody CustomerRequest customerRequest) {
        customerService.register(customerRequest.getEmail(),
                customerRequest.getPassword(),
                customerRequest.getUsername());
        return ResponseEntity.created(URI.create("/login")).build();
    }

    @GetMapping("/me")
    public ResponseEntity<CustomerResponse> showCustomer(@AuthenticationPrincipal String email) {
        CustomerResponse customerResponse = customerService.showCustomer(email);
        return ResponseEntity.ok(customerResponse);
    }
}
