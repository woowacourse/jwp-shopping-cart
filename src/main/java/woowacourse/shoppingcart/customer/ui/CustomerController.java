package woowacourse.shoppingcart.customer.ui;

import java.net.URI;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import woowacourse.shoppingcart.auth.support.jwt.AuthenticationPrincipal;
import woowacourse.shoppingcart.customer.application.CustomerService;
import woowacourse.shoppingcart.customer.application.dto.request.CustomerPasswordUpdateRequest;
import woowacourse.shoppingcart.customer.application.dto.request.CustomerProfileUpdateRequest;
import woowacourse.shoppingcart.customer.application.dto.request.CustomerRegisterRequest;
import woowacourse.shoppingcart.customer.application.dto.request.CustomerRemoveRequest;
import woowacourse.shoppingcart.customer.application.dto.response.CustomerResponse;
import woowacourse.shoppingcart.customer.application.dto.response.CustomerUpdateResponse;

@RestController
@RequestMapping("/customers")
public class CustomerController {

    private final CustomerService customerService;

    public CustomerController(final CustomerService customerService) {
        this.customerService = customerService;
    }

    @PostMapping
    public ResponseEntity<CustomerResponse> registerCustomer(
            @RequestBody final CustomerRegisterRequest customerRegisterRequest) {
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
    public ResponseEntity<CustomerUpdateResponse> updateCustomerProfile(
            @AuthenticationPrincipal final Long id,
            @RequestBody final CustomerProfileUpdateRequest customerUpdateRequest) {
        final CustomerUpdateResponse customerUpdateResponse = customerService.updateCustomerProfile(id,
                customerUpdateRequest);
        return ResponseEntity.ok(customerUpdateResponse);
    }

    @PatchMapping("/password")
    public ResponseEntity<Void> updateCustomer(
            @AuthenticationPrincipal final Long id,
            @RequestBody final CustomerPasswordUpdateRequest customerPasswordUpdateRequest) {
        customerService.updateCustomerPassword(id, customerPasswordUpdateRequest);
        return ResponseEntity.noContent().build();
    }

    @DeleteMapping
    public ResponseEntity<Void> removeCustomer(
            @AuthenticationPrincipal final Long id,
            @RequestBody final CustomerRemoveRequest customerRemoveRequest) {
        customerService.removeCustomer(id, customerRemoveRequest);
        return ResponseEntity.noContent().build();
    }
}
