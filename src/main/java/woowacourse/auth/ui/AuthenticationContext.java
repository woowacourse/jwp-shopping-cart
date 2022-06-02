package woowacourse.auth.ui;

import java.util.Objects;
import org.springframework.stereotype.Component;
import org.springframework.web.context.annotation.RequestScope;

@Component
@RequestScope
public class AuthenticationContext {

    private String principal;

    public String getPrincipal() {
        if (Objects.isNull(principal)) {
            throw new IllegalStateException("principal은 null값이 반환될 수 없습니다.");
        }
        return principal;
    }

    public void setPrincipal(final String principal) {
        if (Objects.nonNull(this.principal)) {
            throw new IllegalStateException("이미 principal 정보가 있어 수정할 수 없습니다.");
        }
        this.principal = principal;
    }
}
