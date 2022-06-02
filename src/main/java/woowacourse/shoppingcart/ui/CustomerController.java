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
import woowacourse.shoppingcart.support.Login;
import woowacourse.shoppingcart.application.CustomerService;
import woowacourse.shoppingcart.domain.Customer;
import woowacourse.shoppingcart.dto.CustomerCreationRequest;
import woowacourse.shoppingcart.dto.CustomerResponse;
import woowacourse.shoppingcart.dto.CustomerUpdationRequest;

@RestController
@RequestMapping("/users")
public class CustomerController {

    private final CustomerService customerService;

    public CustomerController(final CustomerService customerService) {
        this.customerService = customerService;
    }

    @PostMapping
    public ResponseEntity<Void> create(@RequestBody @Valid final CustomerCreationRequest customerCreationRequest) {
        customerService.create(customerCreationRequest);

        return ResponseEntity.noContent().build();
    }

    @GetMapping("/me")
    public ResponseEntity<CustomerResponse> getMe(@Login final Customer customer) {
        final CustomerResponse response = new CustomerResponse(
                customer.getEmail(),
                customer.getNickname()
        );
        return ResponseEntity.ok(response);
    }

    @DeleteMapping("/me")
    public ResponseEntity<Void> deleteMe(@Login final Customer customer) {
        customerService.delete(customer);
        return ResponseEntity.noContent().build();
    }

    @PutMapping("/me")
    public ResponseEntity<Void> updateMe(@Login final Customer customer,
                                         @Valid @RequestBody final CustomerUpdationRequest request) {
        customerService.update(customer, request);
        return ResponseEntity.noContent().build();
    }
}
