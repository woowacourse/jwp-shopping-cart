package woowacourse.shoppingcart.ui;

import java.net.URI;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import woowacourse.auth.config.AuthenticationPrincipal;
import woowacourse.shoppingcart.application.CustomerService;
import woowacourse.shoppingcart.dto.CustomerPasswordRequest;
import woowacourse.shoppingcart.dto.CustomerRequest;
import woowacourse.shoppingcart.dto.CustomerResponse;
import woowacourse.shoppingcart.dto.CustomerUpdateRequest;
import woowacourse.shoppingcart.dto.LoginCustomer;
import woowacourse.shoppingcart.dto.Request;

@RestController
@RequestMapping("/customers")
public class CustomerController {

    private final CustomerService customerService;

    public CustomerController(CustomerService customerService) {
        this.customerService = customerService;
    }

    @PostMapping
    public ResponseEntity<CustomerResponse> addCustomer(
            @Validated(Request.allProperties.class) @RequestBody CustomerRequest customerRequest) {
        CustomerResponse customerResponse = customerService.addCustomer(customerRequest);
        return ResponseEntity.created(URI.create("/customers/me")).body(customerResponse);
    }

    @GetMapping("/me")
    public ResponseEntity<CustomerResponse> getMe(
            @AuthenticationPrincipal LoginCustomer loginCustomer) {
        CustomerResponse customerResponse = CustomerResponse.of(loginCustomer);
        return ResponseEntity.ok().body(customerResponse);
    }

    @PutMapping("/me")
    public ResponseEntity<CustomerResponse> updateMe(
            @AuthenticationPrincipal LoginCustomer loginCustomer,
            @Validated(Request.allProperties.class) @RequestBody CustomerUpdateRequest customerUpdateRequest) {
        CustomerResponse customerResponse = customerService.updateCustomer(customerUpdateRequest,
                loginCustomer.toCustomer());
        return ResponseEntity.ok().body(customerResponse);
    }

    @DeleteMapping("/me")
    public ResponseEntity<Void> deleteMe(@AuthenticationPrincipal LoginCustomer loginCustomer,
            @Validated(Request.allProperties.class) @RequestBody CustomerPasswordRequest customerPasswordRequest) {
        customerService.deleteCustomer(loginCustomer.toCustomer(), customerPasswordRequest);
        return ResponseEntity.noContent().build();
    }
}
