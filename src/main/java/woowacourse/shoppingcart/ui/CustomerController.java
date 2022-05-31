package woowacourse.shoppingcart.ui;

import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import woowacourse.shoppingcart.application.CustomerService;
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
}
