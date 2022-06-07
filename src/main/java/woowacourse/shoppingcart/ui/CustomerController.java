package woowacourse.shoppingcart.ui;

import javax.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import woowacourse.auth.dto.TokenRequest;
import woowacourse.auth.support.AuthenticationPrincipal;
import woowacourse.shoppingcart.application.CustomerService;
import woowacourse.shoppingcart.dto.customer.CustomerResponse;
import woowacourse.shoppingcart.dto.customer.CustomerUpdatePasswordRequest;
import woowacourse.shoppingcart.dto.customer.CustomerUpdateProfileRequest;

@RestController
@RequestMapping("/auth/customers/profile")
public class CustomerController {

    private final CustomerService customerService;

    public CustomerController(final CustomerService customerService) {
        this.customerService = customerService;
    }

    @GetMapping
    public ResponseEntity<CustomerResponse> findByCustomerId(
            @AuthenticationPrincipal TokenRequest tokenRequest) {
        CustomerResponse customerResponse = customerService.findProfile(tokenRequest);
        return ResponseEntity.ok().body(customerResponse);
    }

    @DeleteMapping
    public ResponseEntity<Void> withdraw(@AuthenticationPrincipal TokenRequest tokenRequest) {
        customerService.withdraw(tokenRequest);
        return ResponseEntity.noContent().build();
    }

    @PatchMapping
    public ResponseEntity<Void> update(@AuthenticationPrincipal TokenRequest tokenRequest,
                                       @Valid @RequestBody CustomerUpdateProfileRequest customerUpdateProfileRequest) {
        customerService.updateProfile(tokenRequest, customerUpdateProfileRequest);
        return ResponseEntity.ok().build();
    }

    @PatchMapping("/password")
    public ResponseEntity<Void> updatePassword(@AuthenticationPrincipal TokenRequest tokenRequest,
                                               @Valid @RequestBody CustomerUpdatePasswordRequest customerUpdatePasswordRequest) {
        customerService.updatePassword(tokenRequest, customerUpdatePasswordRequest);
        return ResponseEntity.ok().build();
    }
}
