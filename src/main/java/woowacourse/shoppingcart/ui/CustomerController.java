package woowacourse.shoppingcart.ui;

import java.net.URI;
import javax.validation.Valid;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import woowacourse.auth.support.AuthenticationPrincipal;
import woowacourse.shoppingcart.dto.ChangePasswordRequest;
import woowacourse.shoppingcart.dto.CustomerResponse;
import woowacourse.shoppingcart.dto.DeleteCustomerRequest;
import woowacourse.shoppingcart.dto.SignUpRequest;
import woowacourse.shoppingcart.dto.SignUpResponse;
import woowacourse.shoppingcart.service.CustomerService;

@RestController
public class CustomerController {

    private final CustomerService customerService;

    public CustomerController(CustomerService customerService) {
        this.customerService = customerService;
    }

    @PostMapping("/users")
    public ResponseEntity<SignUpResponse> signUp(@RequestBody SignUpRequest signUpRequest) {
        return ResponseEntity.created(URI.create("/users/me")).body(customerService.signUp(signUpRequest));
    }

    @GetMapping("/users/me")
    public ResponseEntity<CustomerResponse> searchCustomerInformation(@AuthenticationPrincipal String username) {
        return ResponseEntity.ok().body(customerService.findCustomerInformation(username));
    }

    @PatchMapping("/users/me")
    public ResponseEntity changePassword(@AuthenticationPrincipal String username,
                                         @RequestBody ChangePasswordRequest changePasswordRequest) {
        customerService.changePassword(username, changePasswordRequest);
        return ResponseEntity.ok().build();
    }

    @DeleteMapping("/users/me")
    public ResponseEntity deleteCustomer(@AuthenticationPrincipal String username,
                                         @RequestBody DeleteCustomerRequest deleteCustomerRequest) {
        customerService.deleteUser(username, deleteCustomerRequest);
        return ResponseEntity.noContent().build();
    }
}
