package woowacourse.shoppingcart.ui;

import java.net.URI;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import woowacourse.shoppingcart.application.CustomerService;
import woowacourse.shoppingcart.dto.CustomerRequest;
import woowacourse.shoppingcart.dto.EmailDuplicationResponse;

@RestController
@RequestMapping("/api")
public class CustomerController {
    private final CustomerService customerService;

    public CustomerController(CustomerService customerService) {
        this.customerService = customerService;
    }

    @PostMapping("/customers")
    public ResponseEntity<Void> createCustomer(@RequestBody CustomerRequest customerRequest) {
        int id = customerService.create(customerRequest);
        return ResponseEntity.created(URI.create("/api/customers/" + id)).build();
    }

    @PostMapping("/validation")
    public ResponseEntity<EmailDuplicationResponse> checkEmailDuplication(@RequestParam String email) {
        EmailDuplicationResponse response = customerService.isDuplicatedEmail(email);
        return ResponseEntity.ok(response);
    }
}
