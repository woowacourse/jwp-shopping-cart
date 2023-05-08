package cart.config.auth;

import java.util.Objects;
import java.util.Optional;

import javax.servlet.http.HttpServletRequest;

import org.springframework.core.MethodParameter;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.support.WebDataBinderFactory;
import org.springframework.web.context.request.NativeWebRequest;
import org.springframework.web.method.support.HandlerMethodArgumentResolver;
import org.springframework.web.method.support.ModelAndViewContainer;

import cart.config.auth.dto.AuthInfo;
import cart.config.auth.dto.AuthUser;
import cart.domain.user.User;
import cart.exception.LoginException;
import cart.service.user.UserService;

@Component
public class LoginUserArgumentResolver implements HandlerMethodArgumentResolver {

	private final BasicAuthorizationExtractor basicAuthorizationExtractor;
	private final UserService userService;

	public LoginUserArgumentResolver(final BasicAuthorizationExtractor basicAuthorizationExtractor,
		final UserService userService) {
		this.basicAuthorizationExtractor = basicAuthorizationExtractor;
		this.userService = userService;
	}

	@Override
	public boolean supportsParameter(final MethodParameter parameter) {
		boolean isLoginUserAnnotation = parameter.getParameterAnnotation(LoginUser.class) != null;
		boolean isUserClass = AuthUser.class.equals(parameter.getParameterType());

		return isLoginUserAnnotation && isUserClass;
	}

	@Override
	public Object resolveArgument(final MethodParameter parameter, final ModelAndViewContainer mavContainer,
		final NativeWebRequest webRequest, final WebDataBinderFactory binderFactory) throws Exception {

		final HttpServletRequest request = webRequest.getNativeRequest(HttpServletRequest.class);
		final AuthInfo authInfo = basicAuthorizationExtractor.extract(request);

		final User user = validateAuthInfo(authInfo);

		return new AuthUser(user);
	}

	private User validateAuthInfo(final AuthInfo authInfo) {
		final String email = authInfo.getEmail();
		final Optional<User> optionalUser = userService.findUserByEmail(email);

		final User user = optionalUser
			.orElseThrow(() -> new IllegalArgumentException("해당하는 유저가 없습니다."));

		if (!Objects.equals(user.getPassword(), authInfo.getPassword())) {
			throw LoginException.EXCEPTION;
		}

		return user;
	}

}
