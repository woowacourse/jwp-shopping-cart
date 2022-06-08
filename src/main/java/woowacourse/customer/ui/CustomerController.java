package woowacourse.customer.ui;

import javax.validation.Valid;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import woowacourse.auth.support.AuthenticationPrincipal;
import woowacourse.customer.application.CustomerService;
import woowacourse.customer.dto.CustomerResponse;
import woowacourse.customer.dto.PasswordConfirmRequest;
import woowacourse.customer.dto.SignupRequest;
import woowacourse.customer.dto.UpdateCustomerRequest;
import woowacourse.customer.dto.UpdatePasswordRequest;

@RequestMapping("/api/customers")
@RestController
public class CustomerController {

    private final CustomerService customerService;

    public CustomerController(final CustomerService customerService) {
        this.customerService = customerService;
    }

    @PostMapping("/signup")
    public ResponseEntity<Void> signup(@Valid @RequestBody final SignupRequest signupRequest) {
        customerService.save(signupRequest);
        return ResponseEntity.status(HttpStatus.CREATED).build();
    }

    @GetMapping
    public ResponseEntity<CustomerResponse> findCustomerInfo(@AuthenticationPrincipal final String username) {
        return ResponseEntity.ok().body(customerService.findCustomerByUsername(username));
    }

    @PutMapping
    public ResponseEntity<Void> updateInfo(
        @Valid @RequestBody final UpdateCustomerRequest updateCustomerRequest,
        @AuthenticationPrincipal final String username
    ) {
        customerService.updateInfo(username, updateCustomerRequest);
        return ResponseEntity.status(HttpStatus.NO_CONTENT.value()).build();
    }

    @PostMapping("/password")
    public ResponseEntity<Void> confirmPassword(
        @Valid @RequestBody final PasswordConfirmRequest passwordConfirmRequest,
        @AuthenticationPrincipal final String username
    ) {
        customerService.confirmPassword(username, passwordConfirmRequest.getPassword());
        return ResponseEntity.status(HttpStatus.OK.value()).build();
    }

    @PatchMapping("/password")
    public ResponseEntity<Void> updatePassword(
        @Valid @RequestBody final UpdatePasswordRequest updatePasswordRequest,
        @AuthenticationPrincipal final String username
    ) {
        customerService.updatePassword(username, updatePasswordRequest);
        return ResponseEntity.status(HttpStatus.NO_CONTENT.value()).build();
    }

    @DeleteMapping
    public ResponseEntity<Void> deleteCustomer(@AuthenticationPrincipal final String username) {
        customerService.deleteCustomerByUsername(username);
        return ResponseEntity.status(HttpStatus.NO_CONTENT.value()).build();
    }
}
