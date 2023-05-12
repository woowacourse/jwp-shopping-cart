package cart.auth.infrastructure;

import cart.auth.dto.UserInfo;
import org.springframework.core.MethodParameter;
import org.springframework.web.bind.support.WebDataBinderFactory;
import org.springframework.web.context.request.NativeWebRequest;
import org.springframework.web.method.support.HandlerMethodArgumentResolver;
import org.springframework.web.method.support.ModelAndViewContainer;

import javax.servlet.http.HttpServletRequest;
import java.util.List;
import java.util.Optional;

public class AuthenticationPrincipalArgumentResolver implements HandlerMethodArgumentResolver {

    private final BasicAuthorizationExtractor basicAuthorizationExtractor = new BasicAuthorizationExtractor();

    @Override
    public boolean supportsParameter(final MethodParameter parameter) {
        return parameter.hasParameterAnnotation(Auth.class);
    }

    @Override
    public Object resolveArgument(final MethodParameter parameter, final ModelAndViewContainer mavContainer,
                                  final NativeWebRequest webRequest, final WebDataBinderFactory binderFactory) {
        final Optional<HttpServletRequest> maybeRequest = Optional.ofNullable(
                webRequest.getNativeRequest(HttpServletRequest.class));

        final List<String> credentials = maybeRequest
                .map(this.basicAuthorizationExtractor::extract)
                .orElseThrow(() -> new RuntimeException("request가 존재하지 않습니다."));
        return UserInfo.of(credentials.get(0), credentials.get(1));
    }
}
