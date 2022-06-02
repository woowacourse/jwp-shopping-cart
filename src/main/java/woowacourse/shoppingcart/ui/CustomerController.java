package woowacourse.shoppingcart.ui;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import woowacourse.shoppingcart.application.CustomerService;
import woowacourse.shoppingcart.dto.CustomerRequest;
import woowacourse.shoppingcart.dto.CustomerResponse;

import javax.servlet.http.HttpServletRequest;
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
                URI.create("/api/customers/" + customerRequest.getName())).build();
    }

    @PostMapping("/duplication")
    public ResponseEntity<Boolean> checkDuplicatedName(@RequestBody String customerName) {
        boolean isExistCustomer = customerService.existsCustomer(customerName);
        return ResponseEntity.ok(isExistCustomer);
    }

    @PutMapping("/me")
    public ResponseEntity<Void> edit(HttpServletRequest request, @RequestBody @Valid CustomerRequest editRequest) {
        customerService.editCustomer(request, editRequest);
        return ResponseEntity.ok().build();
    }

    @GetMapping("/me")
    public ResponseEntity<CustomerResponse> customer(HttpServletRequest request) {
        return ResponseEntity.ok(customerService.findCustomer(request));
    }

    @DeleteMapping("/me")
    public ResponseEntity<Void> withDraw(HttpServletRequest request) {
        customerService.deleteCustomer(request);
        return ResponseEntity.noContent().build();
    }
}
