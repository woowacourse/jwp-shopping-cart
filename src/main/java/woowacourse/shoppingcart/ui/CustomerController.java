package woowacourse.shoppingcart.ui;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import woowacourse.auth.dto.DeleteCustomerRequest;
import woowacourse.auth.dto.UpdateCustomerRequest;
import woowacourse.auth.support.AuthenticationPrincipal;
import woowacourse.shoppingcart.application.CustomerService;
import woowacourse.shoppingcart.dto.CustomerDto;
import woowacourse.shoppingcart.dto.SignupRequest;

import javax.validation.Valid;
import java.net.URI;
import java.net.URISyntaxException;

@RestController
public class CustomerController {

    private final CustomerService customerService;

    public CustomerController(CustomerService customerService) {
        this.customerService = customerService;
    }

    @PostMapping("/signup")
    public ResponseEntity<Void> signup(@RequestBody @Valid SignupRequest signupRequest) throws URISyntaxException {
        customerService.create(signupRequest);

        return ResponseEntity.created(new URI("/signin"))
                .build();
    }

    @GetMapping("/customers")
    public CustomerDto getCustomers(@AuthenticationPrincipal long customerId) {
        return customerService.getById(customerId);
    }

    @PutMapping("/customers")
    public void updateCustomer(@AuthenticationPrincipal long customerId, @RequestBody @Valid UpdateCustomerRequest updateCustomerRequest) {
        customerService.update(customerId, updateCustomerRequest);
    }

    @DeleteMapping("/customers")
    public ResponseEntity<Void> deleteCustomer(@AuthenticationPrincipal long customerId, @RequestBody DeleteCustomerRequest deleteCustomerRequest) {
        customerService.delete(customerId, deleteCustomerRequest);
        return ResponseEntity.noContent().build();
    }
}
