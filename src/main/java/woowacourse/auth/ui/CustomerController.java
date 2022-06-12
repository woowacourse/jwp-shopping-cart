package woowacourse.auth.ui;

import java.net.URI;
import javax.validation.constraints.NotBlank;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import springfox.documentation.annotations.ApiIgnore;
import woowacourse.auth.application.CustomerService;
import woowacourse.auth.domain.user.Customer;
import woowacourse.auth.dto.request.SignUpRequest;
import woowacourse.auth.dto.request.UpdateMeRequest;
import woowacourse.auth.dto.request.UpdatePasswordRequest;
import woowacourse.auth.dto.response.GetMeResponse;
import woowacourse.auth.dto.response.UniqueUsernameResponse;

@Validated
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
    public ResponseEntity<GetMeResponse> getMe(@ApiIgnore Customer authCustomer) {
        return ResponseEntity.ok(new GetMeResponse(authCustomer));
    }

    @PutMapping("/me")
    public ResponseEntity<Void> updateMe(@ApiIgnore Customer authCustomer,
                                         @Validated @RequestBody UpdateMeRequest updateMeRequest) {
        customerService.updateNicknameAndAge(authCustomer, updateMeRequest);
        return ResponseEntity.ok().build();
    }

    @DeleteMapping("/me")
    public ResponseEntity<Void> deleteMe(@ApiIgnore Customer authCustomer) {
        customerService.deleteCustomer(authCustomer);
        return ResponseEntity.noContent().build();
    }

    @PutMapping("/me/password")
    public ResponseEntity<Void> updatePassword(@ApiIgnore Customer authCustomer,
                                               @Validated @RequestBody UpdatePasswordRequest updatePasswordRequest) {
        customerService.updatePassword(authCustomer, updatePasswordRequest);
        return ResponseEntity.ok().build();
    }

    @GetMapping("/username/uniqueness")
    public ResponseEntity<UniqueUsernameResponse> checkUniqueUsername(
            @NotBlank(message = "아이디 입력 필요") @RequestParam String username) {
        UniqueUsernameResponse response = customerService.checkUniqueUsername(username);
        return ResponseEntity.ok(response);
    }
}
