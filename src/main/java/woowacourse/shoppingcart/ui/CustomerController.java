package woowacourse.shoppingcart.ui;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import woowacourse.auth.support.AuthenticationPrincipal;
import woowacourse.shoppingcart.application.CustomerService;
import woowacourse.shoppingcart.domain.Customer;
import woowacourse.shoppingcart.dto.CustomerRequest;
import woowacourse.shoppingcart.dto.CustomerResponse;
import woowacourse.shoppingcart.dto.DuplicateResponse;

import javax.validation.Valid;
import java.net.URI;

@RestController
@RequestMapping("/api/customers")
public class CustomerController {

    private final CustomerService customerService;

    public CustomerController(CustomerService customerService) {
        this.customerService = customerService;
    }

    @PostMapping
    public ResponseEntity<Void> createCustomer(
            @Valid @RequestBody CustomerRequest.UserNameAndPassword customerRequest) {
        Long id = customerService.signUp(customerRequest);

        return ResponseEntity.created(URI.create("/api/customers/" + id)).build();
    }

    @GetMapping("/me")
    public ResponseEntity<CustomerResponse> getMe(@AuthenticationPrincipal Customer customer) {
        CustomerResponse response = customerService.getMeById(customer.getId());
        return ResponseEntity.ok(response);
    }

    @PutMapping("/me")
    public ResponseEntity<CustomerResponse> updateCustomer(@AuthenticationPrincipal Customer customer,
                                                           @Valid @RequestBody CustomerRequest.UserNameAndPassword customerRequest) {
        CustomerResponse response = customerService.updateById(customer.getId(), customerRequest);
        return ResponseEntity.ok(response);
    }

    @DeleteMapping("/me")
    public ResponseEntity<Void> deleteCustomer(@AuthenticationPrincipal Customer customer) {
        customerService.deleteById(customer.getId());
        return ResponseEntity.noContent().build();
    }

    @PostMapping("/duplication")
    public ResponseEntity<DuplicateResponse> duplicateUserName(
            @Valid @RequestBody CustomerRequest.UserNameOnly customerRequest) {
        final DuplicateResponse response = customerService.isDuplicateUserName(customerRequest);
        return ResponseEntity.ok(response);
    }
}
