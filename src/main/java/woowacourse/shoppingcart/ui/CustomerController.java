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
import woowacourse.shoppingcart.application.dto.CustomerDto;
import woowacourse.shoppingcart.application.dto.ModifiedCustomerDto;
import woowacourse.shoppingcart.dto.CustomerResponse;
import woowacourse.shoppingcart.dto.ModifiedCustomerRequest;
import woowacourse.shoppingcart.dto.SignUpRequest;

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

    @GetMapping("/customers")
    public ResponseEntity<CustomerResponse> findCustomerInformation(
            @AuthenticationPrincipal Long customerId) {
        CustomerResponse response = customerService.findCustomerById(customerId);
        return ResponseEntity.ok(response);
    }

    @PutMapping("/customers")
    public ResponseEntity<Void> updateCustomerInformation(@AuthenticationPrincipal Long customerId,
                                                          @RequestBody final ModifiedCustomerRequest request) {
        customerService.updateCustomer(customerId, ModifiedCustomerDto.fromModifiedCustomerRequest(request));
        return ResponseEntity.noContent().build();
    }

    @DeleteMapping("/customers")
    public ResponseEntity<Void> deleteCustomer(@AuthenticationPrincipal Long customerId) {
        customerService.deleteCustomer(customerId);
        return ResponseEntity.noContent().build();
    }
}
