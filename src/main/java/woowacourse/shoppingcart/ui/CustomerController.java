package woowacourse.shoppingcart.ui;

import java.net.URI;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import woowacourse.shoppingcart.application.CustomerService;
import woowacourse.shoppingcart.dto.GetMeResponse;
import woowacourse.shoppingcart.dto.SignUpRequest;
import woowacourse.shoppingcart.dto.UpdateMeRequest;
import woowacourse.shoppingcart.dto.UpdatePasswordRequest;

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
    public ResponseEntity<GetMeResponse> getMe() {
        GetMeResponse currentCustomer = customerService.getMe(1L);
        return ResponseEntity.ok(currentCustomer);
    }

    @PutMapping("/me")
    public ResponseEntity<Void> updateMe(@RequestBody UpdateMeRequest request) {
        customerService.updateMe(1L, request);
        return ResponseEntity.ok().build();
    }

    @PatchMapping("/me/password")
    public ResponseEntity<Void> updatePassword(@RequestBody UpdatePasswordRequest request) {
        customerService.updatePassword(1L, request);
        return ResponseEntity.ok().build();
    }

    @DeleteMapping("/me")
    public ResponseEntity<Void> deleteMe() {
        customerService.deleteMe(1L);
        return ResponseEntity.noContent().build();
    }
}
