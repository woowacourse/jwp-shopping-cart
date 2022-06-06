package woowacourse.shoppingcart.ui;

import javax.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import woowacourse.auth.support.AuthenticationPrincipal;
import woowacourse.shoppingcart.application.UserService;
import woowacourse.shoppingcart.domain.user.Customer;
import woowacourse.shoppingcart.dto.SignUpRequest;
import woowacourse.shoppingcart.dto.UserResponse;
import woowacourse.shoppingcart.dto.UserUpdateRequest;

@RestController
public class UserController {

    private final UserService userService;

    public UserController(UserService userService) {
        this.userService = userService;
    }

    @PostMapping("/users")
    public ResponseEntity<Void> signUp(@RequestBody @Valid SignUpRequest signUpRequest) {
        userService.registerCustomer(signUpRequest);
        return ResponseEntity.noContent().build();
    }

    @GetMapping("/users/me")
    public ResponseEntity<UserResponse> getUser(@AuthenticationPrincipal String email) {
        Customer customer = userService.findByEmail(email);
        UserResponse userResponse = new UserResponse(customer.getEmail(), customer.getNickname());
        return ResponseEntity.ok(userResponse);
    }

    @DeleteMapping("/users/me")
    public ResponseEntity<Void> deleteUser(@AuthenticationPrincipal String email) {
        userService.deleteByEmail(email);
        return ResponseEntity.noContent().build();
    }

    @PutMapping("/users/me")
    public ResponseEntity<Void> updateUser(@AuthenticationPrincipal String email,
                                           @RequestBody @Valid UserUpdateRequest userUpdateRequest) {
        userService.updateCustomer(email, userUpdateRequest);
        return ResponseEntity.noContent().build();
    }
}
