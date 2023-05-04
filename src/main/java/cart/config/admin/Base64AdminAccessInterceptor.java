package cart.config.admin;

import cart.config.auth.Base64AuthInterceptor;
import cart.exception.customExceptions.AdminAccessException;
import org.springframework.util.Base64Utils;
import org.springframework.web.servlet.HandlerInterceptor;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class Base64AdminAccessInterceptor implements HandlerInterceptor {

    public static final String ADMIN_EMAIL = "admin@wootech.com";
    public static final String ADMIN_NAME = "admin";

    @Override
    public boolean preHandle(final HttpServletRequest request, final HttpServletResponse response, final Object handler) throws Exception {
        final String authValue = request.getHeader(Base64AuthInterceptor.AUTHORIZATION_HEADER);
        final String authValueWithBase64Encoding = authValue.substring(Base64AuthInterceptor.BASIC.length()).trim();
        final String auth = new String(Base64Utils.decodeFromString(authValueWithBase64Encoding));

        final String[] emailAndPasswordWithDecryption = auth.split(":");
        final String email = emailAndPasswordWithDecryption[0];

        if (!ADMIN_EMAIL.equals(email)) {
            throw new AdminAccessException("관리자만 접근이 가능합니다.");
        }

        return HandlerInterceptor.super.preHandle(request, response, handler);
    }
}
