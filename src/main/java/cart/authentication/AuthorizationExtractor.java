package cart.authentication;

import javax.servlet.http.HttpServletRequest;

public interface AuthorizationExtractor<T> {

    MemberInfo extract(HttpServletRequest request);
}
