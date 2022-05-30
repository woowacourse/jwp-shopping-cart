package woowacourse.shoppingcart.ui;

import javax.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import woowacourse.shoppingcart.dto.CustomerRequest;

@RestController
public class CustomerController {

    @PostMapping("/users")
    public ResponseEntity<Void> addCustomer(@RequestBody @Valid CustomerRequest customerRequest) {
        return ResponseEntity.ok().build();
    }

}
