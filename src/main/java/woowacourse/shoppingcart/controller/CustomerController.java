package woowacourse.shoppingcart.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import woowacourse.auth.service.AuthService;
import woowacourse.auth.support.AuthorizationExtractor;
import woowacourse.shoppingcart.dto.CheckDuplicationRequest;
import woowacourse.shoppingcart.dto.CheckDuplicationResponse;
import woowacourse.shoppingcart.service.CustomerService;
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

    public CustomerController(final CustomerService customerService, final AuthService authService) {
        this.customerService = customerService;
        this.authService = authService;
    }

    @PostMapping
    public ResponseEntity<Void> signUp(@RequestBody @Valid final CustomerRequest customerRequest) {
        customerService.addCustomer(customerRequest);
        return ResponseEntity.created(URI.create("/api/customers/" + customerRequest.getName())).build();
    }

    @PutMapping("/me")
    public ResponseEntity<Void> edit(final HttpServletRequest request,
                                     @RequestBody @Valid final CustomerRequest editRequest) {
        final String customerName = getNameFromToken(request);
        customerService.editCustomerByName(customerName, editRequest);
        return ResponseEntity.ok().build();
    }

    @GetMapping("/me")
    public ResponseEntity<CustomerResponse> customer(final HttpServletRequest request) {
        final String customerName = getNameFromToken(request);
        return ResponseEntity.ok(customerService.findCustomerByName(customerName));
    }

    @DeleteMapping("/me")
    public ResponseEntity<Void> withDraw(final HttpServletRequest request) {
        final String customerName = getNameFromToken(request);
        customerService.deleteCustomerByName(customerName);
        return ResponseEntity.noContent().build();
    }

    @PostMapping("/duplication")
    public ResponseEntity<CheckDuplicationResponse> checkDuplication(@RequestBody final CheckDuplicationRequest request) {
        return ResponseEntity.ok(customerService.checkDuplicationByName(request.getUserName()));
    }

    private String getNameFromToken(final HttpServletRequest request) {
        final String token = AuthorizationExtractor.extract(request);
        return authService.getNameFromToken(token);
    }
}
