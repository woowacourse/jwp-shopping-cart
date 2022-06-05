package woowacourse.shoppingcart.ui;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import woowacourse.auth.support.AuthenticationPrincipal;
import woowacourse.shoppingcart.application.CustomerService;
import woowacourse.shoppingcart.dto.ChangeCustomerRequest;
import woowacourse.shoppingcart.dto.ChangePasswordRequest;
import woowacourse.shoppingcart.dto.CustomerCreateRequest;
import woowacourse.shoppingcart.dto.CustomerResponse;

import java.net.URI;
import woowacourse.shoppingcart.dto.LoginCustomer;

@RestController
@RequestMapping("/api/customers")
public class CustomerController {

    private final CustomerService customerService;

    public CustomerController(CustomerService customerService) {
        this.customerService = customerService;
    }

    @PostMapping
    public ResponseEntity<Void> createCustomer(@RequestBody CustomerCreateRequest request) {
        Long customerId = customerService.createCustomer(request);
        return ResponseEntity.created(URI.create("/api/customers/" + customerId)).build();
    }

    @GetMapping("/me")
    public ResponseEntity<CustomerResponse> showCustomer(@AuthenticationPrincipal LoginCustomer loginCustomer) {
        CustomerResponse customerResponse = customerService.findCustomerByEmail(
          loginCustomer.getEmail());
        return ResponseEntity.ok(customerResponse);
    }

    @DeleteMapping
    public ResponseEntity<Void> deleteCustomer(@AuthenticationPrincipal LoginCustomer loginCustomer) {
        customerService.deleteCustomer(loginCustomer.getEmail());
        return ResponseEntity.noContent().build();
    }

    @PatchMapping("/password")
    public ResponseEntity<Void> changePassword(@AuthenticationPrincipal LoginCustomer loginCustomer, @RequestBody ChangePasswordRequest request) {
        customerService.changePassword(loginCustomer.getEmail(), request);
        return ResponseEntity.ok().build();
    }

    @PatchMapping
    public ResponseEntity<Void> changeNickname(@AuthenticationPrincipal LoginCustomer loginCustomer, @RequestBody ChangeCustomerRequest request) {
        customerService.changeNickname(loginCustomer.getEmail(), request);
        return ResponseEntity.ok().build();
    }
}
