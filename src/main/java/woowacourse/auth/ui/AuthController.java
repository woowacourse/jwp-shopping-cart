package woowacourse.auth.ui;

import javax.validation.Valid;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import lombok.RequiredArgsConstructor;
import woowacourse.auth.application.AuthService;
import woowacourse.auth.dto.ExceptionResponse;
import woowacourse.auth.dto.token.TokenRequest;
import woowacourse.auth.dto.token.TokenResponse;
import woowacourse.exception.BusinessException;

@RestController
@RequestMapping("/auth")
@RequiredArgsConstructor
public class AuthController {

	private final AuthService authService;

	@PostMapping("/login")
	public ResponseEntity<TokenResponse> login(@RequestBody @Valid TokenRequest tokenRequest) {
		return ResponseEntity.ok(authService.login(tokenRequest));
	}
}
