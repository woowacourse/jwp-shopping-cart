package woowacourse.shoppingcart.ui;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import woowacourse.auth.support.AuthenticationPrincipal;
import woowacourse.shoppingcart.application.CustomerService;
import woowacourse.shoppingcart.domain.Customer;
import woowacourse.shoppingcart.dto.CustomerResponse;
import woowacourse.shoppingcart.dto.CustomerUpdateRequest;
import woowacourse.shoppingcart.dto.SignUpRequest;

import javax.validation.Valid;

@RestController
public class CustomerController {

    private final CustomerService customerService;

    public CustomerController(CustomerService customerService) {
        this.customerService = customerService;
    }

    @PostMapping("/users")
    public ResponseEntity<Void> addCustomer(@RequestBody @Valid SignUpRequest signUpRequest) {
        customerService.registerCustomer(signUpRequest);
        return ResponseEntity.noContent().build();
    }

    @GetMapping("/users/me")
    public ResponseEntity<CustomerResponse> getCustomer(@AuthenticationPrincipal String email) {
        Customer customer = customerService.findByEmail(email);
        CustomerResponse customerResponse = new CustomerResponse(customer.getEmail(), customer.getNickname());
        return ResponseEntity.ok(customerResponse);
    }

    @DeleteMapping("/users/me")
    public ResponseEntity<Void> deleteCustomer(@AuthenticationPrincipal String email) {
        customerService.deleteByEmail(email);
        return ResponseEntity.noContent().build();
    }

    @PutMapping("/users/me")
    public ResponseEntity<Void> updateCustomer(@AuthenticationPrincipal String email,
                                               @RequestBody @Valid CustomerUpdateRequest customerUpdateRequest) {
        customerService.updateCustomer(email, customerUpdateRequest);
        return ResponseEntity.noContent().build();
    }
}
