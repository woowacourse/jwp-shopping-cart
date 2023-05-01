package cart.config.auth;

import cart.domain.member.Member;
import cart.dto.member.MemberLoginRequestDto;
import cart.exception.MemberNotFoundException;
import cart.exception.PasswordInvalidException;
import cart.repository.member.MemberRepository;
import cart.util.AuthorizationExtractor;
import cart.util.BasicAuthorizationExtractor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.MethodParameter;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.support.WebDataBinderFactory;
import org.springframework.web.context.request.NativeWebRequest;
import org.springframework.web.method.support.HandlerMethodArgumentResolver;
import org.springframework.web.method.support.ModelAndViewContainer;

@Component
public class LoginMemberArgumentResolver implements HandlerMethodArgumentResolver {

    private static final String AUTHORIZATION_HEADER = "Authorization";

    private final AuthorizationExtractor<MemberLoginRequestDto> authorizationExtractor;
    private final MemberRepository memberRepository;

    @Autowired
    public LoginMemberArgumentResolver(final MemberRepository memberRepository) {
        this.authorizationExtractor = new BasicAuthorizationExtractor();
        this.memberRepository = memberRepository;
    }

    @Override
    public boolean supportsParameter(final MethodParameter parameter) {
        return parameter.hasParameterAnnotation(LoginBasic.class);
    }

    @Override
    public Member resolveArgument(final MethodParameter parameter,
                                  final ModelAndViewContainer mavContainer,
                                  final NativeWebRequest webRequest,
                                  final WebDataBinderFactory binderFactory) throws Exception {
        MemberLoginRequestDto memberLoginRequestDto = getMemberLoginRequestDto(webRequest);

        Member member = memberRepository.findByEmail(memberLoginRequestDto.getEmail())
                .orElseThrow(MemberNotFoundException::new);

        validateMemberLogin(memberLoginRequestDto, member);

        return member;
    }

    private MemberLoginRequestDto getMemberLoginRequestDto(final NativeWebRequest webRequest) {
        String authorization = webRequest.getHeader(AUTHORIZATION_HEADER);
        return authorizationExtractor.extractHeader(authorization);
    }

    private static void validateMemberLogin(final MemberLoginRequestDto memberLoginRequestDto, final Member member) {
        if (!member.isEmailCorrect(memberLoginRequestDto.getEmail())) {
            throw new MemberNotFoundException();
        }

        if (!member.isPasswordCorrect(memberLoginRequestDto.getPassword())) {
            throw new PasswordInvalidException();
        }
    }
}
