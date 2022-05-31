package woowacourse.shoppingcart.ui;

import java.net.URI;
import javax.servlet.http.HttpServletRequest;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import woowacourse.auth.application.AuthService;
import woowacourse.auth.support.AuthorizationExtractor;
import woowacourse.shoppingcart.application.CustomerService;
import woowacourse.shoppingcart.dto.response.GetMeResponse;
import woowacourse.shoppingcart.dto.request.SignUpRequest;
import woowacourse.shoppingcart.dto.request.UpdateMeRequest;
import woowacourse.shoppingcart.dto.request.UpdatePasswordRequest;

@RestController
@RequestMapping("/customers")
public class CustomerController {

    private final CustomerService customerService;
    private final AuthService authService;

    public CustomerController(CustomerService customerService, AuthService authService) {
        this.customerService = customerService;
        this.authService = authService;
    }

    @PostMapping
    public ResponseEntity<Void> signUp(@RequestBody SignUpRequest request) {
        Long customerId = customerService.signUp(request);
        URI location = URI.create("/customers/" + customerId);
        return ResponseEntity.created(location).build();
    }

    @GetMapping("/me")
    public ResponseEntity<GetMeResponse> getMe(HttpServletRequest request) {
        String token = AuthorizationExtractor.extract(request);
        Long customerId = authService.findUserIdByToken(token);
        GetMeResponse currentCustomer = customerService.getMe(customerId);
        return ResponseEntity.ok(currentCustomer);
    }

    @PutMapping("/me")
    public ResponseEntity<Void> updateMe(@RequestBody UpdateMeRequest updateMeRequest,
                                         HttpServletRequest request) {
        String token = AuthorizationExtractor.extract(request);
        Long customerId = authService.findUserIdByToken(token);
        customerService.updateMe(customerId, updateMeRequest);
        return ResponseEntity.ok().build();
    }

    @DeleteMapping("/me")
    public ResponseEntity<Void> deleteMe(HttpServletRequest request) {
        String token = AuthorizationExtractor.extract(request);
        Long customerId = authService.findUserIdByToken(token);
        customerService.deleteMe(customerId);
        return ResponseEntity.noContent().build();
    }

    @PutMapping("/me/password")
    public ResponseEntity<Void> updatePassword(@RequestBody UpdatePasswordRequest updatePasswordRequest,
                                               HttpServletRequest request) {
        String token = AuthorizationExtractor.extract(request);
        Long customerId = authService.findUserIdByToken(token);
        customerService.updatePassword(customerId, updatePasswordRequest);
        return ResponseEntity.ok().build();
    }
}
