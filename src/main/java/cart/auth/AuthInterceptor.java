package cart.auth;

import cart.exeception.AuthorizationException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.springframework.http.HttpHeaders;
import org.springframework.web.servlet.HandlerInterceptor;

public class AuthInterceptor implements HandlerInterceptor {

    private final BasicCredentialExtractor basicCredentialExtractor;
    private final AuthDao authDao;
    private final CredentialThreadLocal credentialThreadLocal;

    public AuthInterceptor(final BasicCredentialExtractor basicCredentialExtractor, final AuthDao authDao,
                           final CredentialThreadLocal credentialThreadLocal) {
        this.basicCredentialExtractor = basicCredentialExtractor;
        this.authDao = authDao;
        this.credentialThreadLocal = credentialThreadLocal;
    }

    @Override
    public boolean preHandle(final HttpServletRequest request, final HttpServletResponse response, final Object handler) {
        final String authInfo = request.getHeader(HttpHeaders.AUTHORIZATION);
        final Credential requestMember = basicCredentialExtractor.extractMemberInfo(authInfo);
        final Credential registeredMember = authDao.findMemberByEmail(requestMember.getEmail())
                .orElseThrow(() -> new AuthorizationException("해당 이메일로 등록된 사용자가 없습니다."));
        if (requestMember.getPassword().equals(registeredMember.getPassword())) {
            credentialThreadLocal.set(registeredMember);
            return true;
        }
        throw new AuthorizationException("인증 정보가 잘못되었습니다.");
    }
}
