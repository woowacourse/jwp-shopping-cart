package cart.authentication;

import cart.controller.dto.AuthInfo;

public class AuthInfoThreadLocal {

    private final ThreadLocal<AuthInfo> localAuthInfo = new ThreadLocal<>();

    public void set(AuthInfo authInfo) {
        localAuthInfo.set(authInfo);
    }

    public AuthInfo get() {
        return localAuthInfo.get();
    }

    public void remove() {
        localAuthInfo.remove();
    }
}
