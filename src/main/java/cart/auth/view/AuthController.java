package cart.auth.view;

import javax.servlet.http.HttpServletRequest;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;

import cart.auth.application.AuthService;
import cart.auth.application.AuthorizationException;
import cart.auth.dto.AuthInfo;
import cart.auth.infrastructure.AuthorizationExtractor;
import cart.auth.infrastructure.BasicAuthorizationExtractor;
import cart.service.response.MemberResponse;

public class AuthController {
	private static final String USERNAME_FIELD = "email";
	private static final String PASSWORD_FIELD = "password";
	private AuthorizationExtractor<AuthInfo> basicAuthorizationExtractor = new BasicAuthorizationExtractor();
	private AuthService authService;

	public AuthController(AuthService authService) {
		this.authService = authService;
	}

	@GetMapping("/carts")
	public ResponseEntity showCart(HttpServletRequest request) {
		final AuthInfo authInfo = basicAuthorizationExtractor.extract(request);
		String email = authInfo.getEmail();
		String password = authInfo.getPassword();

		if (authService.checkInvalidLogin(email, password)) {
			throw new AuthorizationException();
		}

		MemberResponse member = authService.findMember(email);
		return ResponseEntity.ok().body(member);
	}
}
