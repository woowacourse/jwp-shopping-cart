package woowacourse.member.domain;

import static org.assertj.core.api.Assertions.assertThat;
import static woowacourse.helper.fixture.MemberFixture.EMAIL;
import static woowacourse.helper.fixture.MemberFixture.ENCODE_PASSWORD;
import static woowacourse.helper.fixture.MemberFixture.NAME;
import static woowacourse.helper.fixture.MemberFixture.PASSWORD;
import static woowacourse.helper.fixture.MemberFixture.createMember;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import woowacourse.member.infrastructure.SHA256PasswordEncoder;

public class MemberTest {

    @DisplayName("비밀번호를 암호화한다.")
    @Test
    void encodePassword() {
        Member member = createMember(EMAIL, PASSWORD, NAME);
        member.encodePassword(new SHA256PasswordEncoder());

        assertThat(member.getPassword()).isEqualTo(ENCODE_PASSWORD);
    }
}
