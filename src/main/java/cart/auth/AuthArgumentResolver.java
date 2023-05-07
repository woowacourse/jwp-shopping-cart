package cart.auth;

import cart.domain.member.Member;
import cart.persistence.MembersDao;
import org.springframework.core.MethodParameter;
import org.springframework.web.bind.support.WebDataBinderFactory;
import org.springframework.web.context.request.NativeWebRequest;
import org.springframework.web.method.support.HandlerMethodArgumentResolver;
import org.springframework.web.method.support.ModelAndViewContainer;

import javax.servlet.http.HttpServletRequest;

public class AuthArgumentResolver implements HandlerMethodArgumentResolver {

    private final MembersDao membersDao;

    public AuthArgumentResolver(MembersDao membersDao) {
        this.membersDao = membersDao;
    }

    @Override
    public boolean supportsParameter(MethodParameter parameter) {
        return parameter.hasParameterAnnotation(Auth.class);
    }

    @Override
    public Member resolveArgument(
            MethodParameter parameter,
            ModelAndViewContainer mavContainer,
            NativeWebRequest webRequest,
            WebDataBinderFactory binderFactory) {

        HttpServletRequest request = (HttpServletRequest) webRequest.getNativeRequest();
        String email = (String) request.getAttribute("email");

        long memberId = membersDao.findIdByEmail(email);

        return membersDao.findById(memberId);
    }
}
