package cart.service;

import static cart.fixture.MemberFixture.TEST_MEMBER1;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;

import cart.exception.MemberNotFoundException;
import cart.repository.JdbcMemberRepository;
import cart.service.dto.MemberInfo;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.JdbcTest;
import org.springframework.context.annotation.Import;

@JdbcTest
@Import({JdbcMemberRepository.class, BasicAuthService.class})
class BasicAuthServiceTest {

    @Autowired
    BasicAuthService basicAuthService;

    @Nested
    @DisplayName("MemberInfo가 유효한 멤버인지 확인하는 기능 테스트")
    class authorizeTest {

        @Test
        @DisplayName("memberInfo의 정보가 memberDb에 존재하지 않는다면, MemberNotFoundException을 반환한다.")
        void invalidMemberInfo() {
            final MemberInfo memberInfo = new MemberInfo("bakfox@wooteco.com", "bakfox");

            assertThatThrownBy(() -> basicAuthService.authorize(memberInfo))
                    .isInstanceOf(MemberNotFoundException.class);
        }

        @Test
        @DisplayName("memberInfo의 정보가 memberDb에 존재한다면, Exception을 반환하지 않는다.")
        void validMemberInfoTest() {
            final MemberInfo memberInfo = new MemberInfo(TEST_MEMBER1.getEmail(), TEST_MEMBER1.getPassword());

            assertDoesNotThrow(() -> basicAuthService.authorize(memberInfo));
        }
    }
}
