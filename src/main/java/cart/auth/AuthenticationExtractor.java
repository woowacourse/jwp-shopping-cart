package cart.auth;

import cart.dto.MemberDto;

import javax.servlet.http.HttpServletRequest;

public interface AuthenticationExtractor<T> {

    MemberDto extract(HttpServletRequest httpServletRequest);
}
