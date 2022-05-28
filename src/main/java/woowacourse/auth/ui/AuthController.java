package woowacourse.auth.ui;

import javax.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import woowacourse.shoppingcart.dto.SignInDto;
import woowacourse.shoppingcart.dto.TokenResponseDto;
import woowacourse.shoppingcart.service.CustomerService;

@RestController
@RequestMapping("/api/auth")
public class AuthController {

        private final CustomerService customerService;

        public AuthController(final CustomerService customerService) {
            this.customerService = customerService;
        }

        @PostMapping("/login")
        public ResponseEntity<TokenResponseDto> login(@Valid @RequestBody SignInDto signInDto) {
            final TokenResponseDto response = customerService.login(signInDto);
            return  ResponseEntity.ok(response);
        }
    }