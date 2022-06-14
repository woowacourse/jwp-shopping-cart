package woowacourse.shoppingcart.ui;

import java.net.URI;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import woowacourse.auth.support.AuthenticationPrincipal;
import woowacourse.shoppingcart.application.CustomerService;
import woowacourse.shoppingcart.dto.request.SignUpRequest;
import woowacourse.shoppingcart.dto.request.UpdateMeRequest;
import woowacourse.shoppingcart.dto.request.UpdatePasswordRequest;
import woowacourse.shoppingcart.dto.response.MeResponse;
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
        customerService.signUp(request);
        URI location = URI.create("/customers/me");
        return ResponseEntity.created(location).build();
    }

    @GetMapping("/me")
    public ResponseEntity<MeResponse> getMe(@AuthenticationPrincipal Long id) {
        MeResponse currentCustomer = customerService.getMe(id);
        return ResponseEntity.ok(currentCustomer);
    }

    @PutMapping("/me")
    public ResponseEntity<Void> updateMe(@AuthenticationPrincipal Long id
            , @RequestBody UpdateMeRequest updateMeRequest) {
        customerService.updateMe(id, updateMeRequest);
        return ResponseEntity.ok().build();
    }

    @DeleteMapping("/me")
    public ResponseEntity<Void> deleteMe(@AuthenticationPrincipal Long id) {
        customerService.deleteMe(id);
        return ResponseEntity.noContent().build();
    }

    @PutMapping("/me/password")
    public ResponseEntity<Void> updatePassword(@AuthenticationPrincipal Long id
            , @RequestBody UpdatePasswordRequest updatePasswordRequest) {
        customerService.updatePassword(id, updatePasswordRequest);
        return ResponseEntity.ok().build();
    }

    @GetMapping("/username/uniqueness")
    public ResponseEntity<UniqueUsernameResponse> checkUniqueUsername(@RequestParam String username) {
        UniqueUsernameResponse response = customerService.checkUniqueUsername(username);
        return ResponseEntity.ok(response);
    }
}
