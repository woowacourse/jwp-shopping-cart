package woowacourse.shoppingcart.ui;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import woowacourse.auth.support.AuthenticationPrincipal;
import woowacourse.shoppingcart.application.CustomerService;
import woowacourse.shoppingcart.domain.customer.Customer;
import woowacourse.shoppingcart.dto.*;

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

    @GetMapping("/username/duplication")
    public ResponseEntity<UserNameDuplicationResponse> checkDuplicationUserName(
            @RequestBody UserNameDuplicationRequest userNameDuplicationRequest) {
        return ResponseEntity.ok(customerService.checkDuplication(userNameDuplicationRequest));
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
    public ResponseEntity<CustomerResponse> updateInfo(@AuthenticationPrincipal Customer customer,
                                                       @RequestBody CustomerRequest customerRequest) {
        return null;
    }

    @DeleteMapping("/me")
    public ResponseEntity<Void> deleteCustomer(@AuthenticationPrincipal Customer customer) {

        return ResponseEntity.noContent().build();
    }
}
