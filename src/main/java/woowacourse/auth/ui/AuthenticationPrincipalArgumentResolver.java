package woowacourse.auth.ui;

import org.springframework.core.MethodParameter;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.support.WebDataBinderFactory;
import org.springframework.web.context.request.NativeWebRequest;
import org.springframework.web.method.support.HandlerMethodArgumentResolver;
import org.springframework.web.method.support.ModelAndViewContainer;
import woowacourse.auth.support.AuthenticationPrincipal;
import woowacourse.shoppingcart.exception.PayloadNotFoundException;

@Component
public class AuthenticationPrincipalArgumentResolver implements HandlerMethodArgumentResolver {

    private static final String TOKEN_PAYLOAD = "TOKEN_PAYLOAD";

    @Override
    public boolean supportsParameter(final MethodParameter parameter) {
        return parameter.hasParameterAnnotation(AuthenticationPrincipal.class);
    }

    @Override
    public Long resolveArgument(final MethodParameter parameter, final ModelAndViewContainer mavContainer, final NativeWebRequest webRequest, final WebDataBinderFactory binderFactory) {
        final String payload = (String) webRequest.getAttribute(TOKEN_PAYLOAD, NativeWebRequest.SCOPE_REQUEST);
        validatePayload(payload);

        return Long.parseLong(payload);
    }

    private void validatePayload(final String payload) {
        if (payload == null) {
            throw new PayloadNotFoundException();
        }
    }
}
