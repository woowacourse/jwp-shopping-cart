package woowacourse.shoppingcart.ui;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import woowacourse.auth.support.AuthenticationPrincipal;
import woowacourse.shoppingcart.application.CustomerService;
import woowacourse.shoppingcart.dto.*;
import javax.validation.Valid;

@RestController
@RequestMapping("/api/customer")
public class CustomerController {

    private final CustomerService customerService;

    public CustomerController(final CustomerService customerService) {
        this.customerService = customerService;
    }

    @PostMapping
    public ResponseEntity<Void> register(@RequestBody @Valid final CustomerRegisterRequest request) {
        customerService.save(request);
        return ResponseEntity.status(HttpStatus.CREATED).build();
    }

    @GetMapping
    public ResponseEntity<CustomerDetailResponse> showMyDetail(@AuthenticationPrincipal final Long id) {
        final CustomerDetailResponse response = customerService.findById(id);
        return ResponseEntity.ok(response);
    }

    @PutMapping("/profile")
    public ResponseEntity<Void> updateProfile(@AuthenticationPrincipal final Long id,
                                              @RequestBody @Valid final CustomerProfileUpdateRequest request) {
        customerService.updateName(id, request);
        return ResponseEntity.noContent().build();
    }

    @PutMapping("/password")
    public ResponseEntity<Void> updatePassword(@AuthenticationPrincipal final Long id,
                                               @RequestBody @Valid final CustomerPasswordUpdateRequest request) {
        customerService.updatePassword(id, request);
        return ResponseEntity.noContent().build();
    }

    @DeleteMapping
    public ResponseEntity<Void> delete(@AuthenticationPrincipal final Long id,
                                       @RequestBody @Valid final CustomerDeleteRequest request) {
        customerService.delete(id, request);
        return ResponseEntity.noContent().build();
    }
}
