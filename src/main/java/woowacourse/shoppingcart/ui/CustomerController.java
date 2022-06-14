package woowacourse.shoppingcart.ui;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import woowacourse.auth.dto.DeleteCustomerRequest;
import woowacourse.shoppingcart.dto.UpdateCustomerRequest;
import woowacourse.auth.support.AuthenticationPrincipal;
import woowacourse.shoppingcart.application.CustomerService;
import woowacourse.shoppingcart.dto.CustomerResponse;
import woowacourse.shoppingcart.dto.SignupRequest;

import javax.validation.Valid;
import java.net.URI;
import java.net.URISyntaxException;

@RestController
public class CustomerController {

    private final CustomerService customerService;

    public CustomerController(final CustomerService customerService) {
        this.customerService = customerService;
    }

    @PostMapping("/signup")
    public ResponseEntity<Void> signup(@RequestBody @Valid final SignupRequest signupRequest) throws URISyntaxException {
        customerService.create(signupRequest);

        return ResponseEntity.created(new URI("/signin"))
                .build();
    }

    @GetMapping("/customers")
    public CustomerResponse getCustomers(@AuthenticationPrincipal final long customerId) {
        return customerService.getById(customerId);
    }

    @PutMapping("/customers")
    public void updateCustomer(@AuthenticationPrincipal final long customerId, @RequestBody @Valid final UpdateCustomerRequest updateCustomerRequest) {
        customerService.update(customerId, updateCustomerRequest);
    }

    @DeleteMapping("/customers")
    public ResponseEntity<Void> deleteCustomer(@AuthenticationPrincipal final long customerId, @RequestBody final DeleteCustomerRequest deleteCustomerRequest) {
        customerService.delete(customerId, deleteCustomerRequest);
        return ResponseEntity.noContent().build();
    }
}
