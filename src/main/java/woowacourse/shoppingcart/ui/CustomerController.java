package woowacourse.shoppingcart.ui;

import java.net.URI;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import woowacourse.auth.application.AuthService;
import woowacourse.auth.dto.CustomerResponse;
import woowacourse.auth.dto.DeleteCustomerRequest;
import woowacourse.auth.dto.SignUpRequest;
import woowacourse.auth.dto.SignUpResponse;
import woowacourse.auth.support.AuthenticationPrincipal;
import woowacourse.shoppingcart.application.CustomerService;
import woowacourse.shoppingcart.dto.UpdatePasswordRequest;

@RestController
@RequestMapping("/users")
public class CustomerController {

    private final CustomerService customerService;

    public CustomerController(CustomerService customerService) {
        this.customerService = customerService;
    }

    @PostMapping
    public ResponseEntity<SignUpResponse> signUp(@RequestBody SignUpRequest signUpRequest) {
        SignUpResponse signUpresponse = customerService.addCustomer(signUpRequest);
        return ResponseEntity.created(URI.create("/users"))
                .body(signUpresponse);
    }

    @GetMapping("/me")
    public ResponseEntity<CustomerResponse> getMe(@AuthenticationPrincipal String userNameByToken) {
        return ResponseEntity.ok().body(customerService.findMe(userNameByToken));
    }

    @PatchMapping
    public ResponseEntity<CustomerResponse> updateMe(@AuthenticationPrincipal String userNameByToken,
                                                     @RequestBody UpdatePasswordRequest updatePasswordRequest) {
        customerService.updateMe(userNameByToken, updatePasswordRequest);
        return ResponseEntity.ok().build();
    }

    @DeleteMapping
    public ResponseEntity<Void> deleteMe(@AuthenticationPrincipal String userNameByToken,
                                         @RequestBody DeleteCustomerRequest deleteCustomerRequest) {
        customerService.deleteMe(userNameByToken, deleteCustomerRequest);
        return ResponseEntity.noContent().build();
    }
}
