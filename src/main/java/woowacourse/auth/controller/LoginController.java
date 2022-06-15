package woowacourse.auth.controller;

import javax.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import woowacourse.auth.dto.LoginRequest;
import woowacourse.auth.dto.LoginResponse;
import woowacourse.auth.service.AuthService;

@RestController
@RequestMapping("/api")
public class LoginController {

    private final AuthService loginService;

    public LoginController(final AuthService loginService) {
        this.loginService = loginService;
    }

    @PostMapping("/login")
    public ResponseEntity<LoginResponse> login(@RequestBody @Valid final LoginRequest request) {
        return ResponseEntity.ok(loginService.login(request));
    }
}
