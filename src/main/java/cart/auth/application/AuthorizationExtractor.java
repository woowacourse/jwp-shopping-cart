package cart.auth.application;

import javax.servlet.http.HttpServletRequest;

import cart.auth.dto.AuthInfo;

public interface AuthorizationExtractor<T extends AuthInfo> {
	String AUTHORIZATION = "Authorization";

	T extract(HttpServletRequest request);
}
