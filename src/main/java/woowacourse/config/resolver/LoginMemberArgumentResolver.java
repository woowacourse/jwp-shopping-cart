package woowacourse.config.resolver;

import java.util.Map;
import javax.servlet.http.HttpServletRequest;
import org.springframework.core.MethodParameter;
import org.springframework.web.bind.support.WebDataBinderFactory;
import org.springframework.web.context.request.NativeWebRequest;
import org.springframework.web.method.support.HandlerMethodArgumentResolver;
import org.springframework.web.method.support.ModelAndViewContainer;
import woowacourse.auth.domain.LoginMemberPrincipal;
import woowacourse.auth.ui.dto.LoginMember;
import woowacourse.exception.UnauthorizedException;

public class LoginMemberArgumentResolver implements HandlerMethodArgumentResolver {
    @Override
    public boolean supportsParameter(MethodParameter parameter) {
        return parameter.hasParameterAnnotation(LoginMemberPrincipal.class);
    }

    @Override
    public Object resolveArgument(MethodParameter parameter, ModelAndViewContainer mavContainer,
                                  NativeWebRequest webRequest, WebDataBinderFactory binderFactory) {
        final HttpServletRequest httpServletRequest = (HttpServletRequest) webRequest.getNativeRequest();
        final Map<String, Object> payload = (Map<String, Object>) httpServletRequest.getAttribute("payload");
        final long id = Long.parseLong(String.valueOf(payload.getOrDefault("id", 0)));

        try {
            return LoginMember.from(id);
        } catch (Exception e) {
            throw new UnauthorizedException();
        }
    }
}
