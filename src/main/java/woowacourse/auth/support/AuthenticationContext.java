package woowacourse.auth.support;

import org.springframework.stereotype.Component;
import org.springframework.web.context.annotation.RequestScope;
import woowacourse.shoppingcart.domain.customer.Email;

@Component
@RequestScope
public class AuthenticationContext {

    private Email email;

    public Email getEmail() {
        return email;
    }

    public void setEmail(Email email) {
        this.email = email;
    }
}
