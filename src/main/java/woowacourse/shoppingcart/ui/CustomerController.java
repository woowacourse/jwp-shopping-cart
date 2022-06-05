package woowacourse.shoppingcart.ui;

import java.net.URI;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import woowacourse.auth.application.AuthService;
import woowacourse.auth.config.AuthenticationPrincipal;
import woowacourse.shoppingcart.application.CustomerService;
import woowacourse.shoppingcart.dto.CustomerPasswordRequest;
import woowacourse.shoppingcart.dto.CustomerRequest;
import woowacourse.shoppingcart.dto.CustomerResponse;
import woowacourse.shoppingcart.dto.CustomerUpdateRequest;
import woowacourse.shoppingcart.dto.LoginCustomer;

@RestController
@RequestMapping("/customers")
public class CustomerController {

    private final CustomerService customerService;
    private final AuthService authService;

    public CustomerController(CustomerService customerService, AuthService authService) {
        this.customerService = customerService;
        this.authService = authService;
    }

    @PostMapping
    public ResponseEntity<CustomerResponse> addCustomer(
            @RequestBody CustomerRequest customerRequest) {
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
            @RequestBody CustomerUpdateRequest customerUpdateRequest) {
        authService.checkPassword(loginCustomer.toCustomer(), customerUpdateRequest.getPassword());
        CustomerResponse customerResponse = customerService.updateCustomer(customerUpdateRequest,
                loginCustomer.getLoginId());
        return ResponseEntity.ok().body(customerResponse);
    }

    @DeleteMapping("/me")
    public ResponseEntity<Void> deleteMe(@AuthenticationPrincipal LoginCustomer loginCustomer,
            @RequestBody CustomerPasswordRequest customerPasswordRequest) {
        authService.checkPassword(loginCustomer.toCustomer(), customerPasswordRequest.getPassword());
        customerService.deleteCustomer(loginCustomer.getLoginId());
        return ResponseEntity.noContent().build();
    }
}
