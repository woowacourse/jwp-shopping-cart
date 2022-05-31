package woowacourse.shoppingcart.ui;

import javax.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import woowacourse.shoppingcart.application.CustomerService;
import woowacourse.shoppingcart.domain.Customer;
import woowacourse.shoppingcart.dto.CustomerRequest;
import woowacourse.shoppingcart.dto.CustomerResponse;
import woowacourse.shoppingcart.dto.EmailDuplicateCheckResponse;

@Controller
@RequestMapping("/api/members")
public class CustomerController {

    private final CustomerService customerService;

    public CustomerController(CustomerService customerService) {
        this.customerService = customerService;
    }

    @GetMapping
    public ResponseEntity<EmailDuplicateCheckResponse> checkDuplicateEmail(@RequestParam String email) {
        EmailDuplicateCheckResponse emailDuplicateCheckResponse =
                new EmailDuplicateCheckResponse(customerService.isDistinctEmail(email));
        return ResponseEntity.ok().body(emailDuplicateCheckResponse);
    }

    @PostMapping
    public ResponseEntity<CustomerResponse> signUp(@RequestBody @Valid CustomerRequest customerRequest) {
        Customer customer = customerService.signUp(customerRequest);
        CustomerResponse customerResponse = new CustomerResponse(customer.getEmail(), customer.getNickname());
        return new ResponseEntity<>(customerResponse, HttpStatus.CREATED);
    }
}
