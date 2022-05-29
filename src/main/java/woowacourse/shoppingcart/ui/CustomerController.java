package woowacourse.shoppingcart.ui;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import woowacourse.shoppingcart.dto.CustomerRequest;
import woowacourse.shoppingcart.dto.CustomerResponse;

import javax.validation.Valid;

@RestController
@RequestMapping("/customers")
public class CustomerController {

    @PostMapping("/signup")
    public ResponseEntity<CustomerResponse> addCustomer(@Valid @RequestBody CustomerRequest customerRequest) {
        CustomerResponse customerResponse = new CustomerResponse(1L,
                customerRequest.getUserName(),
                customerRequest.getNickName(),
                customerRequest.getPassword(),
                customerRequest.getAge()
        );
        return ResponseEntity.status(HttpStatus.CREATED).body(customerResponse);
    }
}
