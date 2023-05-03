package cart.authorization;

import cart.exception.AuthenticationFailureException;

import javax.servlet.http.HttpServletRequest;

public interface AuthorizationExtractor<T> {

    String AUTHORIZATION = "Authorization";

    T extract(HttpServletRequest request) throws AuthenticationFailureException;
}
