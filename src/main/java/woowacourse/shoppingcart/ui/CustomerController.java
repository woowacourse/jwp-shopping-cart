package woowacourse.shoppingcart.ui;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;
import woowacourse.auth.domain.LoginCustomer;
import woowacourse.auth.support.AuthenticationPrincipal;
import woowacourse.shoppingcart.application.CustomerService;
import woowacourse.shoppingcart.dto.CustomerRequest;
import woowacourse.shoppingcart.dto.CustomerResponse;
import woowacourse.shoppingcart.dto.ExistCustomerResponse;

import javax.validation.Valid;
import java.net.URI;

@RestController
@RequestMapping("/api/customers")
public class CustomerController {
    private final CustomerService customerService;

    public CustomerController(CustomerService customerService) {
        this.customerService = customerService;
    }

    @PostMapping
    public ResponseEntity<Void> signUp(@RequestBody @Valid CustomerRequest customerRequest) {
        customerService.addCustomer(customerRequest);
        return ResponseEntity.created(
                URI.create("/api/customers/" + customerRequest.getUserName())).build();
    }

    @GetMapping("/exists")
    public ResponseEntity<ExistCustomerResponse> checkDuplicatedName(@RequestParam String userName) {
        boolean isExistCustomer = customerService.existsCustomer(userName);
        return ResponseEntity.ok(new ExistCustomerResponse(isExistCustomer));
    }

    @PutMapping("/me")
    public ResponseEntity<Void> edit(@AuthenticationPrincipal LoginCustomer loginCustomer, @RequestBody @Valid CustomerRequest editRequest) {
        customerService.editCustomer(loginCustomer.getUserName(), editRequest);
        return ResponseEntity.ok().build();
    }

    @GetMapping("/me")
    public ResponseEntity<CustomerResponse> customer(@AuthenticationPrincipal LoginCustomer loginCustomer) {
        return ResponseEntity.ok(customerService.findCustomer(loginCustomer.getUserName()));
    }

    @DeleteMapping("/me")
    public ResponseEntity<Void> withDraw(@AuthenticationPrincipal LoginCustomer loginCustomer) {
        customerService.deleteCustomer(loginCustomer.getUserName());
        return ResponseEntity.noContent().build();
    }
}
