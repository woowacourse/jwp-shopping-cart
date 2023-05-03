package cart.util;

import cart.dto.AuthInfo;
import org.apache.tomcat.util.codec.binary.Base64;
import org.springframework.core.MethodParameter;
import org.springframework.web.bind.support.WebDataBinderFactory;
import org.springframework.web.context.request.NativeWebRequest;
import org.springframework.web.method.support.HandlerMethodArgumentResolver;
import org.springframework.web.method.support.ModelAndViewContainer;
import javax.servlet.http.HttpServletRequest;
import java.util.Arrays;
import java.util.List;

public class UserInfoArgumentResolver implements HandlerMethodArgumentResolver {

    private static final int AUTH_VALUE_INDEX = 1;
    private static final String DELIMITER = ":";
    private static final int EMAIL_INDEX = 0;
    private static final int PASSWORD_INDEX = 1;

    @Override
    public boolean supportsParameter(final MethodParameter parameter) {
        return parameter.getParameterType().equals(AuthInfo.class);
    }

    @Override
    public AuthInfo resolveArgument(final MethodParameter parameter, final ModelAndViewContainer mavContainer, final NativeWebRequest webRequest, final WebDataBinderFactory binderFactory) throws Exception {
        HttpServletRequest request = (HttpServletRequest) webRequest.getNativeRequest();

        final String credentials = request.getHeader("Authorization");

        final List<String> authorization = Arrays.asList(credentials.split(" "));
        final String authValue = authorization.get(AUTH_VALUE_INDEX);

        return encode(authValue);
    }

    private AuthInfo encode(final String authValue) {
        final byte[] decodedBytes = Base64.decodeBase64(authValue);
        final String userInfo = new String(decodedBytes);

        final String[] userInfos = userInfo.split(DELIMITER);
        final String email = userInfos[EMAIL_INDEX];
        final String password = userInfos[PASSWORD_INDEX];

        return new AuthInfo(email, password);
    }
}
