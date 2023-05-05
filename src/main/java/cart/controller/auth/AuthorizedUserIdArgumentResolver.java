package cart.controller.auth;

import javax.servlet.http.HttpServletRequest;

import org.springframework.core.MethodParameter;
import org.springframework.web.bind.support.WebDataBinderFactory;
import org.springframework.web.context.request.NativeWebRequest;
import org.springframework.web.method.support.HandlerMethodArgumentResolver;
import org.springframework.web.method.support.ModelAndViewContainer;

import cart.controller.auth.dto.AuthInfo;
import cart.dao.UserDao;
import cart.dao.dto.UserDto;
import cart.infra.BasicAuthorizationExtractor;

public class AuthorizedUserIdArgumentResolver implements HandlerMethodArgumentResolver {

    private final UserDao userDao;

    public AuthorizedUserIdArgumentResolver(UserDao userDao) {
        this.userDao = userDao;
    }

    @Override
    public boolean supportsParameter(MethodParameter parameter) {
        return parameter.hasParameterAnnotation(AuthorizedUserId.class);
    }

    @Override
    public Object resolveArgument(MethodParameter parameter, ModelAndViewContainer mavContainer,
            NativeWebRequest webRequest, WebDataBinderFactory binderFactory) {
        HttpServletRequest request = webRequest.getNativeRequest(HttpServletRequest.class);
        AuthInfo authInfo = BasicAuthorizationExtractor.extract(request);
        UserDto userDto = userDao.selectBy(authInfo.getEmail());
        return userDto.getId();
    }
}
