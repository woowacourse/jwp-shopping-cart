package woowacourse.auth.service;

import woowacourse.auth.ui.dto.TokenRequest;

public interface AuthenticationService {
    String getToken(TokenRequest tokenRequest);
}
