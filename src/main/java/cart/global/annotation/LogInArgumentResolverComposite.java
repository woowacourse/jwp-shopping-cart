package cart.global.annotation;

import cart.auth.AuthAccount;
import cart.global.exception.auth.InvalidAuthorizationTypeException;
import org.springframework.core.MethodParameter;
import org.springframework.web.bind.support.WebDataBinderFactory;
import org.springframework.web.context.request.NativeWebRequest;
import org.springframework.web.method.support.HandlerMethodArgumentResolver;
import org.springframework.web.method.support.ModelAndViewContainer;

import java.util.List;

public class LogInArgumentResolverComposite implements HandlerMethodArgumentResolver {

    private final List<LogInAuthorizationArgumentResolver> resolvers =
            List.of(
                    new BasicAuthorizationArgumentResolver(),
                    new BearerAuthorizationArgumentResolver()
            );

    @Override
    public boolean supportsParameter(final MethodParameter parameter) {
        return parameter.hasParameterAnnotation(LogIn.class) &&
                AuthAccount.class.isAssignableFrom(parameter.getParameterType());
    }

    @Override
    public Object resolveArgument(final MethodParameter parameter,
                                  final ModelAndViewContainer mavContainer,
                                  final NativeWebRequest webRequest,
                                  final WebDataBinderFactory binderFactory
    ) throws Exception {

        for (final LogInAuthorizationArgumentResolver resolver : resolvers) {
            final AuthAccount authAccount =
                    (AuthAccount) resolver.resolveArgument(parameter,
                                                           mavContainer,
                                                           webRequest,
                                                           binderFactory
                    );

            if (authAccount != null) {
                return authAccount;
            }
        }

        throw new InvalidAuthorizationTypeException("올바르지 않는 인증 형태입니다.");
    }
}
