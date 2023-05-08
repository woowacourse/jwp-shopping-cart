package cart.ui;

import cart.auth.AuthorizationExtractor;
import cart.auth.BasicAuthorizationExtractor;
import cart.entity.Member;
import org.springframework.core.MethodParameter;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.support.WebDataBinderFactory;
import org.springframework.web.context.request.NativeWebRequest;
import org.springframework.web.method.support.HandlerMethodArgumentResolver;
import org.springframework.web.method.support.ModelAndViewContainer;

import javax.servlet.http.HttpServletRequest;

@Component
public class CartAuthenticationArgumentResolver implements HandlerMethodArgumentResolver {

    private final AuthorizationExtractor<Member> basicAuthorizationExtractor;

    public CartAuthenticationArgumentResolver(BasicAuthorizationExtractor basicAuthorizationExtractor) {
        this.basicAuthorizationExtractor = basicAuthorizationExtractor;
    }

    @Override
    public boolean supportsParameter(MethodParameter parameter) {
        return parameter.hasParameterAnnotation(CartAuthentication.class);
    }

    @Override
    public Object resolveArgument(MethodParameter parameter, ModelAndViewContainer mavContainer, NativeWebRequest webRequest, WebDataBinderFactory binderFactory) {
        HttpServletRequest request = (HttpServletRequest) webRequest.getNativeRequest();

        Member member = basicAuthorizationExtractor.extract(request);

        if (member == null || member.getEmail() == null || member.getPassword() == null) {
            throw new AuthorizationException();
        }

        if (member.getEmail().isBlank() || member.getPassword().isBlank()) {
            throw new AuthorizationException();
        }

        return member.getEmail();
    }
}
