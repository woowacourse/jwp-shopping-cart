package woowacourse.auth.ui;

import javax.servlet.http.HttpServletRequest;

import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import woowacourse.auth.application.AuthService;
import woowacourse.auth.dto.CustomerDto;
import woowacourse.auth.dto.CustomerResponse;
import woowacourse.auth.dto.LoginRequest;
import woowacourse.auth.dto.PasswordDto;
import woowacourse.auth.dto.TokenResponse;
import woowacourse.shoppingcart.domain.customer.Customer;

@RequestMapping("/api/customers")
@RestController
public class AuthController {

    private final AuthService authService;

    public AuthController(final AuthService authService) {
        this.authService = authService;
    }

    @PostMapping("/login")
    public ResponseEntity<TokenResponse> login(@Validated @RequestBody LoginRequest loginRequest) {
        authService.validateLogin(loginRequest);
        TokenResponse tokenResponse = authService.createToken(loginRequest);
        return ResponseEntity.ok().body(tokenResponse);
    }

    @GetMapping
    public ResponseEntity<CustomerResponse> findCustomerInfo(HttpServletRequest request) {
        Customer customer = authService.findCustomerByUsername((String) request.getAttribute("username"));
        return ResponseEntity.ok().body(new CustomerResponse(CustomerDto.from(customer)));
    }

    @PostMapping("/password")
    public ResponseEntity<Void> checkPassword(@RequestBody PasswordDto passwordDto, HttpServletRequest request) {
        authService.matchPassword((String)request.getAttribute("username"), passwordDto);
        return ResponseEntity.ok().build();
    }
}
