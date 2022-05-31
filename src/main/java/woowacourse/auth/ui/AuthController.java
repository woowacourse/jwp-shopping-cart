package woowacourse.auth.ui;

import com.fasterxml.jackson.core.JsonProcessingException;
import javax.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import woowacourse.auth.application.AuthService;
import woowacourse.auth.dto.SignInDto;
import woowacourse.auth.dto.TokenResponseDto;

@RestController
@RequestMapping("/api/auth")
public class AuthController {

    private final AuthService authService;

    public AuthController(final AuthService authService) {
        this.authService = authService;
    }

    @PostMapping("/login")
    public ResponseEntity<TokenResponseDto> login(@Valid @RequestBody SignInDto signInDto)
            throws JsonProcessingException {
        final TokenResponseDto response = authService.login(signInDto);
        return ResponseEntity.ok(response);
    }
}