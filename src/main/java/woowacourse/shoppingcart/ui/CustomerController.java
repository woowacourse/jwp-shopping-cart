package woowacourse.shoppingcart.ui;

import java.net.URI;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import woowacourse.auth.dto.EmailDto;
import woowacourse.auth.dto.TokenRequest;
import woowacourse.auth.dto.TokenResponse;
import woowacourse.auth.support.AuthenticationPrincipal;
import woowacourse.shoppingcart.application.CustomerService;
import woowacourse.shoppingcart.application.dto.CustomerDto;
import woowacourse.shoppingcart.application.dto.ModifiedCustomerDto;
import woowacourse.shoppingcart.application.dto.SignInDto;
import woowacourse.shoppingcart.domain.customer.Email;
import woowacourse.shoppingcart.dto.CustomerRequest;
import woowacourse.shoppingcart.dto.ModifiedCustomerRequest;
import woowacourse.shoppingcart.dto.SignUpRequest;
import woowacourse.shoppingcart.dto.CustomerResponse;

@RestController
@RequestMapping("/api")
public class CustomerController {

    private final CustomerService customerService;

    public CustomerController(CustomerService customerService) {
        this.customerService = customerService;
    }

    @PostMapping("/customers")
    public ResponseEntity<Void> createCustomers(@RequestBody final SignUpRequest request) {
        final Long customerId = customerService.createCustomer(CustomerDto.fromCustomerRequest(request));
        return ResponseEntity.created(URI.create("/api/customers/" + customerId)).build();
    }

    @PostMapping("/customer/authentication/sign-in")
    public ResponseEntity<TokenResponse> signIn(@RequestBody final TokenRequest tokenRequest) {
        final TokenResponse tokenResponse = customerService.signIn(SignInDto.fromTokenRequest(tokenRequest));
        return ResponseEntity.ok().body(tokenResponse);
    }

    @GetMapping("/customers/{customerId}")
    public ResponseEntity<CustomerResponse> findCustomerInformation(
            @AuthenticationPrincipal final EmailDto email, @PathVariable String customerId) {
        CustomerResponse response = customerService.findCustomerByEmail(email);
        return ResponseEntity.ok().body(response);
    }

    @PutMapping("/customers/{customerId}")
    public ResponseEntity<Void> updateCustomerInformation(@AuthenticationPrincipal final EmailDto emailDto,
                                                          @RequestBody final ModifiedCustomerRequest request) {
       customerService.updateCustomer(ModifiedCustomerDto.fromModifiedCustomerRequest(request));
       return ResponseEntity.noContent().build();
    }
}
