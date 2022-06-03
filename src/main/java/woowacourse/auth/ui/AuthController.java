package woowacourse.auth.ui;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import woowacourse.auth.support.AuthenticationPrincipal;
import woowacourse.shoppingcart.application.CustomerService;
import woowacourse.shoppingcart.application.dto.response.CustomerResponse;
import woowacourse.shoppingcart.application.dto.request.CustomerUpdatePasswordRequest;
import woowacourse.shoppingcart.application.dto.request.CustomerUpdateRequest;
import woowacourse.shoppingcart.application.dto.request.TokenRequest;

@RestController
@RequestMapping("/auth/customers/profile")
public class AuthController {

    private final CustomerService customerService;

    public AuthController(final CustomerService customerService) {
        this.customerService = customerService;
    }

    @GetMapping
    public ResponseEntity<CustomerResponse> findByCustomerId(@AuthenticationPrincipal TokenRequest tokenRequest) {
        CustomerResponse customerResponse = customerService.findByCustomerId(tokenRequest);
        return ResponseEntity.ok().body(customerResponse);
    }

    @DeleteMapping
    public ResponseEntity<Void> withdraw(@AuthenticationPrincipal TokenRequest tokenRequest) {
        customerService.withdraw(tokenRequest);
        return ResponseEntity.noContent().build();
    }

    @PatchMapping
    public ResponseEntity<Void> update(@AuthenticationPrincipal TokenRequest tokenRequest,
                                                   @RequestBody CustomerUpdateRequest customerUpdateRequest) {
        customerService.update(tokenRequest, customerUpdateRequest);
        return ResponseEntity.ok().build();
    }

    @PatchMapping("/password")
    public ResponseEntity<Void> updatePassword(@AuthenticationPrincipal TokenRequest tokenRequest,
                                                   @RequestBody CustomerUpdatePasswordRequest customerUpdatePasswordRequest) {
        customerService.updatePassword(tokenRequest, customerUpdatePasswordRequest);
        return ResponseEntity.ok().build();
    }
}
