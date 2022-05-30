package woowacourse.shoppingcart.ui;

import java.net.URI;
import javax.servlet.http.HttpServletRequest;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import woowacourse.auth.support.AuthorizationExtractor;
import woowacourse.shoppingcart.application.CustomerService;
import woowacourse.shoppingcart.dto.CustomerRequest;
import woowacourse.shoppingcart.dto.CustomerResponse;

@RestController
@RequestMapping("/api/customers")
public class CustomerController {

    private final CustomerService customerService;

    public CustomerController(CustomerService customerService) {
        this.customerService = customerService;
    }

    @PostMapping
    public ResponseEntity<Void> register(@RequestBody CustomerRequest customerRequest) {
        customerService.register(customerRequest.getEmail(),
                customerRequest.getPassword(),
                customerRequest.getUsername());
        return ResponseEntity.created(URI.create("/login")).build();
    }

    @GetMapping("/me")
    public ResponseEntity<CustomerResponse> showCustomer(HttpServletRequest request) {
        final String token = AuthorizationExtractor.extract(request);
        CustomerResponse customerResponse = customerService.showCustomer(token);
        return ResponseEntity.ok(customerResponse);
    }
}
