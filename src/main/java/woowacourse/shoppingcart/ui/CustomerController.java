package woowacourse.shoppingcart.ui;

import javax.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import woowacourse.shoppingcart.application.dto.CustomerResponse;
import woowacourse.auth.support.AuthenticationPrincipal;
import woowacourse.auth.ui.LoginCustomer;
import woowacourse.shoppingcart.application.CustomerService;
import woowacourse.shoppingcart.ui.dto.CustomerChangePasswordRequest;
import woowacourse.shoppingcart.ui.dto.CustomerChangeRequest;
import woowacourse.shoppingcart.ui.dto.CustomerSignUpRequest;

@RestController
@RequestMapping("/api/customers")
public class CustomerController {

    private final CustomerService customerService;

    public CustomerController(CustomerService customerService) {
        this.customerService = customerService;
    }

    @PostMapping
    public ResponseEntity<Void> saveCustomer(@RequestBody @Valid CustomerSignUpRequest customerSignUpRequest) {
        customerService.save(customerSignUpRequest.toServiceRequest());
        return ResponseEntity.ok().build();
    }

    @GetMapping("/me")
    public ResponseEntity<CustomerResponse> showCustomer(@AuthenticationPrincipal @Valid LoginCustomer loginCustomer) {
        CustomerResponse customerResponse = customerService.findById(loginCustomer.getId());
        return ResponseEntity.ok(customerResponse);
    }

    @PatchMapping
    public ResponseEntity<Void> updateCustomer(@AuthenticationPrincipal @Valid LoginCustomer loginCustomer,
                                               @RequestBody @Valid CustomerChangeRequest customerChangeRequest) {
        customerService.update(loginCustomer.getId(), customerChangeRequest.toServiceRequest());
        return ResponseEntity.noContent().build();
    }

    @PatchMapping("/password")
    public ResponseEntity<Void> updateCustomer(@AuthenticationPrincipal @Valid LoginCustomer loginCustomer,
                                               @RequestBody @Valid CustomerChangePasswordRequest customerChangePasswordRequest) {
        customerService.updatePassword(loginCustomer.getId(), customerChangePasswordRequest.toServiceRequest());
        return ResponseEntity.noContent().build();
    }

    @DeleteMapping
    public ResponseEntity<Void> deleteCustomer(@AuthenticationPrincipal @Valid LoginCustomer loginCustomer) {
        customerService.delete(loginCustomer.getId());
        return ResponseEntity.noContent().build();
    }
}
