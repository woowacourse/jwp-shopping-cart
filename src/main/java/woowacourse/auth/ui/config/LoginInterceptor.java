package woowacourse.auth.ui.config;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.http.HttpMethod;
import org.springframework.web.servlet.HandlerInterceptor;

import lombok.RequiredArgsConstructor;
import woowacourse.auth.application.AuthService;
import woowacourse.auth.support.AuthorizationExtractor;

@RequiredArgsConstructor
public class LoginInterceptor implements HandlerInterceptor {

	private final AuthService authService;

	@Override
	public boolean preHandle(
		HttpServletRequest request, HttpServletResponse response, Object handler) {

		if (HttpMethod.OPTIONS.matches(request.getMethod())) {
			return true;
		}

		if (isSignUpRequest(request)) {
			return true;
		}

		String token = AuthorizationExtractor.extract(request);
		request.setAttribute("customer", authService.findCustomerByToken(token));

		return true;
	}

	private boolean isSignUpRequest(HttpServletRequest request) {
		return request.getRequestURI().contains("/customer")
			&& HttpMethod.POST.matches(request.getMethod());
	}
}
