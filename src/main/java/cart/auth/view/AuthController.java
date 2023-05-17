package cart.auth.view;

import javax.servlet.http.HttpServletRequest;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;

import cart.auth.application.AuthService;
import cart.auth.application.AuthorizationExtractor;
import cart.auth.application.BasicAuthorizationExtractor;
import cart.auth.dto.AuthInfo;
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
		MemberResponse member = authService.findMember(email);

		return ResponseEntity.ok().body(member);
	}
}
