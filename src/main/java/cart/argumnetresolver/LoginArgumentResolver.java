package cart.argumnetresolver;

import cart.dao.MemberDao;
import cart.entity.MemberEntity;
import cart.exception.AuthenticationException;
import java.util.Base64;
import javax.servlet.http.HttpServletRequest;
import org.springframework.core.MethodParameter;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.support.WebDataBinderFactory;
import org.springframework.web.context.request.NativeWebRequest;
import org.springframework.web.method.support.HandlerMethodArgumentResolver;
import org.springframework.web.method.support.ModelAndViewContainer;

@Component
public class LoginArgumentResolver implements HandlerMethodArgumentResolver {

  private final MemberDao memberDao;

  public LoginArgumentResolver(final MemberDao memberDao) {
    this.memberDao = memberDao;
  }

  @Override
  public boolean supportsParameter(final MethodParameter parameter) {
    return parameter.hasParameterAnnotation(Login.class);
  }

  @Override
  public Object resolveArgument(final MethodParameter parameter, final ModelAndViewContainer mavContainer,
      NativeWebRequest webRequest, WebDataBinderFactory binderFactory)  {
    final HttpServletRequest request = (HttpServletRequest) webRequest.getNativeRequest();
    final String header = request.getHeader("Authorization");
    final String authorization = header.split(" ")[1];
    final String emailAndPassword = new String(Base64.getDecoder().decode(authorization));
    final String[] splitEmailAndPassword = emailAndPassword.split(":");
    final MemberEntity memberEntity = new MemberEntity(splitEmailAndPassword[0], splitEmailAndPassword[1]);
    final MemberEntity findEntity = memberDao.findByMemberEntity(memberEntity)
        .orElseThrow(() -> new AuthenticationException("올바른 인증정보를 입력해주세요."));

    return findEntity.getId();
  }
}
