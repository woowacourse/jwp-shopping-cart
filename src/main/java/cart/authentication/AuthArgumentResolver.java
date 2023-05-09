package cart.authentication;

import org.springframework.core.MethodParameter;
import org.springframework.web.bind.support.WebDataBinderFactory;
import org.springframework.web.context.request.NativeWebRequest;
import org.springframework.web.method.support.HandlerMethodArgumentResolver;
import org.springframework.web.method.support.ModelAndViewContainer;

import javax.servlet.http.HttpServletRequest;

public class AuthArgumentResolver implements HandlerMethodArgumentResolver {

    @Override
    public boolean supportsParameter(MethodParameter parameter) {
        return parameter.getParameterType()
                .equals(Auth.class);
    }

    @Override
    public Object resolveArgument(MethodParameter parameter,
                                  ModelAndViewContainer mavContainer,
                                  NativeWebRequest webRequest,
                                  WebDataBinderFactory binderFactory) throws Exception {
        HttpServletRequest httpServletRequest = webRequest.getNativeRequest(HttpServletRequest.class);
        BasicAuthorizationExtractor basicAuthorizationExtractor = new BasicAuthorizationExtractor();
        AuthInfo extract = basicAuthorizationExtractor.extract(httpServletRequest);
        return new Auth(extract.getEmail());
    }

}
