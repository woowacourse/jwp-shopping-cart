package cart.auth;

import cart.dao.MemberDao;
import cart.entity.MemberEntity;
import cart.excpetion.AuthenticationException;
import org.springframework.core.MethodParameter;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.support.WebDataBinderFactory;
import org.springframework.web.context.request.NativeWebRequest;
import org.springframework.web.method.support.HandlerMethodArgumentResolver;
import org.springframework.web.method.support.ModelAndViewContainer;

@Component
public class MemberArgumentResolver implements HandlerMethodArgumentResolver {

    private static final String AUTHORIZATION = "Authorization";
    private final MemberDao memberDao;
    private final BasicAuthorizationExtractor basicAuthorizationExtractor;

    public MemberArgumentResolver(final MemberDao memberDao, final BasicAuthorizationExtractor basicAuthorizationExtractor) {
        this.memberDao = memberDao;
        this.basicAuthorizationExtractor = basicAuthorizationExtractor;
    }

    @Override
    public boolean supportsParameter(final MethodParameter parameter) {
        return parameter.hasParameterAnnotation(LoginUser.class);
    }

    @Override
    public Object resolveArgument(final MethodParameter parameter, final ModelAndViewContainer mavContainer, final NativeWebRequest webRequest, final WebDataBinderFactory binderFactory) throws Exception {
        final String loginToken = webRequest.getHeader(AUTHORIZATION);
        if (loginToken == null) {
            throw new AuthenticationException("로그인 하지 않았습니다");
        }
        final LoginRequest extract = basicAuthorizationExtractor.extract(loginToken);
        final MemberEntity findMember = memberDao.findBy(extract.getEmail(), extract.getPassword())
                .orElseThrow(() -> {
                    throw new AuthenticationException("존재 하지 않는 유저의 로그인 정보입니다.");
                });
        return new MemberInfo(findMember.getId(), findMember.getEmail());
    }
}
