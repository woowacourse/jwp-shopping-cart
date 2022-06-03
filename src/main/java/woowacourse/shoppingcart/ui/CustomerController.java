package woowacourse.shoppingcart.ui;

import java.net.URI;
import javax.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import woowacourse.auth.support.AuthenticationPrincipal;
import woowacourse.shoppingcart.application.CustomerService;
import woowacourse.shoppingcart.domain.customer.Customer;
import woowacourse.shoppingcart.dto.CustomerRequest;
import woowacourse.shoppingcart.dto.CustomerResponse;
import woowacourse.shoppingcart.dto.DuplicateResponse;

@RestController
@RequestMapping("/api/customers")
public class CustomerController {

    private final CustomerService customerService;

    public CustomerController(CustomerService customerService) {
        this.customerService = customerService;
    }

    @PostMapping
    public ResponseEntity<Void> createCustomer(
            @Valid @RequestBody final CustomerRequest.UserNameAndPassword customerRequest) {
        Long id = customerService.signUp(customerRequest);

        return ResponseEntity.created(URI.create("/api/customers/" + id)).build();
    }

    @GetMapping("/me")
    public ResponseEntity<CustomerResponse> getMe(@AuthenticationPrincipal final Customer customer) {
        CustomerResponse response = customerService.getMeById(customer.getId());
        return ResponseEntity.ok(response);
    }

    @PutMapping("/me")
    public ResponseEntity<CustomerResponse> updateCustomer(
            @AuthenticationPrincipal final Customer customer,
            @Valid @RequestBody final CustomerRequest.UserNameAndPassword customerRequest) {
        CustomerResponse response = customerService.updateById(customer.getId(), customerRequest);
        return ResponseEntity.ok(response);
    }

    @DeleteMapping("/me")
    public ResponseEntity<Void> deleteCustomer(@AuthenticationPrincipal final Customer customer) {
        customerService.deleteById(customer.getId());
        return ResponseEntity.noContent().build();
    }

    @PostMapping("/duplication")
    public ResponseEntity<DuplicateResponse> duplicateUserName(
            @Valid @RequestBody final CustomerRequest.UserNameOnly customerRequest) {
        final DuplicateResponse response = customerService.isDuplicateUserName(customerRequest);
        return ResponseEntity.ok(response);
    }
}
