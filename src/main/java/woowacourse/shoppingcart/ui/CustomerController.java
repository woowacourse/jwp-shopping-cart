package woowacourse.shoppingcart.ui;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import woowacourse.auth.support.AuthenticationPrincipal;
import woowacourse.auth.support.TokenProvider;
import woowacourse.shoppingcart.dto.CheckDuplicateResponse;
import woowacourse.shoppingcart.application.CustomerService;
import woowacourse.shoppingcart.dto.CustomerRequest;
import woowacourse.shoppingcart.dto.CustomerResponse;

import javax.validation.Valid;
import java.net.URI;

@RestController
@RequestMapping("/api/customers")
public class CustomerController {

    private final CustomerService customerService;
    private final TokenProvider tokenProvider;

    public CustomerController(CustomerService customerService, TokenProvider tokenProvider) {
        this.customerService = customerService;
        this.tokenProvider = tokenProvider;
    }

    @PostMapping
    public ResponseEntity<Void> signUp(@RequestBody @Valid CustomerRequest customerRequest) {
        customerService.addCustomer(customerRequest);
        return ResponseEntity.created(
                URI.create("/api/customers/" + customerRequest.getName())).build();
    }

    @PutMapping("/me")
    public ResponseEntity<Void> edit(@AuthenticationPrincipal String customerName,
                                     @RequestBody @Valid CustomerRequest editRequest) {
        customerService.editCustomerByName(customerName, editRequest);
        return ResponseEntity.ok().build();
    }

    @GetMapping("/me")
    public ResponseEntity<CustomerResponse> customer(@AuthenticationPrincipal String customerName) {
        return ResponseEntity.ok(customerService.findCustomerByName(customerName));
    }

    @DeleteMapping("/me")
    public ResponseEntity<Void> withDraw(@AuthenticationPrincipal String customerName) {
        customerService.deleteCustomerByName(customerName);
        return ResponseEntity.noContent().build();
    }

    @GetMapping("/exists")
    public ResponseEntity<CheckDuplicateResponse> checkDuplicate(@RequestParam String userName) {
        return ResponseEntity.ok(customerService.isExistUser(userName));
    }
}
