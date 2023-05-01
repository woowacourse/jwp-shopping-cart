package cart.web.controller.cart;

import javax.servlet.http.HttpServletRequest;

public interface AuthorizationExtractor<T> {
    String AUTHORIZATION = "Authorization";

    T extract(HttpServletRequest request);
}
