package woowacourse.shoppingcart.ui;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import woowacourse.auth.support.AuthenticationPrincipal;
import woowacourse.auth.ui.dto.LoginCustomer;
import woowacourse.shoppingcart.application.CustomerService;
import woowacourse.shoppingcart.application.dto.CustomerResponse;
import woowacourse.shoppingcart.ui.dto.CustomerChangePasswordRequest;
import woowacourse.shoppingcart.ui.dto.CustomerChangeRequest;
import woowacourse.shoppingcart.ui.dto.CustomerSignUpRequest;

import javax.validation.Valid;

@RestController
@RequestMapping("/api/customers")
public class CustomerController {

    private final CustomerService customerService;

    public CustomerController(final CustomerService customerService) {
        this.customerService = customerService;
    }

    @PostMapping
    public ResponseEntity<Void> saveCustomer(@RequestBody @Valid final CustomerSignUpRequest customerSignUpRequest) {
        customerService.save(customerSignUpRequest.toServiceRequest());
        return ResponseEntity.ok().build();
    }

    @GetMapping("/me")
    public ResponseEntity<CustomerResponse> showCustomer(@AuthenticationPrincipal @Valid final LoginCustomer loginCustomer) {
        CustomerResponse customerResponse = customerService.findById(loginCustomer.getId());
        return ResponseEntity.ok(customerResponse);
    }

    @PatchMapping
    public ResponseEntity<Void> updateCustomer(@AuthenticationPrincipal @Valid final LoginCustomer loginCustomer,
                                               @RequestBody @Valid final CustomerChangeRequest customerChangeRequest) {
        customerService.update(loginCustomer.getId(), customerChangeRequest.toServiceRequest());
        return ResponseEntity.noContent().build();
    }

    @PatchMapping("/password")
    public ResponseEntity<Void> updateCustomer(@AuthenticationPrincipal @Valid final LoginCustomer loginCustomer,
                                               @RequestBody @Valid final CustomerChangePasswordRequest customerChangePasswordRequest) {
        customerService.updatePassword(loginCustomer.getId(), customerChangePasswordRequest.toServiceRequest());
        return ResponseEntity.noContent().build();
    }

    @DeleteMapping
    public ResponseEntity<Void> deleteCustomer(@AuthenticationPrincipal @Valid final LoginCustomer loginCustomer) {
        customerService.delete(loginCustomer.getId());
        return ResponseEntity.noContent().build();
    }
}
