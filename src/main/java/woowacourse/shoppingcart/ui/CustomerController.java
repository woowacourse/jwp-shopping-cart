package woowacourse.shoppingcart.ui;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import woowacourse.auth.support.AuthenticationPrincipal;
import woowacourse.auth.support.TokenProvider;
import woowacourse.shoppingcart.domain.User.User;
import woowacourse.shoppingcart.domain.customer.UserName;
import woowacourse.shoppingcart.dto.CheckDuplicateResponse;
import woowacourse.shoppingcart.application.CustomerService;
import woowacourse.shoppingcart.dto.CustomerRequest;
import woowacourse.shoppingcart.dto.CustomerResponse;

import javax.validation.Valid;

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

        return ResponseEntity.status(HttpStatus.CREATED).build();
    }

    @PutMapping("/me")
    public ResponseEntity<Void> edit(@AuthenticationPrincipal User user,
                                     @RequestBody @Valid CustomerRequest editRequest) {
        customerService.editCustomerByName(user, editRequest);
        return ResponseEntity.ok().build();
    }

    @GetMapping("/me")
    public ResponseEntity<CustomerResponse> customer(@AuthenticationPrincipal User user) {
        return ResponseEntity.ok(customerService.findCustomerByName(user));
    }

    @DeleteMapping("/me")
    public ResponseEntity<Void> withDraw(@AuthenticationPrincipal User user) {
        customerService.deleteCustomerByName(user);
        return ResponseEntity.noContent().build();
    }

    @GetMapping("/exists")
    public ResponseEntity<CheckDuplicateResponse> checkDuplicate(@RequestParam String userName) {
        return ResponseEntity.ok(customerService.isExistUser(userName));
    }
}
