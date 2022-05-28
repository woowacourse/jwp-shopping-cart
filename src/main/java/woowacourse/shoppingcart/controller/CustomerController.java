package woowacourse.shoppingcart.controller;

import java.net.URI;
import javax.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import woowacourse.shoppingcart.dto.SignUpDto;
import woowacourse.shoppingcart.service.CustomerService;

@RestController
@RequestMapping("/api/customers")
public class CustomerController {

    private final CustomerService customerService;

    public CustomerController(final CustomerService customerService) {
        this.customerService = customerService;
    }

    @PostMapping()
    public ResponseEntity<Void> signUp(@Valid @RequestBody SignUpDto signUpDto){
        final Long createdId = customerService.signUp(signUpDto);

        return ResponseEntity.created(URI.create("/api/customers/" + createdId)).build();
    }
}
