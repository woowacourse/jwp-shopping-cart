package woowacourse.member.domain;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static woowacourse.helper.fixture.MemberFixture.EMAIL;
import static woowacourse.helper.fixture.MemberFixture.ENCODE_PASSWORD;
import static woowacourse.helper.fixture.MemberFixture.NAME;
import static woowacourse.helper.fixture.MemberFixture.PASSWORD;
import static woowacourse.helper.fixture.MemberFixture.createMember;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;
import woowacourse.member.exception.MemberCreateException;
import woowacourse.member.infrastructure.SHA256PasswordEncoder;

public class MemberTest {

    @DisplayName("비밀번호를 암호화한다.")
    @Test
    void encodePassword() {
        Member member = createMember(EMAIL, PASSWORD, NAME);
        member.encodePassword(new SHA256PasswordEncoder());

        assertThat(member.getPassword()).isEqualTo(ENCODE_PASSWORD);
    }

    @DisplayName("비밀번호를 확인한다.")
    @Test
    void authenticate() {
        Member member = createMember(EMAIL, PASSWORD, NAME);

        assertThat(member.authenticate(PASSWORD)).isTrue();
    }

    @DisplayName("이름을 변경한다.")
    @Test
    void updateName() {
        Member member = createMember(EMAIL, PASSWORD, NAME);
        member.updateName("MARU");

        assertThat(member.getName()).isEqualTo("MARU");
    }

    @DisplayName("이메일 형식이 아니면 에러가 발생한다.")
    @Test
    void validateRightEmail() {
        assertThatThrownBy(() -> createMember("abcde", PASSWORD, NAME))
                .isInstanceOf(MemberCreateException.class);
    }

    @DisplayName("비밀번호 형식이 아니면 에러가 발생한다.")
    @Test
    void validateRightPassword() {
        assertThatThrownBy(() -> createMember(EMAIL, "1234", NAME))
                .isInstanceOf(MemberCreateException.class);
    }

    @DisplayName("올바른 이름 형식이 아니면 에러가 발생한다.")
    @ParameterizedTest
    @ValueSource(strings = {"", "aaaaaaaaaaa"})
    void validateRightName(String value) {
        assertThatThrownBy(() -> createMember(EMAIL, PASSWORD, value))
                .isInstanceOf(MemberCreateException.class);
    }
}
