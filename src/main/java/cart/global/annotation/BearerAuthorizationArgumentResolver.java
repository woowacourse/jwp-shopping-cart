package cart.global.annotation;

import cart.auth.AuthAccount;

public class BearerAuthorizationArgumentResolver extends LogInAuthorizationArgumentResolver {

    private static final String BEARER_TYPE = "Bearer";

    @Override
    protected boolean canDecoding(final String header) {
        return header.toLowerCase().startsWith(BEARER_TYPE.toLowerCase());
    }

    @Override
    protected AuthAccount decode(final String header) {
        throw new UnsupportedOperationException("아직 지원되지 않은 기능입니다.");
    }
}
