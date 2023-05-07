package cart.auth;

import cart.exception.CustomAuthException;
import org.apache.tomcat.util.codec.binary.Base64;
import org.springframework.core.MethodParameter;
import org.springframework.web.bind.support.WebDataBinderFactory;
import org.springframework.web.context.request.NativeWebRequest;
import org.springframework.web.method.support.HandlerMethodArgumentResolver;
import org.springframework.web.method.support.ModelAndViewContainer;

public class AuthenticationPrincipalArgumentResolver implements HandlerMethodArgumentResolver {

    private static final String DELIMITER = ":";
    private static final String AUTHENTICATION_HEADER_NAME = "Basic";

    @Override
    public boolean supportsParameter(MethodParameter parameter) {
        return parameter.hasParameterAnnotation(AuthenticationPrincipal.class);
    }

    @Override
    public Object resolveArgument(MethodParameter parameter, ModelAndViewContainer mavContainer,
                                  NativeWebRequest webRequest, WebDataBinderFactory binderFactory) {
        String header = getValidateHeader(webRequest);
        String basicHeaderValue = header.substring(AUTHENTICATION_HEADER_NAME.length()).trim();
        byte[] decodedHeaderValue = Base64.decodeBase64(basicHeaderValue);

        String decodedStringHeaderValue = new String(decodedHeaderValue);
        validateDecodedHeaderValue(decodedStringHeaderValue);
        String[] splitDecodedStringHeaderValue = decodedStringHeaderValue.split(DELIMITER);
        int id = Integer.parseInt(splitDecodedStringHeaderValue[0]);
        String email = splitDecodedStringHeaderValue[1];
        String password = splitDecodedStringHeaderValue[2];

        return new AuthMember(id, email, password);
    }

    private void validateDecodedHeaderValue(final String decodedStringHeaderValue) {
        try {
            if (!decodedStringHeaderValue.contains(DELIMITER)) {
                throw new CustomAuthException("잘못된 인증 정보입니다.");
            }
            String[] decodedSplitHeaderValue = decodedStringHeaderValue.split(DELIMITER);
            Integer.parseInt(decodedSplitHeaderValue[0]);
            validateDecodedSplitHeaderValueSize(decodedSplitHeaderValue);
        } catch (NumberFormatException exception) {
            throw new IllegalArgumentException("잘못된 인증 정보입니다.");
        }
    }

    private void validateDecodedSplitHeaderValueSize(final String[] decodedSplitHeaderValue) {
        if (decodedSplitHeaderValue.length != 3) {
            throw new CustomAuthException("잘못된 인증 정보입니다.");
        }
    }

    private String getValidateHeader(final NativeWebRequest webRequest) {
        String authorizationHeader = webRequest.getHeader("Authorization");
        validateHasAuthorizationHeader(authorizationHeader);
        String basicHeaderValue = authorizationHeader.substring("Basic".length()).trim();
        validateHasBasicHeaderValue(basicHeaderValue);
        return authorizationHeader;
    }

    private void validateHasBasicHeaderValue(final String basicHeaderValue) {
        if (basicHeaderValue == null) {
            throw new CustomAuthException("Basic 인증 정보가 없습니다.");
        }
    }

    private void validateHasAuthorizationHeader(final String authorizationHeader) {
        if (authorizationHeader == null) {
            throw new CustomAuthException("인증 정보가 없습니다");
        }
        if (!authorizationHeader.contains(AUTHENTICATION_HEADER_NAME)) {
            throw new CustomAuthException("인증 정보가 없습니다");
        }
    }

}
