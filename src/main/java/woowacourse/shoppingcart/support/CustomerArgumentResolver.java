package woowacourse.shoppingcart.support;

import org.springframework.core.MethodParameter;
import org.springframework.web.bind.support.WebDataBinderFactory;
import org.springframework.web.context.request.NativeWebRequest;
import org.springframework.web.method.support.HandlerMethodArgumentResolver;
import org.springframework.web.method.support.ModelAndViewContainer;
import woowacourse.auth.application.exception.InvalidTokenException;
import woowacourse.auth.support.AuthorizationExtractor;
import woowacourse.auth.support.JwtTokenProvider;
import woowacourse.shoppingcart.dto.CustomerWithId;

import javax.servlet.http.HttpServletRequest;

public class CustomerArgumentResolver implements HandlerMethodArgumentResolver {

    private final JwtTokenProvider jwtTokenProvider;

    public CustomerArgumentResolver(final JwtTokenProvider jwtTokenProvider) {
        this.jwtTokenProvider = jwtTokenProvider;
    }

    @Override
    public boolean supportsParameter(MethodParameter parameter) {
        return parameter.hasParameterAnnotation(LookUpCustomer.class);
    }

    @Override
    public Object resolveArgument(MethodParameter parameter, ModelAndViewContainer mavContainer,
                                  NativeWebRequest webRequest, WebDataBinderFactory binderFactory) {
        final HttpServletRequest request = (HttpServletRequest) webRequest.getNativeRequest();
        String token = AuthorizationExtractor.extract(request);

        if (token == null) {
            return new CustomerWithId(null);
        }
        if (!jwtTokenProvider.validateToken(token)) {
            throw new InvalidTokenException();
        }
        Long id = Long.parseLong(jwtTokenProvider.getPayload(token));
        return new CustomerWithId(id);
    }
}
