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
import woowacourse.auth.dto.CustomerResponse;
import woowacourse.auth.dto.DeleteCustomerRequest;
import woowacourse.auth.dto.SignUpRequest;
import woowacourse.auth.dto.SignUpResponse;
import woowacourse.auth.support.AuthenticationPrincipal;
import woowacourse.auth.utils.EmailUtil;
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
        return ResponseEntity.created(URI.create("/users/" + EmailUtil.getIdentifier(signUpresponse.getEmail())))
                .body(signUpresponse);
    }

    @GetMapping("/{headOfEmail}")
    public ResponseEntity<CustomerResponse> getMe(@AuthenticationPrincipal String email,
                                                  @PathVariable String headOfEmail) {
        return ResponseEntity.ok().body(customerService.findMe(email));
    }

    @PatchMapping("/{headOfEmail}")
    public ResponseEntity<CustomerResponse> updateMe(@AuthenticationPrincipal String email,
                                                     @PathVariable String headOfEmail,
                                                     @RequestBody UpdatePasswordRequest updatePasswordRequest) {
        customerService.updateMe(email, updatePasswordRequest);
        return ResponseEntity.ok().build();
    }

    @DeleteMapping("/{username}")
    public ResponseEntity<Void> deleteMe(@AuthenticationPrincipal String email,
                                         @PathVariable String username,
                                         @RequestBody DeleteCustomerRequest deleteCustomerRequest) {
        customerService.deleteMe(username, deleteCustomerRequest);
        return ResponseEntity.noContent().build();
    }
}
