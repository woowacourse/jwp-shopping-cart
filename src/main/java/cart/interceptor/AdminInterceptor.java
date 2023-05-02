package cart.interceptor;

import org.springframework.util.Base64Utils;
import org.springframework.web.servlet.HandlerInterceptor;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class AdminInterceptor implements HandlerInterceptor {

    private static final String AUTHORIZATION_HEADER = "authorization";
    private static final String BASIC = "Basic";
    private static final String AUTH_EMAIL = "admin@wootech.com";

    @Override
    public boolean preHandle(final HttpServletRequest request, final HttpServletResponse response, final Object handler) throws Exception {
        final String authValue = request.getHeader(AUTHORIZATION_HEADER);
        if (authValue == null) {
            throw new Exception("로그인을 해주세요.");
        }

        if (!authValue.startsWith(BASIC)) {
            throw new Exception("지원되지 않는 인코딩 타입입니다.");
        }

        final String authValueWithBase64Encoding = authValue.substring(BASIC.length()).trim();
        final String auth = new String(Base64Utils.decodeFromString(authValueWithBase64Encoding));

        final String[] emailAndPasswordWithDecryption = auth.split(":");
        final String email = emailAndPasswordWithDecryption[0];

        if (!AUTH_EMAIL.equals(email)) {
            throw new Exception("관리자만 접근이 가능합니다.");
        }

        return HandlerInterceptor.super.preHandle(request, response, handler);
    }

}
