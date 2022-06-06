package woowacourse.shoppingcart.ui;

import java.net.URI;
import java.net.URISyntaxException;
import javax.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import woowacourse.shoppingcart.application.CustomerService;
import woowacourse.shoppingcart.dto.SignupRequest;

@RestController
public class SignupController {

    private final CustomerService customerService;

    public SignupController(CustomerService customerService) {
        this.customerService = customerService;
    }

    @PostMapping("/signup")
    public ResponseEntity<Void> signup(@RequestBody @Valid SignupRequest signupRequest) throws URISyntaxException {
        customerService.create(signupRequest);

        return ResponseEntity.created(new URI("/signin")).build();
    }
}
