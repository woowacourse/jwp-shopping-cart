package cart.auth.infrastructure;

import javax.servlet.http.HttpServletRequest;

import org.springframework.core.MethodParameter;
import org.springframework.web.bind.support.WebDataBinderFactory;
import org.springframework.web.context.request.NativeWebRequest;
import org.springframework.web.method.support.HandlerMethodArgumentResolver;
import org.springframework.web.method.support.ModelAndViewContainer;

import cart.auth.dto.AuthInfo;

public class AuthenticationPrincipalArgumentResolver implements HandlerMethodArgumentResolver {

	private final AuthorizationExtractor<AuthInfo> extractor = new BasicAuthorizationExtractor();

	@Override
	public boolean supportsParameter(final MethodParameter parameter) {
		return parameter.hasParameterAnnotation(AuthenticationPrincipal.class);
	}

	@Override
	public Object resolveArgument(final MethodParameter parameter, final ModelAndViewContainer mavContainer,
		final NativeWebRequest webRequest, final WebDataBinderFactory binderFactory) throws Exception {
		final HttpServletRequest request = (HttpServletRequest)webRequest.getNativeRequest();
		return extractor.extract(request);
	}
}
