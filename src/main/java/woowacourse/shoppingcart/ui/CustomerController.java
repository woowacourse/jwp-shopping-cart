package woowacourse.shoppingcart.ui;

import java.net.URI;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;
import javax.validation.constraints.NotNull;
import woowacourse.auth.support.AuthenticationPrincipal;
import woowacourse.shoppingcart.application.CustomerService;
import woowacourse.shoppingcart.dto.CustomerRegisterRequest;
import woowacourse.shoppingcart.dto.CustomerResponse;
import woowacourse.shoppingcart.dto.CustomerUpdateRequest;
import woowacourse.shoppingcart.dto.CustomerUpdateResponse;

@RestController
@RequestMapping("/customers")
public class CustomerController {

    private final CustomerService customerService;

    public CustomerController(final CustomerService customerService) {
        this.customerService = customerService;
    }

    @PostMapping
    public ResponseEntity<CustomerResponse> registerCustomer(
            @RequestBody @Valid final CustomerRegisterRequest customerRegisterRequest) {
        final Long customerId = customerService.registerCustomer(customerRegisterRequest);
        final CustomerResponse customerResponse = customerService.findById(customerId);

        return ResponseEntity.created(URI.create("/customers/" + customerId)).body(customerResponse);
    }

    @GetMapping
    public ResponseEntity<CustomerResponse> findCustomer(@AuthenticationPrincipal @NotNull final Long id) {
        final CustomerResponse customerResponse = customerService.findById(id);
        return ResponseEntity.ok(customerResponse);
    }

    @PatchMapping
    public ResponseEntity<CustomerUpdateResponse> updateCustomer(
            @AuthenticationPrincipal @NotNull final Long id,
            @RequestBody @Valid final CustomerUpdateRequest customerUpdateRequest) {
        final CustomerUpdateResponse customerUpdateResponse = customerService.updateCustomer(id, customerUpdateRequest);
        return ResponseEntity.ok(customerUpdateResponse);
    }

    @DeleteMapping
    public ResponseEntity<Void> removeCustomer(@AuthenticationPrincipal @NotNull final Long id) {
        customerService.removeCustomer(id);
        return ResponseEntity.noContent().build();
    }
}
