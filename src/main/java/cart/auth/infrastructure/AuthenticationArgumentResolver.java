package cart.auth.infrastructure;

import javax.servlet.http.HttpServletRequest;

import org.springframework.core.MethodParameter;
import org.springframework.web.bind.support.WebDataBinderFactory;
import org.springframework.web.context.request.NativeWebRequest;
import org.springframework.web.method.support.HandlerMethodArgumentResolver;
import org.springframework.web.method.support.ModelAndViewContainer;

import cart.auth.application.Auth;
import cart.auth.application.AuthService;
import cart.auth.application.AuthorizationException;
import cart.auth.application.AuthorizationExtractor;
import cart.auth.application.BasicAuthorizationExtractor;
import cart.auth.dto.AuthInfo;

public class AuthenticationArgumentResolver implements HandlerMethodArgumentResolver {

	private final AuthorizationExtractor<AuthInfo> extractor = new BasicAuthorizationExtractor();
	private final AuthService authService;

	public AuthenticationArgumentResolver(final AuthService authService) {
		this.authService = authService;
	}

	@Override
	public boolean supportsParameter(final MethodParameter parameter) {
		return parameter.hasParameterAnnotation(Auth.class);
	}

	@Override
	public Object resolveArgument(final MethodParameter parameter, final ModelAndViewContainer mavContainer,
		final NativeWebRequest webRequest, final WebDataBinderFactory binderFactory) throws Exception {
		final HttpServletRequest request = (HttpServletRequest)webRequest.getNativeRequest();

		final AuthInfo authInfo = extractor.extract(request);
		final String email = authInfo.getEmail();
		final String password = authInfo.getPassword();

		if(authService.checkInvalidLogin(email, password)){
			throw new AuthorizationException();
		}

		return authInfo;
	}
}
