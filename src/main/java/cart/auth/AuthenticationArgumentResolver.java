package cart.auth;

import cart.dao.MemberDao;
import cart.domain.entity.MemberEntity;
import cart.dto.MemberDto;
import cart.exception.AuthenticationException;
import org.springframework.core.MethodParameter;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.support.WebDataBinderFactory;
import org.springframework.web.context.request.NativeWebRequest;
import org.springframework.web.method.support.HandlerMethodArgumentResolver;
import org.springframework.web.method.support.ModelAndViewContainer;

@Component
public class AuthenticationArgumentResolver implements HandlerMethodArgumentResolver {

    private final AuthenticationExtractor<MemberEntity> authenticationExtractor;
    private final MemberDao memberDao;

    public AuthenticationArgumentResolver(final AuthenticationExtractor<MemberEntity> authenticationExtractor, final MemberDao memberDao) {
        this.authenticationExtractor = authenticationExtractor;
        this.memberDao = memberDao;
    }

    @Override
    public boolean supportsParameter(MethodParameter parameter) {
        return parameter.hasParameterAnnotation(AuthenticationPrincipal.class);
    }

    @Override
    public Object resolveArgument(MethodParameter parameter, ModelAndViewContainer mavContainer, NativeWebRequest webRequest, WebDataBinderFactory binderFactory) {
        MemberEntity extractedMember = authenticationExtractor.extract(webRequest);
        validateExtraction(extractedMember);

        MemberEntity selectedMember = memberDao.selectByEmailAndPassword(extractedMember);
        validateMember(selectedMember);

        return AuthenticatedMember.from(selectedMember);
    }

    private void validateExtraction(MemberEntity extractedMember){
        if(extractedMember == null){
            throw new AuthenticationException("사용자 인증이 필요합니다.");
        }
    }

    private void validateMember(MemberEntity selectedMember){
        if (selectedMember == null) {
            throw new AuthenticationException("사용자 인증에 실패하였습니다.");
        }
    }

}
