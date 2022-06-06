package woowacourse.auth.ui;

import java.net.URI;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import springfox.documentation.annotations.ApiIgnore;
import woowacourse.auth.domain.User;
import woowacourse.auth.application.CustomerService;
import woowacourse.auth.dto.request.SignUpRequest;
import woowacourse.auth.dto.request.UniqueUsernameRequest;
import woowacourse.auth.dto.request.UpdateMeRequest;
import woowacourse.auth.dto.request.UpdatePasswordRequest;
import woowacourse.auth.dto.response.GetMeResponse;
import woowacourse.auth.dto.response.UniqueUsernameResponse;

@RestController
@RequestMapping("/customers")
public class CustomerController {

    private static final URI CUSTOMER_INFO_LOCATION = URI.create("/customers/me");

    private final CustomerService customerService;

    public CustomerController(CustomerService customerService) {
        this.customerService = customerService;
    }

    @PostMapping
    public ResponseEntity<Void> signUp(@Validated @RequestBody SignUpRequest request) {
        customerService.createCustomer(request);
        return ResponseEntity.created(CUSTOMER_INFO_LOCATION).build();
    }

    @GetMapping("/me")
    public ResponseEntity<GetMeResponse> getMe(@ApiIgnore User authUser) {
        GetMeResponse currentCustomer = customerService.getCustomer(authUser);
        return ResponseEntity.ok(currentCustomer);
    }

    @PutMapping("/me")
    public ResponseEntity<Void> updateMe(@ApiIgnore User authUser,
                                         @Validated @RequestBody UpdateMeRequest updateMeRequest) {
        customerService.updateNicknameAndAge(authUser, updateMeRequest);
        return ResponseEntity.ok().build();
    }

    @DeleteMapping("/me")
    public ResponseEntity<Void> deleteMe(@ApiIgnore User authUser) {
        customerService.deleteCustomer(authUser);
        return ResponseEntity.noContent().build();
    }

    @PutMapping("/me/password")
    public ResponseEntity<Void> updatePassword(@ApiIgnore User authUser,
                                               @Validated @RequestBody UpdatePasswordRequest updatePasswordRequest) {
        customerService.updatePassword(authUser, updatePasswordRequest);
        return ResponseEntity.ok().build();
    }

    @GetMapping("/username/duplication")
    public ResponseEntity<UniqueUsernameResponse> checkUniqueUsername(
            @Validated @RequestBody UniqueUsernameRequest request) {
        UniqueUsernameResponse response = customerService.checkUniqueUsername(request.getUsername());
        return ResponseEntity.ok(response);
    }
}
