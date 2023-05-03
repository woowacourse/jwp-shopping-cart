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
import cart.dao.user.UserDao;
import cart.domain.user.User;

@Component
public class LoginUserArgumentResolver implements HandlerMethodArgumentResolver {

	private final BasicAuthorizationExtractor basicAuthorizationExtractor;
	private final UserDao userDao;

	public LoginUserArgumentResolver(final BasicAuthorizationExtractor basicAuthorizationExtractor,
		final UserDao userDao) {
		this.basicAuthorizationExtractor = basicAuthorizationExtractor;
		this.userDao = userDao;
	}

	@Override
	public boolean supportsParameter(final MethodParameter parameter) {
		boolean isLoginUserAnnotation = parameter.getParameterAnnotation(LoginUser.class) != null;
		boolean isUserClass = AuthInfo.class.equals(parameter.getParameterType());

		return isLoginUserAnnotation && isUserClass;
	}

	@Override
	public Object resolveArgument(final MethodParameter parameter, final ModelAndViewContainer mavContainer,
		final NativeWebRequest webRequest, final WebDataBinderFactory binderFactory) throws Exception {

		final HttpServletRequest request = webRequest.getNativeRequest(HttpServletRequest.class);
		final AuthInfo authInfo = basicAuthorizationExtractor.extract(request);

		final String email = authInfo.getEmail();
		final Optional<User> optionalUser = userDao.findUserByEmail(email);

		final User user = validateUser(authInfo, optionalUser);

		return new AuthUser(user);
	}

	private static User validateUser(final AuthInfo authInfo, final Optional<User> optionalUser) {
		final User user = optionalUser
			.orElseThrow(() -> new IllegalArgumentException("해당하는 유저가 없습니다."));
		if (!Objects.equals(user.getPassword(), authInfo.getPassword())) {
			throw new IllegalArgumentException("올바른 패스워드가 아닙니다.");
		}
		return user;
	}
}
