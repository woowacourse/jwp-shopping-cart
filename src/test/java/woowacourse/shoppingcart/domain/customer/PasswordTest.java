package woowacourse.shoppingcart.domain.customer;

import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;

class PasswordTest {

    private static final String PASSWORD = "Leo1234!@";

    @Test
    void isSame() {
        final Password password = Password.of(PASSWORD);
        final boolean result = password.isSame(password);

        assertThat(result).isTrue();
    }
}
