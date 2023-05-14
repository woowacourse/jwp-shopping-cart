package cart.auth;

import cart.auth.credential.Credential;
import cart.auth.credential.CredentialDao;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.springframework.web.servlet.HandlerInterceptor;

public class AuthenticationInterceptor implements HandlerInterceptor {

    public static final String AUTH_KEY = "memberId";
    private final BasicAuthorizationExtractor basicAuthorizationExtractor;
    private final CredentialDao credentialDao;

    public AuthenticationInterceptor(BasicAuthorizationExtractor basicAuthorizationExtractor,
            CredentialDao credentialDao) {
        this.basicAuthorizationExtractor = basicAuthorizationExtractor;
        this.credentialDao = credentialDao;
    }

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        Credential credential = basicAuthorizationExtractor.extract(request);
        Credential savedCredential = credentialDao.findByEmail(credential.getEmail())
                .orElseThrow(()-> new IllegalArgumentException("존재하지 않는 회원입니다"));
        if(!credential.getPassword().equals(savedCredential.getPassword())) {
            throw new IllegalArgumentException("비밀번호가 일치하지 않습니다");
        }
        request.setAttribute(AUTH_KEY, savedCredential.getMemberId());
        return true;
    }

}
