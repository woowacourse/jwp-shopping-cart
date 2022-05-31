package woowacourse.member.infrastructure;

import static org.assertj.core.api.Assertions.assertThat;
import static woowacourse.helper.fixture.MemberFixture.ENCODE_PASSWORD;
import static woowacourse.helper.fixture.MemberFixture.PASSWORD;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import woowacourse.helper.fixture.MemberFixture;

public class PasswordEncoderTest {

    @DisplayName("비밀번호를 암호화한다.")
    @Test
    void encode() {
        PasswordEncoder passwordEncoder = MemberFixture.passwordEncoder();
        assertThat(passwordEncoder.encode(PASSWORD)).isEqualTo(ENCODE_PASSWORD);
    }
}
