package woowacourse.shoppingcart.ui;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import woowacourse.auth.application.AuthService;
import woowacourse.auth.support.AuthorizationExtractor;
import woowacourse.shoppingcart.dto.CheckDuplicateResponse;
import woowacourse.shoppingcart.application.CustomerService;
import woowacourse.shoppingcart.dto.CheckDuplicateRequest;
import woowacourse.shoppingcart.dto.CustomerRequest;
import woowacourse.shoppingcart.dto.CustomerResponse;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;
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
    public ResponseEntity<Void> signUp(@RequestBody @Valid CustomerRequest customerRequest) {
        customerService.addCustomer(customerRequest);
        return ResponseEntity.created(
                URI.create("/api/customers/" + customerRequest.getName())).build();
    }

    @PutMapping("/me")
    public ResponseEntity<Void> edit(HttpServletRequest request, @RequestBody @Valid CustomerRequest editRequest) {
        String customerName = getNameFromToken(request);
        customerService.editCustomerByName(customerName, editRequest);
        return ResponseEntity.ok().build();
    }

    @GetMapping("/me")
    public ResponseEntity<CustomerResponse> customer(HttpServletRequest request) {
        String customerName = getNameFromToken(request);
        return ResponseEntity.ok(customerService.findCustomerByName(customerName));
    }

    @DeleteMapping("/me")
    public ResponseEntity<Void> withDraw(HttpServletRequest request) {
        String customerName = getNameFromToken(request);
        customerService.deleteCustomerByName(customerName);
        return ResponseEntity.noContent().build();
    }

    @PostMapping("/duplication")
    public ResponseEntity<CheckDuplicateResponse> checkDuplicate(@RequestBody CheckDuplicateRequest request) {
        return ResponseEntity.ok(customerService.isExistUser(request));
    }

    private String getNameFromToken(HttpServletRequest request) {
        String token = AuthorizationExtractor.extract(request);
        return authService.getNameFromToken(token);
    }
}
