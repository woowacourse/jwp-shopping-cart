package woowacourse.shoppingcart.ui;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import woowacourse.auth.support.AuthenticationPrincipal;
import woowacourse.shoppingcart.application.CustomerService;
import woowacourse.shoppingcart.domain.Customer;
import woowacourse.shoppingcart.dto.customer.CustomerResponse;
import woowacourse.shoppingcart.dto.customer.CustomerUpdateRequest;
import woowacourse.shoppingcart.dto.customer.CustomerSignUpRequest;

import javax.validation.Valid;

@RestController
@RequestMapping("/users")
public class CustomerController {

    private final CustomerService customerService;

    public CustomerController(CustomerService customerService) {
        this.customerService = customerService;
    }

    @PostMapping
    public ResponseEntity<Void> addCustomer(@RequestBody @Valid CustomerSignUpRequest customerSignUpRequest) {
        customerService.registerCustomer(customerSignUpRequest);
        return ResponseEntity.noContent().build();
    }

    @GetMapping("/me")
    public ResponseEntity<CustomerResponse> getCustomer(@AuthenticationPrincipal Customer customer) {
        CustomerResponse customerResponse = new CustomerResponse(customer.getEmail(), customer.getNickname());
        return ResponseEntity.ok(customerResponse);
    }

    @DeleteMapping("/me")
    public ResponseEntity<Void> deleteCustomer(@AuthenticationPrincipal Customer customer) {
        customerService.deleteByEmail(customer.getEmail());
        return ResponseEntity.noContent().build();
    }

    @PutMapping("/me")
    public ResponseEntity<Void> updateCustomer(@AuthenticationPrincipal Customer customer,
                                               @RequestBody @Valid CustomerUpdateRequest customerUpdateRequest) {
        customerService.updateCustomer(customer.getEmail(), customerUpdateRequest);
        return ResponseEntity.noContent().build();
    }
}
