package woowacourse.shoppingcart.ui;

import java.net.URI;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import woowacourse.auth.support.AuthenticationPrincipal;
import woowacourse.auth.support.JwtTokenProvider;
import woowacourse.shoppingcart.application.CustomerService;
import woowacourse.shoppingcart.dto.CustomerLoginRequest;
import woowacourse.shoppingcart.dto.CustomerLoginResponse;
import woowacourse.shoppingcart.dto.CustomerRequest;
import woowacourse.shoppingcart.dto.CustomerResponse;
import woowacourse.shoppingcart.dto.CustomerUpdateRequest;
import woowacourse.shoppingcart.dto.PasswordChangeRequest;
import woowacourse.shoppingcart.dto.PasswordRequest;
import woowacourse.shoppingcart.dto.TokenRequest;

@RestController
public class CustomerController {

    private final CustomerService customerService;

    private final JwtTokenProvider jwtTokenProvider;

    public CustomerController(final JwtTokenProvider jwtTokenProvider,
                              final CustomerService customerService) {
        this.jwtTokenProvider = jwtTokenProvider;
        this.customerService = customerService;
    }

    @PostMapping("/customers/signUp")
    public ResponseEntity<Void> signUp(final @RequestBody CustomerRequest request) {
        Long id = customerService.signUp(request);
        return ResponseEntity.created(URI.create("/customers/" + id)).build();
    }

    @PostMapping("/customers/login")
    public ResponseEntity<CustomerLoginResponse> login(final @RequestBody CustomerLoginRequest request) {
        CustomerLoginResponse response = customerService.login(request);
        String accessToken = jwtTokenProvider.createToken(String.valueOf(response.getId()));
        return ResponseEntity.ok().body(response.setToken(accessToken));
    }

    @GetMapping("/auth/customers/profile")
    public ResponseEntity<CustomerResponse> getProfile(final @AuthenticationPrincipal TokenRequest request) {
        CustomerResponse response = customerService.findById(request);
        return ResponseEntity.ok().body(response);
    }

    @PatchMapping("/auth/customers/profile")
    public ResponseEntity<Void> updateProfile(final @AuthenticationPrincipal TokenRequest tokenRequest,
                                              final @RequestBody CustomerUpdateRequest customerUpdateRequest) {
        customerService.update(tokenRequest, customerUpdateRequest);
        return ResponseEntity.ok().build();
    }

    @PatchMapping("/auth/customers/profile/password")
    public ResponseEntity<Void> updatePassword(final @AuthenticationPrincipal TokenRequest tokenRequest,
                                               final @RequestBody PasswordChangeRequest passwordChangeRequest) {
        customerService.updatePassword(tokenRequest, passwordChangeRequest);
        return ResponseEntity.ok().build();
    }

    @DeleteMapping("/auth/customers/profile")
    public ResponseEntity<Void> withdraw(final @AuthenticationPrincipal TokenRequest request) {
        customerService.withdraw(request);
        return ResponseEntity.noContent().build();
    }

    @GetMapping(value = "/customers/check", params = "userId")
    public ResponseEntity<Void> checkDuplicateUsername(final @RequestParam(name = "userId") String username) {
        customerService.checkDuplicateUsername(username);
        return ResponseEntity.ok().build();
    }

    @GetMapping(value = "/customers/check", params = "nickname")
    public ResponseEntity<Void> checkDuplicateNickname(final @RequestParam String nickname) {
        customerService.checkDuplicateNickname(nickname);
        return ResponseEntity.ok().build();
    }

    @PostMapping("/auth/customers/match/password")
    public ResponseEntity<Void> matchPassword(final @AuthenticationPrincipal TokenRequest tokenRequest,
                                              final @RequestBody PasswordRequest passwordRequest) {
        customerService.matchPassword(tokenRequest, passwordRequest);
        return ResponseEntity.ok().build();
    }
}
