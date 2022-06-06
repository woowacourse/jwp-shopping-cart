package woowacourse.shoppingcart.controller;

import java.net.URI;
import javax.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import woowacourse.auth.support.UserName;
import woowacourse.shoppingcart.dto.request.CheckDuplicationRequest;
import woowacourse.shoppingcart.dto.request.EditCustomerRequest;
import woowacourse.shoppingcart.dto.request.SignUpRequest;
import woowacourse.shoppingcart.dto.response.CheckDuplicationResponse;
import woowacourse.shoppingcart.dto.response.CustomerResponse;
import woowacourse.shoppingcart.service.CustomerService;

@RestController
@RequestMapping("/api/customers")
public class CustomerController {

    private final CustomerService customerService;

    public CustomerController(final CustomerService customerService) {
        this.customerService = customerService;
    }

    @PostMapping
    public ResponseEntity<Void> signUp(@RequestBody @Valid final SignUpRequest request) {
        customerService.addCustomer(request);
        return ResponseEntity.created(URI.create("/api/customers/" + request.getUserName())).build();
    }

    @PutMapping("/me")
    public ResponseEntity<Void> edit(@UserName final String customerName,
                                     @RequestBody @Valid final EditCustomerRequest request) {
        customerService.editCustomerByName(customerName, request);
        return ResponseEntity.ok().build();
    }

    @GetMapping("/me")
    public ResponseEntity<CustomerResponse> customer(@UserName final String customerName) {
        return ResponseEntity.ok(customerService.findCustomerByName(customerName));
    }

    @DeleteMapping("/me")
    public ResponseEntity<Void> withDraw(@UserName final String customerName) {
        customerService.deleteCustomerByName(customerName);
        return ResponseEntity.noContent().build();
    }

    @PostMapping("/duplication")
    public ResponseEntity<CheckDuplicationResponse> checkDuplication(
            @RequestBody final CheckDuplicationRequest request) {
        return ResponseEntity.ok(customerService.checkDuplicationByName(request.getUserName()));
    }
}
