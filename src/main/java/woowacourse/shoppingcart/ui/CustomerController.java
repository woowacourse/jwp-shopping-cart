package woowacourse.shoppingcart.ui;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import woowacourse.auth.support.AuthenticationPrincipal;
import woowacourse.shoppingcart.application.CustomerService;
import woowacourse.shoppingcart.domain.customer.Customer;
import woowacourse.shoppingcart.dto.CustomerRequest;
import woowacourse.shoppingcart.dto.CustomerResponse;
import woowacourse.shoppingcart.dto.PasswordRequest;
import woowacourse.shoppingcart.dto.UsernameDuplicationResponse;

import javax.validation.Valid;
import java.net.URI;

@RestController
@RequestMapping("/customers")
public class CustomerController {
    private final CustomerService customerService;

    public CustomerController(CustomerService customerService) {
        this.customerService = customerService;
    }

    @PostMapping
    public ResponseEntity<Void> addCustomer(@Valid @RequestBody CustomerRequest customerRequest) {
        customerService.addCustomer(customerRequest);
        return ResponseEntity.created(URI.create("/customers/me")).build();
    }

    @GetMapping("/username/uniqueness")
    public ResponseEntity<UsernameDuplicationResponse> checkDuplicationUserName(@RequestParam String username) {
        return ResponseEntity.ok(customerService.checkDuplication(username));
    }

    @GetMapping("/me")
    public ResponseEntity<CustomerResponse> findMyInfo(@AuthenticationPrincipal Customer customer) {
        return ResponseEntity.ok(CustomerResponse.from(customer));
    }

    @PutMapping("/me/password")
    public ResponseEntity<Void> updateMyPassword(@AuthenticationPrincipal Customer customer,
                                                 @RequestBody PasswordRequest passwordRequest) {
        customerService.updatePassword(customer, passwordRequest);
        return ResponseEntity.ok().build();
    }

    @PutMapping("/me")
    public ResponseEntity<Void> updateInfo(@AuthenticationPrincipal Customer customer,
                                           @RequestBody CustomerRequest customerRequest) {
        customerService.updateInfo(customer, customerRequest);
        return ResponseEntity.ok().build();
    }

    @DeleteMapping("/me")
    public ResponseEntity<Void> deleteCustomer(@AuthenticationPrincipal Customer customer) {
        customerService.deleteCustomer(customer);
        return ResponseEntity.noContent().build();
    }
}
