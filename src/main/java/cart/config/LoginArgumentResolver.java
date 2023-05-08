package cart.config;

import cart.auth.BasicAuthorizationExtractor;
import cart.auth.Login;
import cart.controller.dto.MemberRequest;
import javax.servlet.http.HttpServletRequest;
import org.springframework.core.MethodParameter;
import org.springframework.web.bind.support.WebDataBinderFactory;
import org.springframework.web.context.request.NativeWebRequest;
import org.springframework.web.method.support.HandlerMethodArgumentResolver;
import org.springframework.web.method.support.ModelAndViewContainer;

public class LoginArgumentResolver implements HandlerMethodArgumentResolver {

    private final BasicAuthorizationExtractor extractor = new BasicAuthorizationExtractor();

    @Override
    public boolean supportsParameter(MethodParameter parameter) {
        boolean hasLoginAnnotation = parameter.hasParameterAnnotation(Login.class);
        boolean isMemberRequestType = MemberRequest.class.isAssignableFrom(
            parameter.getParameterType());
        return hasLoginAnnotation && isMemberRequestType;
    }

    @Override
    public Object resolveArgument(MethodParameter parameter, ModelAndViewContainer mavContainer,
                                  NativeWebRequest webRequest, WebDataBinderFactory binderFactory)
        throws Exception {
        HttpServletRequest request = (HttpServletRequest) webRequest.getNativeRequest();
        return extractor.extract(request);
    }
}
