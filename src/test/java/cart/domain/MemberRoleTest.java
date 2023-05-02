package cart.domain;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.assertj.core.api.AssertionsForClassTypes.assertThatThrownBy;
import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;

import cart.exception.ErrorCode;
import cart.exception.GlobalException;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;
import org.junit.jupiter.params.provider.ValueSource;

public class MemberRoleTest {

    @ParameterizedTest(name = "주어진 권한 정보에 맞는 이름이 들어왔다면, 사용자 권한 객체를 생성한다.")
    @ValueSource(strings = {"USER", "ADMIN"})
    void from_success(final String validRoleName) {
        final MemberRole role = assertDoesNotThrow(() -> MemberRole.from(validRoleName));
        assertThat(role)
            .isInstanceOf(MemberRole.class)
            .extracting("name")
            .isEqualTo(validRoleName);
    }

    @ParameterizedTest(name = "주어진 권한 정보에 맞지 않는 이름이 들어왔다면, 예외가 발생한다.")
    @ValueSource(strings = {"USE", "사용자", "어드민", ""})
    void from_fail(final String validRoleName) {
        assertThatThrownBy(() -> MemberRole.from(validRoleName))
            .isInstanceOf(GlobalException.class)
            .extracting("errorCode")
            .isEqualTo(ErrorCode.MEMBER_INVALID_ROLE);

    }

    @ParameterizedTest(name = "입력받은 권한이 어드민인지 확인한다.")
    @CsvSource(value = {"ADMIN:true", "USER:false"}, delimiter = ':')
    void isAdmin(final String role, final boolean expected) {
        assertThat(MemberRole.isAdmin(role))
            .isSameAs(expected);
    }
}
