package woowacourse.shoppingcart.ui;

import java.net.URI;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import woowacourse.auth.domain.User;
import woowacourse.auth.support.AuthenticatedUser;
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
    public ResponseEntity<Void> signUp(@Validated @RequestBody SignUpRequest request) {
        Long customerId = customerService.createCustomer(request);
        URI location = URI.create("/customers/" + customerId);
        return ResponseEntity.created(location).build();
    }

    @GetMapping("/me")
    public ResponseEntity<GetMeResponse> getMe(@AuthenticatedUser User authUser) {
        GetMeResponse currentCustomer = customerService.getCustomer(authUser);
        return ResponseEntity.ok(currentCustomer);
    }

    @PutMapping("/me")
    public ResponseEntity<Void> updateMe(@AuthenticatedUser User authUser,
                                         @Validated @RequestBody UpdateMeRequest updateMeRequest) {
        customerService.updateNicknameAndAge(authUser, updateMeRequest);
        return ResponseEntity.ok().build();
    }

    @DeleteMapping("/me")
    public ResponseEntity<Void> deleteMe(@AuthenticatedUser User authUser) {
        customerService.deleteCustomer(authUser);
        return ResponseEntity.noContent().build();
    }

    @PutMapping("/me/password")
    public ResponseEntity<Void> updatePassword(@AuthenticatedUser User authUser,
                                               @Validated @RequestBody UpdatePasswordRequest updatePasswordRequest) {
        customerService.updatePassword(authUser, updatePasswordRequest);
        return ResponseEntity.ok().build();
    }

    @GetMapping("/username/duplication")
    public ResponseEntity<UniqueUsernameResponse> checkUniqueUsername(@Validated @RequestBody UniqueUsernameRequest request) {
        UniqueUsernameResponse response = customerService.checkUniqueUsername(request.getUsername());
        return ResponseEntity.ok(response);
    }
}
