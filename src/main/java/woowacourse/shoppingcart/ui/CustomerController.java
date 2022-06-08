package woowacourse.shoppingcart.ui;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import woowacourse.auth.support.AuthenticationPrincipal;
import woowacourse.auth.support.TokenProvider;
import woowacourse.shoppingcart.domain.customer.UserName;
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
                URI.create("/api/customers/" + customerRequest.getUserName())).build();
    }

    @PutMapping("/me")
    public ResponseEntity<Void> edit(@AuthenticationPrincipal UserName userName,
                                     @RequestBody @Valid CustomerRequest editRequest) {
        customerService.editCustomerByName(userName, editRequest);
        return ResponseEntity.ok().build();
    }

    @GetMapping("/me")
    public ResponseEntity<CustomerResponse> customer(@AuthenticationPrincipal UserName userName) {
        return ResponseEntity.ok(customerService.findCustomerByName(userName));
    }

    @DeleteMapping("/me")
    public ResponseEntity<Void> withDraw(@AuthenticationPrincipal UserName userName) {
        customerService.deleteCustomerByName(userName);
        return ResponseEntity.noContent().build();
    }

    @GetMapping("/exists")
    public ResponseEntity<CheckDuplicateResponse> checkDuplicate(@RequestParam String userName) {
        return ResponseEntity.ok(customerService.isExistUser(userName));
    }
}
