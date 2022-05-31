package woowacourse.auth.config;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.http.HttpStatus;
import org.springframework.web.servlet.HandlerInterceptor;

import lombok.RequiredArgsConstructor;
import woowacourse.auth.support.AuthorizationExtractor;
import woowacourse.auth.support.JwtTokenProvider;

@RequiredArgsConstructor
public class LoginInterceptor implements HandlerInterceptor {

	private final JwtTokenProvider tokenProvider;

	@Override
	public boolean preHandle(
		HttpServletRequest request, HttpServletResponse response, Object handler) {

		if (isSignUpRequest(request)) {
			return true;
		}

		String token = AuthorizationExtractor.extract(request);
		if (isUnauthorizedRequest(token)) {
			response.setStatus(HttpStatus.UNAUTHORIZED.value());
			return false;
		}
		return true;
	}

	private boolean isSignUpRequest(HttpServletRequest request) {
		return request.getRequestURI().contains("/customer")
			&& request.getMethod().equalsIgnoreCase("post");
	}

	private boolean isUnauthorizedRequest(String nickname) {
		return nickname == null || !tokenProvider.validateToken(nickname);
	}
}
