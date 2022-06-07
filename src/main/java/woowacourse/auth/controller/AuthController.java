package woowacourse.auth.controller;

import javax.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import woowacourse.auth.application.AuthService;
import woowacourse.auth.dto.SignInRequestDto;
import woowacourse.auth.dto.SignInResponseDto;

@RestController
@RequestMapping("/api/auth")
public class AuthController {

    private final AuthService authService;

    public AuthController(final AuthService authService) {
        this.authService = authService;
    }

    @PostMapping("/login")
    public ResponseEntity<SignInResponseDto> login(@Valid @RequestBody final SignInRequestDto signInDto) {
        final SignInResponseDto response = authService.login(signInDto);
        return ResponseEntity.ok(response);
    }
}