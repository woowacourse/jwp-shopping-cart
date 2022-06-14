package woowacourse.shoppingcart.ui;

import javax.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import woowacourse.auth.support.Auth;
import woowacourse.auth.support.AuthenticationPrincipal;
import woowacourse.shoppingcart.application.CustomerService;
import woowacourse.shoppingcart.domain.customer.Customer;
import woowacourse.shoppingcart.dto.CustomerCreationRequest;
import woowacourse.shoppingcart.dto.CustomerResponse;
import woowacourse.shoppingcart.dto.CustomerUpdationRequest;

@RestController
@RequestMapping("/users")
public class CustomerController {

    private final CustomerService customerService;

    public CustomerController(CustomerService customerService) {
        this.customerService = customerService;
    }

    @PostMapping
    public ResponseEntity<Void> create(@RequestBody @Valid CustomerCreationRequest customerCreationRequest) {
        customerService.create(customerCreationRequest);

        return ResponseEntity.noContent().build();
    }

    @Auth
    @GetMapping("/me")
    public ResponseEntity<CustomerResponse> getMe(@AuthenticationPrincipal String email) {
        Customer customer = customerService.getByEmail(email);
        CustomerResponse response = new CustomerResponse(
                customer.getEmail(),
                customer.getNickname()
        );
        return ResponseEntity.ok(response);
    }

    @Auth
    @DeleteMapping("/me")
    public ResponseEntity<Void> deleteMe(@AuthenticationPrincipal String email) {
        Customer customer = customerService.getByEmail(email);
        customerService.delete(customer);
        return ResponseEntity.noContent().build();
    }

    @Auth
    @PutMapping("/me")
    public ResponseEntity<Void> updateMe(@AuthenticationPrincipal String email,
                                         @Valid @RequestBody CustomerUpdationRequest request) {
        Customer customer = customerService.getByEmail(email);
        customerService.update(customer, request);
        return ResponseEntity.noContent().build();
    }
}
