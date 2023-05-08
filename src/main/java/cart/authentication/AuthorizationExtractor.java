package cart.authentication;

import javax.servlet.http.HttpServletRequest;

public interface AuthorizationExtractor<T> {

    T extact(HttpServletRequest request);
}
