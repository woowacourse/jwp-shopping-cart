package woowacourse.shoppingcart.ui;

import javax.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import woowacourse.shoppingcart.application.CustomerService;
import woowacourse.shoppingcart.dto.CustomerCreationRequest;

@RestController
public class CustomerController {

    private final CustomerService customerService;

    public CustomerController(CustomerService customerService) {
        this.customerService = customerService;
    }

    @PostMapping("/users")
    public ResponseEntity<Void> create(@RequestBody @Valid CustomerCreationRequest customerCreationRequest) {
        customerService.create(customerCreationRequest);

        return ResponseEntity.ok().build();
    }
}
