package woowacourse.shoppingcart.ui;

import java.net.URI;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import woowacourse.auth.dto.EmailAuthentication;
import woowacourse.auth.support.AuthenticationPrincipal;
import woowacourse.shoppingcart.application.CustomerService;
import woowacourse.shoppingcart.application.dto.CustomerDto;
import woowacourse.shoppingcart.application.dto.EmailDto;
import woowacourse.shoppingcart.application.dto.ModifiedCustomerDto;
import woowacourse.shoppingcart.dto.request.ModifiedCustomerRequest;
import woowacourse.shoppingcart.dto.request.SignUpRequest;
import woowacourse.shoppingcart.dto.response.CustomerResponse;
import woowacourse.shoppingcart.dto.response.DuplicateEmailResponse;

@RestController
@RequestMapping("/api")
public class CustomerController {

    private final CustomerService customerService;

    public CustomerController(CustomerService customerService) {
        this.customerService = customerService;
    }

    @GetMapping("/validation")
    public ResponseEntity<DuplicateEmailResponse> checkEmailDuplication(@RequestParam final String email) {
        final boolean isDuplicated = customerService.checkEmailDuplication(new EmailDto(email));
        return ResponseEntity.ok().body(new DuplicateEmailResponse(isDuplicated));
    }

    @PostMapping("/customers")
    public ResponseEntity<Void> createCustomers(@RequestBody final SignUpRequest request) {
        final Long customerId = customerService.createCustomer(CustomerDto.fromCustomerRequest(request));
        return ResponseEntity.created(URI.create("/api/customers/" + customerId)).build();
    }

    @GetMapping("/customers/{customerId}")
    public ResponseEntity<CustomerResponse> findCustomerInformation(
            @AuthenticationPrincipal final EmailAuthentication email, @PathVariable Long customerId) {
        CustomerResponse response = customerService.findCustomerByEmail(email);
        return ResponseEntity.ok().body(response);
    }

    @PutMapping("/customers/{customerId}")
    public ResponseEntity<Void> updateCustomerInformation(@AuthenticationPrincipal final EmailAuthentication emailDto,
                                                          @PathVariable Long customerId,
                                                          @RequestBody final ModifiedCustomerRequest request) {
        customerService.updateCustomer(ModifiedCustomerDto.fromModifiedCustomerRequest(request));
        return ResponseEntity.noContent().build();
    }

    @DeleteMapping("/customers/{customerId}")
    public ResponseEntity<Void> deleteCustomer(@AuthenticationPrincipal final EmailAuthentication emailDto,
                                               @PathVariable Long customerId) {
        customerService.deleteCustomer(emailDto);
        return ResponseEntity.noContent().build();
    }
}
