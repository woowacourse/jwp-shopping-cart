package cart.global.annotation;

import cart.auth.AuthAccount;
import org.springframework.core.MethodParameter;
import org.springframework.http.HttpHeaders;
import org.springframework.web.bind.support.WebDataBinderFactory;
import org.springframework.web.context.request.NativeWebRequest;
import org.springframework.web.method.support.HandlerMethodArgumentResolver;
import org.springframework.web.method.support.ModelAndViewContainer;

public abstract class LogInAuthorizationArgumentResolver implements HandlerMethodArgumentResolver {

    @Override
    public boolean supportsParameter(final MethodParameter parameter) {
        return true;
    }

    @Override
    public Object resolveArgument(final MethodParameter parameter,
                                  final ModelAndViewContainer mavContainer,
                                  final NativeWebRequest webRequest,
                                  final WebDataBinderFactory binderFactory
    ) throws Exception {

        final String header = webRequest.getHeader(HttpHeaders.AUTHORIZATION);

        if (canDecoding(header)) {
            return decode(header);
        }

        return null;
    }

    protected abstract boolean canDecoding(final String header);

    protected abstract AuthAccount decode(final String header);
}
