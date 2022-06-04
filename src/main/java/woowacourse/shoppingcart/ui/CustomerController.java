package woowacourse.shoppingcart.ui;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import woowacourse.auth.dto.CustomerRequest;
import woowacourse.auth.dto.CustomerResponse;
import woowacourse.auth.dto.ValidEmailRequest;
import woowacourse.auth.dto.ValidEmailResponse;
import woowacourse.auth.support.AuthorizationExtractor;
import woowacourse.shoppingcart.application.CustomerService;

import javax.servlet.http.HttpServletRequest;
import java.net.URI;

@RestController
@RequestMapping("/customers")
public class CustomerController {

    final CustomerService customerService;

    public CustomerController(CustomerService customerService) {
        this.customerService = customerService;
    }

    @PostMapping
    public ResponseEntity<CustomerResponse> registerCustomers(@RequestBody final CustomerRequest customerRequest) {
        CustomerResponse customerResponse = customerService.register(customerRequest);
        return ResponseEntity.created(URI.create("/customers/login")).body(customerResponse);
    }

    @GetMapping
    public ResponseEntity<CustomerResponse> findCustomer(HttpServletRequest request) {
        String token = AuthorizationExtractor.extract(request);
        CustomerResponse customerResponse = customerService.findCustomerByToken(token);
        return ResponseEntity.ok().body(customerResponse);
    }

    @PostMapping("/email")
    public ResponseEntity<ValidEmailResponse> checkValidEmail(@RequestBody ValidEmailRequest validEmailRequest) {
        return ResponseEntity.ok().body(customerService.isValidEmail(validEmailRequest));
    }

    @PutMapping
    public ResponseEntity<Void> updateCustomer(@RequestBody CustomerRequest customerRequest, HttpServletRequest request) {
        String token = AuthorizationExtractor.extract(request);
        customerService.edit(token, customerRequest);
        return ResponseEntity.ok().build();
    }

    @DeleteMapping
    public ResponseEntity<Void> deleteCustomer(HttpServletRequest request){
        String token = AuthorizationExtractor.extract(request);
        customerService.delete(token);
        return ResponseEntity.noContent().build();
    }


}
