package woowacourse.shoppingcart.ui;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import woowacourse.auth.application.AuthService;
import woowacourse.auth.support.AuthorizationExtractor;
import woowacourse.shoppingcart.application.CustomerService;
import woowacourse.shoppingcart.domain.Customer;
import woowacourse.shoppingcart.dto.CustomerRequest;

import javax.servlet.http.HttpServletRequest;
import java.net.URI;

@RestController
@RequestMapping("/api/customers")
public class CustomerController {
    private final CustomerService customerService;
    private final AuthService authService;

    public CustomerController(CustomerService customerService, AuthService authService) {
        this.customerService = customerService;
        this.authService = authService;
    }

    @PostMapping
    public ResponseEntity<Void> signUp(@RequestBody CustomerRequest customerRequest) {
        customerService.addCustomer(customerRequest);
        return ResponseEntity.created(
                URI.create("/api/customers/" + customerRequest.getName())).build();
    }

    @GetMapping("/{customerName}")
    public ResponseEntity<Customer> customer(@PathVariable String customerName) {
        return ResponseEntity.ok(customerService.findCustomerByName(customerName));
    }

    @DeleteMapping("/{customerName}")
    public ResponseEntity<Void> withDraw(@PathVariable String customerName) {
        customerService.deleteCustomerByName(customerName);
        return ResponseEntity.noContent().build();
    }

    @PutMapping("/{customerName}")
    public ResponseEntity<Void> edit(@PathVariable String customerName, @RequestBody CustomerRequest editRequest) {
        customerService.editCustomerByName(customerName, editRequest);
        return ResponseEntity.ok().build();
    }

    @GetMapping("/me")
    public ResponseEntity<Customer> customerMe(HttpServletRequest request) {
        String token = AuthorizationExtractor.extract(request);
        String customerName = authService.getNameFromToken(token);
        return ResponseEntity.ok(customerService.findCustomerByName(customerName));
    }
}
