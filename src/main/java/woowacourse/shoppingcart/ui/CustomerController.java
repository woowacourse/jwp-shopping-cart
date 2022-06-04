package woowacourse.shoppingcart.ui;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import woowacourse.shoppingcart.dto.CustomerRequest;
import woowacourse.shoppingcart.dto.CustomerResponse;
import woowacourse.shoppingcart.dto.EmailRequest;
import woowacourse.shoppingcart.dto.EmailResponse;
import woowacourse.auth.support.AuthorizationExtractor;
import woowacourse.shoppingcart.application.CustomerService;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;
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
    public ResponseEntity<EmailResponse> checkValidEmail(@RequestBody @Valid EmailRequest emailRequest) {
        return ResponseEntity.ok().body(customerService.isDuplication(emailRequest));
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
