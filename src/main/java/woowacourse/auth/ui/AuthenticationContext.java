package woowacourse.auth.ui;

import java.util.Objects;
import org.springframework.stereotype.Component;
import org.springframework.web.context.annotation.RequestScope;
import woowacourse.auth.exception.InvalidAuthException;

@Component
@RequestScope
public class AuthenticationContext {

    private Long principal;

    public long getPrincipal() {
        if (Objects.isNull(principal)) {
            throw new InvalidAuthException("principal은 null값이 반환될 수 없습니다.");
        }
        return principal;
    }

    public void setPrincipal(final String principal) {
        this.principal = Long.parseLong(principal);
    }
}
