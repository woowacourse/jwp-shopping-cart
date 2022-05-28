package woowacourse.auth.domain.user;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

class DetailAddressTest {
    @DisplayName("상세 주소 문자열을 전달받아 생성된다.")
    @Test
    void from() {
        // given
        String address = "이디야 1층 책상";

        // when
        DetailAddress actual = DetailAddress.from(address);

        // then
        assertThat(actual).isNotNull();
    }
}
