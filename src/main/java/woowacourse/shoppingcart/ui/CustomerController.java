package woowacourse.shoppingcart.ui;

import java.net.URI;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import woowacourse.auth.support.AuthenticationPrincipal;
import woowacourse.shoppingcart.application.CustomerService;
import woowacourse.shoppingcart.application.dto.request.CustomerIdentificationRequest;
import woowacourse.shoppingcart.application.dto.request.CustomerUpdatePasswordRequest;
import woowacourse.shoppingcart.application.dto.request.CustomerUpdateRequest;
import woowacourse.shoppingcart.application.dto.request.PasswordRequest;
import woowacourse.shoppingcart.application.dto.response.CustomerResponse;
import woowacourse.shoppingcart.application.dto.request.SignUpRequest;

@RestController
public class CustomerController {

    private final CustomerService customerService;

    public CustomerController(final CustomerService customerService) {
        this.customerService = customerService;
    }

    @PostMapping("/customers/signUp")
    public ResponseEntity<Void> signUp(@RequestBody SignUpRequest signUpRequest) {
        Long customerId = customerService.signUp(signUpRequest);
        return ResponseEntity.created(URI.create("/customers/" + customerId)).build();
    }

    @GetMapping("/auth/customers/profile")
    public ResponseEntity<CustomerResponse> findByCustomerId(@AuthenticationPrincipal CustomerIdentificationRequest customerIdentificationRequest) {
        CustomerResponse customerResponse = customerService.findByCustomerId(customerIdentificationRequest);
        return ResponseEntity.ok().body(customerResponse);
    }

    @PatchMapping("/auth/customers/profile")
    public ResponseEntity<Void> update(@AuthenticationPrincipal CustomerIdentificationRequest customerIdentificationRequest,
                                       @RequestBody CustomerUpdateRequest customerUpdateRequest) {
        customerService.update(customerIdentificationRequest, customerUpdateRequest);
        return ResponseEntity.ok().build();
    }

    @PatchMapping("/auth/customers/profile/password")
    public ResponseEntity<Void> updatePassword(@AuthenticationPrincipal CustomerIdentificationRequest customerIdentificationRequest,
                                               @RequestBody CustomerUpdatePasswordRequest customerUpdatePasswordRequest) {
        customerService.updatePassword(customerIdentificationRequest, customerUpdatePasswordRequest);
        return ResponseEntity.ok().build();
    }

    @DeleteMapping("/auth/customers/profile")
    public ResponseEntity<Void> withdraw(@AuthenticationPrincipal CustomerIdentificationRequest customerIdentificationRequest,
                                         @RequestBody PasswordRequest passwordRequest) {
        customerService.withdraw(customerIdentificationRequest, passwordRequest);
        return ResponseEntity.noContent().build();
    }

    @PostMapping("/auth/customers/match/password")
    public ResponseEntity<Void> matchPassword(@AuthenticationPrincipal CustomerIdentificationRequest customerIdentificationRequest,
                                                      @RequestBody PasswordRequest passwordRequest) {
        customerService.matchPassword(customerIdentificationRequest, passwordRequest);
        return ResponseEntity.ok().build();
    }
}
