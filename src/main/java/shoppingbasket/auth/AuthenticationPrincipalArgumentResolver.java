package shoppingbasket.auth;

import javax.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.MethodParameter;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.support.WebDataBinderFactory;
import org.springframework.web.context.request.NativeWebRequest;
import org.springframework.web.method.support.HandlerMethodArgumentResolver;
import org.springframework.web.method.support.ModelAndViewContainer;
import shoppingbasket.exception.PasswordMismatchException;
import shoppingbasket.exception.UnauthenticatedException;
import shoppingbasket.member.entity.MemberEntity;
import shoppingbasket.member.service.MemberService;

@Component
public class AuthenticationPrincipalArgumentResolver implements HandlerMethodArgumentResolver {

    @Autowired
    private MemberService memberService;

    @Override
    public boolean supportsParameter(final MethodParameter parameter) {
        return parameter.hasParameterAnnotation(AuthenticationPrincipal.class);
    }

    @Override
    public AuthInfo resolveArgument(final MethodParameter parameter, final ModelAndViewContainer mavContainer,
                                    final NativeWebRequest webRequest, final WebDataBinderFactory binderFactory)
            throws Exception {
        
        final HttpServletRequest request = (HttpServletRequest) webRequest.getNativeRequest();
        
        final BasicAuthorizationExtractor basicAuthorizationExtractor = new BasicAuthorizationExtractor();
        final AuthInfo authInfo = basicAuthorizationExtractor.extract(request);
        checkAuth(authInfo);

        final MemberEntity member = memberService.findMemberByEmail(authInfo.getEmail());
        validatePassword(member.getPassword(), authInfo.getPassword());

        return authInfo;
    }

    private void checkAuth(final AuthInfo authInfo) {
        if (authInfo == null) {
            throw new UnauthenticatedException("사용자가 선택되지 않았습니다.");
        }
    }

    private void validatePassword(final String memberPassword, final String authPassword) {
        if (!memberPassword.equals(authPassword)) {
            throw new PasswordMismatchException("비밀번호가 일치하지 않습니다.");
        }
    }
}
