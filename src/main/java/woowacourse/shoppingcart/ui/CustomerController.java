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
import woowacourse.shoppingcart.dto.LoginCustomer;

@RestController
@RequestMapping("/customers")
public class CustomerController {

    private final CustomerService customerService;

    public CustomerController(CustomerService customerService) {
        this.customerService = customerService;
    }

    @PostMapping
    public ResponseEntity<CustomerResponse> addCustomer(@RequestBody CustomerRequest customerRequest) {
        CustomerResponse customerResponse = customerService.addCustomer(customerRequest);
        return ResponseEntity.created(URI.create("/customers/me")).body(customerResponse);
    }

    @GetMapping("/me")
    public ResponseEntity<CustomerResponse> getMe(@AuthenticationPrincipal LoginCustomer loginCustomer) {
        CustomerResponse customerResponse = CustomerResponse.of(loginCustomer);
        return ResponseEntity.ok().body(customerResponse);
    }
}
