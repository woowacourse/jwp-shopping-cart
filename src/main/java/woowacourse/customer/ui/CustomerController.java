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

import woowacourse.auth.dto.CustomerResponse;
import woowacourse.auth.support.AuthenticationPrincipal;
import woowacourse.customer.application.CustomerService;
import woowacourse.customer.domain.Customer;
import woowacourse.customer.dto.ConfirmPasswordRequest;
import woowacourse.customer.dto.SignupRequest;
import woowacourse.customer.dto.UpdateCustomerRequest;

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
        Customer customer = customerService.findByUsername(username);
        return ResponseEntity.ok().body(CustomerResponse.from(customer));
    }

    @PutMapping
    public ResponseEntity<Void> updateInfo(
        @RequestBody final UpdateCustomerRequest request,
        @AuthenticationPrincipal final String username
    ) {
        customerService.updateInfo(username, request);
        return ResponseEntity.status(HttpStatus.NO_CONTENT.value()).build();
    }

    @PostMapping("/password")
    public ResponseEntity<Void> confirmPassword(
        @RequestBody final ConfirmPasswordRequest confirmPasswordRequest,
        @AuthenticationPrincipal final String username
    ) {
        customerService.confirmPassword(username, confirmPasswordRequest.getPassword());
        return ResponseEntity.status(HttpStatus.OK.value()).build();
    }

    @PatchMapping("/password")
    public ResponseEntity<Void> updatePassword(
        @RequestBody final UpdateCustomerRequest updateCustomerRequest,
        @AuthenticationPrincipal final String username
    ) {
        customerService.updatePassword(username, updateCustomerRequest);
        return ResponseEntity.status(HttpStatus.NO_CONTENT.value()).build();
    }

    @DeleteMapping
    public ResponseEntity<Void> deleteCustomer(@AuthenticationPrincipal final String username) {
        customerService.deleteByUsername(username);
        return ResponseEntity.status(HttpStatus.NO_CONTENT.value()).build();
    }
}
