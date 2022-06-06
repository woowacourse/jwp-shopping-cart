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
import woowacourse.shoppingcart.application.AccountService;
import woowacourse.shoppingcart.domain.Account;
import woowacourse.shoppingcart.dto.SignUpRequest;
import woowacourse.shoppingcart.dto.AccountResponse;
import woowacourse.shoppingcart.dto.AccountUpdateRequest;

@RestController
public class AccountController {

    private final AccountService accountService;

    public AccountController(AccountService accountService) {
        this.accountService = accountService;
    }

    @PostMapping("/users")
    public ResponseEntity<Void> addCustomer(@RequestBody @Valid SignUpRequest signUpRequest) {
        accountService.saveAccount(signUpRequest);
        return ResponseEntity.noContent().build();
    }

    @GetMapping("/users/me")
    public ResponseEntity<AccountResponse> getCustomer(@AuthenticationPrincipal String email) {
        Account account = accountService.findByEmail(email);
        AccountResponse accountResponse = new AccountResponse(account.getEmail(), account.getNickname());
        return ResponseEntity.ok(accountResponse);
    }

    @DeleteMapping("/users/me")
    public ResponseEntity<Void> deleteCustomer(@AuthenticationPrincipal String email) {
        accountService.deleteByEmail(email);
        return ResponseEntity.noContent().build();
    }

    @PutMapping("/users/me")
    public ResponseEntity<Void> updateCustomer(@AuthenticationPrincipal String email,
                                               @RequestBody @Valid AccountUpdateRequest accountUpdateRequest) {
        accountService.updateCustomer(email, accountUpdateRequest);
        return ResponseEntity.noContent().build();
    }
}
