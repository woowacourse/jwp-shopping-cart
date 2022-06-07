package woowacourse.auth.ui;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.json.JsonMapper;
import java.util.Objects;
import org.springframework.core.MethodParameter;
import org.springframework.web.bind.support.WebDataBinderFactory;
import org.springframework.web.context.request.NativeWebRequest;
import org.springframework.web.method.support.HandlerMethodArgumentResolver;
import org.springframework.web.method.support.ModelAndViewContainer;
import woowacourse.auth.dto.PermissionCustomerRequest;
import woowacourse.auth.support.AuthenticationPrincipal;
import woowacourse.auth.support.AuthorizationExtractor;
import woowacourse.auth.support.JwtTokenProvider;

public class AuthenticationPrincipalArgumentResolver implements HandlerMethodArgumentResolver {

    private JwtTokenProvider jwtTokenProvider;

    public AuthenticationPrincipalArgumentResolver(JwtTokenProvider jwtTokenProvider) {
        this.jwtTokenProvider = jwtTokenProvider;
    }

    @Override
    public boolean supportsParameter(MethodParameter parameter) {
        return parameter.hasParameterAnnotation(AuthenticationPrincipal.class);
    }

    @Override
    public PermissionCustomerRequest resolveArgument(MethodParameter parameter, ModelAndViewContainer mavContainer,
                                                     NativeWebRequest webRequest, WebDataBinderFactory binderFactory)
            throws JsonProcessingException {
        final String accessToken = AuthorizationExtractor.extract(
                Objects.requireNonNull(webRequest.getHeader(AuthorizationExtractor.AUTHORIZATION)));
        final ObjectMapper objectMapper = new JsonMapper();
        return objectMapper.readValue(jwtTokenProvider.getPayload(accessToken), PermissionCustomerRequest.class);
    }
}
