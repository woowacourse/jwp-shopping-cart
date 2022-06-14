package woowacourse.shoppingcart.ui;

import java.net.URI;
import javax.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import woowacourse.auth.support.AuthenticationPrincipal;
import woowacourse.shoppingcart.application.CustomerService;
import woowacourse.shoppingcart.dto.customer.CustomerRegisterRequest;
import woowacourse.shoppingcart.dto.customer.CustomerRemoveRequest;
import woowacourse.shoppingcart.dto.customer.CustomerResponse;
import woowacourse.shoppingcart.dto.customer.CustomerUpdateRequest;
import woowacourse.shoppingcart.dto.customer.CustomerUpdateResponse;

@RestController
@RequestMapping("/customers")
public class CustomerController {

    private final CustomerService customerService;

    public CustomerController(final CustomerService customerService) {
        this.customerService = customerService;
    }

    @PostMapping
    public ResponseEntity<CustomerResponse> registerCustomer(
            @Valid @RequestBody final CustomerRegisterRequest customerRegisterRequest) {
        final Long customerId = customerService.registerCustomer(customerRegisterRequest);
        final CustomerResponse customerResponse = customerService.findById(customerId);

        return ResponseEntity.created(URI.create("/customers/" + customerId)).body(customerResponse);
    }

    @GetMapping
    public ResponseEntity<CustomerResponse> findCustomer(@AuthenticationPrincipal final Long id) {
        final CustomerResponse customerResponse = customerService.findById(id);
        return ResponseEntity.ok(customerResponse);
    }

    @PatchMapping("/profile")
    public ResponseEntity<CustomerUpdateResponse> updateCustomerNickname(
            @AuthenticationPrincipal final Long id,
            @Valid @RequestBody final CustomerUpdateRequest customerUpdateRequest) {
        final CustomerUpdateResponse customerUpdateResponse = customerService.updateCustomerNickName(id,
                customerUpdateRequest);
        return ResponseEntity.ok(customerUpdateResponse);
    }

    @PatchMapping("/password")
    public ResponseEntity<Void> updateCustomerPassword(
            @AuthenticationPrincipal final Long id,
            @Valid @RequestBody final CustomerUpdateRequest customerUpdateRequest) {
        customerService.updateCustomerPassword(id, customerUpdateRequest);
        return ResponseEntity.noContent().build();
    }

    @DeleteMapping
    public ResponseEntity<Void> removeCustomer(
            @AuthenticationPrincipal final Long id,
            @Valid @RequestBody final CustomerRemoveRequest customerRemoveRequest) {
        customerService.removeCustomer(id, customerRemoveRequest);
        return ResponseEntity.noContent().build();
    }
}
