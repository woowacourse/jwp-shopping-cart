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
    private final AuthService authService;

    public CustomerController(CustomerService customerService, AuthService authService) {
        this.customerService = customerService;
        this.authService = authService;
    }

    @PostMapping
    public ResponseEntity<SignUpResponse> signUp(@RequestBody SignUpRequest signUpRequest) {
        SignUpResponse signUpresponse = customerService.addCustomer(signUpRequest);
        return ResponseEntity.created(URI.create("/users/" + signUpRequest.getUsername()))
                .body(signUpresponse);
    }

    @GetMapping("/{username}")
    public ResponseEntity<CustomerResponse> getMe(@AuthenticationPrincipal String userNameByToken,
                                                  @PathVariable String username) {
        authService.validateUser(username, userNameByToken);
        return ResponseEntity.ok().body(customerService.findMe(username));
    }

    @PatchMapping("/{username}")
    public ResponseEntity<CustomerResponse> updateMe(@AuthenticationPrincipal String userNameByToken,
                                                     @PathVariable String username,
                                                     @RequestBody UpdatePasswordRequest updatePasswordRequest) {
        authService.validateUser(username, userNameByToken);
        customerService.updateMe(username, updatePasswordRequest);
        return ResponseEntity.ok().build();
    }

    @DeleteMapping("/{username}")
    public ResponseEntity<Void> deleteMe(@AuthenticationPrincipal String userNameByToken,
                                         @PathVariable String username,
                                         @RequestBody DeleteCustomerRequest deleteCustomerRequest) {
        authService.validateUser(username, userNameByToken);
        customerService.deleteMe(username, deleteCustomerRequest);
        return ResponseEntity.noContent().build();
    }
}
