package woowacourse.shoppingcart.ui;

import java.net.URI;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import woowacourse.auth.support.AuthenticationPrincipal;
import woowacourse.shoppingcart.application.CustomerService;
import woowacourse.shoppingcart.dto.request.SignUpRequest;
import woowacourse.shoppingcart.dto.request.UniqueUsernameRequest;
import woowacourse.shoppingcart.dto.request.UpdateMeRequest;
import woowacourse.shoppingcart.dto.request.UpdatePasswordRequest;
import woowacourse.shoppingcart.dto.response.GetMeResponse;
import woowacourse.shoppingcart.dto.response.UniqueUsernameResponse;

@RestController
@RequestMapping("/customers")
public class CustomerController {

    private final CustomerService customerService;

    public CustomerController(CustomerService customerService) {
        this.customerService = customerService;
    }

    @PostMapping
    public ResponseEntity<Void> signUp(@RequestBody SignUpRequest request) {
        Long customerId = customerService.signUp(request);
        URI location = URI.create("/customers/" + customerId);
        return ResponseEntity.created(location).build();
    }

    @GetMapping("/me")
    public ResponseEntity<GetMeResponse> getMe(@AuthenticationPrincipal Long customerId) {
        GetMeResponse currentCustomer = customerService.getMe(customerId);
        return ResponseEntity.ok(currentCustomer);
    }

    @PutMapping("/me")
    public ResponseEntity<Void> updateMe(@RequestBody UpdateMeRequest updateMeRequest,
                                         @AuthenticationPrincipal Long customerId) {
        customerService.updateMe(customerId, updateMeRequest);
        return ResponseEntity.ok().build();
    }

    @DeleteMapping("/me")
    public ResponseEntity<Void> deleteMe(@AuthenticationPrincipal Long customerId) {
        customerService.deleteMe(customerId);
        return ResponseEntity.noContent().build();
    }

    @PutMapping("/me/password")
    public ResponseEntity<Void> updatePassword(@RequestBody UpdatePasswordRequest updatePasswordRequest,
                                               @AuthenticationPrincipal Long customerId) {
        customerService.updatePassword(customerId, updatePasswordRequest);
        return ResponseEntity.ok().build();
    }

    @GetMapping("/username/duplication")
    public ResponseEntity<UniqueUsernameResponse> checkUniqueUsername(@RequestBody UniqueUsernameRequest request) {
        UniqueUsernameResponse response = customerService.checkUniqueUsername(request);
        return ResponseEntity.ok(response);
    }
}
