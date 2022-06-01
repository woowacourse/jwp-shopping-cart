package woowacourse.shoppingcart.ui;

import javax.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import woowacourse.auth.support.AuthenticationPrincipal;
import woowacourse.shoppingcart.application.CustomerService;
import woowacourse.shoppingcart.domain.Customer;
import woowacourse.shoppingcart.dto.CustomerInfoRequest;
import woowacourse.shoppingcart.dto.CustomerRequest;
import woowacourse.shoppingcart.dto.CustomerResponse;
import woowacourse.shoppingcart.dto.EmailDuplicateCheckResponse;
import woowacourse.shoppingcart.dto.PasswordRequest;

@Controller
@RequestMapping("/api/members")
public class CustomerController {

    private final CustomerService customerService;

    public CustomerController(final CustomerService customerService) {
        this.customerService = customerService;
    }

    @GetMapping
    public ResponseEntity<EmailDuplicateCheckResponse> checkDuplicateEmail(@RequestParam final String email) {
        final EmailDuplicateCheckResponse emailDuplicateCheckResponse =
                new EmailDuplicateCheckResponse(customerService.isDistinctEmail(email));
        return ResponseEntity.ok().body(emailDuplicateCheckResponse);
    }

    @PostMapping
    public ResponseEntity<CustomerResponse> signUp(@RequestBody @Valid final CustomerRequest customerRequest) {
        final Customer customer = customerService.signUp(customerRequest);
        final CustomerResponse customerResponse = new CustomerResponse(customer.getEmail(), customer.getNickname());
        return ResponseEntity.status(HttpStatus.CREATED).body(customerResponse);
    }

    @PostMapping("/auth/password-check")
    public ResponseEntity<Void> checkPassword(@AuthenticationPrincipal final String email,
                                              @RequestBody @Valid final PasswordRequest passwordRequest) {
        customerService.checkPassword(email, passwordRequest);
        return ResponseEntity.noContent().build();
    }

    @GetMapping("/auth/me")
    public ResponseEntity<CustomerResponse> findCustomerInfo(@AuthenticationPrincipal final String email) {
        final Customer customer = customerService.findCustomerByEmail(email);
        final CustomerResponse customerResponse = new CustomerResponse(customer.getEmail(), customer.getNickname());
        return ResponseEntity.ok().body(customerResponse);
    }

    @PatchMapping("/auth/me")
    public ResponseEntity<Void> updateCustomerInfo(@AuthenticationPrincipal final String email,
                                                   @RequestBody @Valid final CustomerInfoRequest customerInfoRequest) {
        customerService.updateInfo(email, customerInfoRequest);
        return ResponseEntity.noContent().build();
    }

    @PatchMapping("/auth/password")
    public ResponseEntity<Void> updateCustomerPassword(@AuthenticationPrincipal final String email,
                                                       @RequestBody @Valid final PasswordRequest passwordRequest) {
        customerService.updatePassword(email, passwordRequest);
        return ResponseEntity.noContent().build();
    }
}
